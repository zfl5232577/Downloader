package com.mark.download_lib.retrofit;

import com.mark.download_lib.BuildConfig;
import com.mark.download_lib.api.API;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 *     author : tangjy
 *     e-mail : jianye.tang@aorise.org
 *     time   : 2017/09/14
 *     desc   : 联网请求工厂类
 *     version: 1.0
 * </pre>
 */
public class RetrofitFactory {

    private static volatile RetrofitFactory sInstance;
    private HashMap<String, Object> services = new HashMap<>();
    private Builder mHttpBuilder = (new OkHttpClient()).newBuilder();
    private Retrofit.Builder mRetrofitBuilder = new Retrofit.Builder();
    private Retrofit mRetrofit;

    static RetrofitFactory getInstance() {
        if (sInstance == null) {
            synchronized (RetrofitFactory.class) {
                if (sInstance == null) {
                    sInstance = new RetrofitFactory();
                }
            }
        }

        return sInstance;
    }

    private RetrofitFactory() {
    }

    public OkHttpClient getOkHttpsClient(boolean debug) {
        try {
            X509TrustManager manager = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                public void checkClientTrusted(javax.security.cert.X509Certificate[] chain, String authType) throws javax.security.cert.CertificateException {
                }

                public void checkServerTrusted(javax.security.cert.X509Certificate[] chain, String authType) throws javax.security.cert.CertificateException {
                }
            };
            TrustManager[] trustAllCerts = new TrustManager[]{manager};
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init((KeyManager[]) null, trustAllCerts, new SecureRandom());
            SSLSocketFactory factory = sc.getSocketFactory();
            this.mHttpBuilder.connectTimeout(60L, TimeUnit.SECONDS).writeTimeout(60L, TimeUnit.SECONDS).readTimeout(60L, TimeUnit.SECONDS).sslSocketFactory(factory, manager).hostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            if (debug) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(Level.HEADERS);
                this.mHttpBuilder.addInterceptor(interceptor);
            }
            // this.mHttpBuilder.addInterceptor(mSessionInterceptor);

            return this.mHttpBuilder.build();
        } catch (Exception var7) {
            throw new RuntimeException(var7);
        }
    }

    public <T> T create(Class<T> service, String uri) {
        Object apiService = services.get(uri);
        if (apiService == null) {
            synchronized (services) {
                apiService = services.get(uri);
                if (apiService == null) {
                    mRetrofit = mRetrofitBuilder.baseUrl(uri)
                            .client(getOkHttpsClient(BuildConfig.DEBUG))
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
                    apiService = mRetrofit.create(service);
                    services.put(uri, apiService);
                }
            }
        }
        return (T) apiService;
    }
}
