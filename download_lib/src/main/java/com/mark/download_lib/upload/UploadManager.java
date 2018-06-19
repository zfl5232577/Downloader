package com.mark.download_lib.upload;

import android.util.Log;

import com.mark.download_lib.retrofit.UploadService;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * <pre>
 *     author : Mark
 *     e-mail : makun.cai@aorise.org
 *     time   : 2018/06/15
 *     desc   : TODO
 *     version: 1.0
 * </pre>
 */
public class UploadManager {

    private static volatile UploadManager mInstance;

    private UploadManager() {
    }

    public static UploadManager getInstance() {
        if (mInstance == null) {
            synchronized (UploadManager.class) {
                if (mInstance == null) {
                    mInstance = new UploadManager();
                }
            }
        }
        return mInstance;
    }

    void upload(@NonNull UploadTask task) {

        File file = new File(task.getFilePath());
        // 创建 RequestBody，用于封装 请求RequestBody
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        if (task.isUpdateProgress()){
            requestFile = new ProgressRequestBody(requestFile,task.getListener());
        }

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("aFile", file.getName(), requestFile);

        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), task.getDescription());

        // 执行请求
        UploadService.Factory.create(task.getUrl()).upload(description, body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (task.getListener()!=null){
                            task.getListener().onStart();
                        }
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (task.getListener()!=null){
                            task.getListener().onNext(responseBody);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (task.getListener()!=null){
                            task.getListener().onFailed();
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (task.getListener()!=null){
                            task.getListener().onComplete();
                        }
                    }
                });
    }
}
