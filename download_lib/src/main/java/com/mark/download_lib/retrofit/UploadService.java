package com.mark.download_lib.retrofit;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * <pre>
 *     author : Mark
 *     e-mail : makun.cai@aorise.org
 *     time   : 2018/06/15
 *     desc   : TODO
 *     version: 1.0
 * </pre>
 */
public interface UploadService {
    @Multipart
    @POST("upload")
    Observable<ResponseBody> upload(@Part("description") RequestBody description,
                                    @Part MultipartBody.Part file);
    class Factory{
        public static UploadService create(String url){
            return RetrofitFactory.getInstance().create(UploadService.class,url);
        }
    }
}
