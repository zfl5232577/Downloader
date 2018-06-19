package com.mark.download_lib.upload;

import com.mark.download_lib.download.DownloadTask;

import okhttp3.ResponseBody;

/**
 * <pre>
 *     author : Mark
 *     e-mail : makun.cai@aorise.org
 *     time   : 2018/06/15
 *     desc   : TODO
 *     version: 1.0
 * </pre>
 */
public interface UploadListener {
    void onStart();
    void onNext(ResponseBody responseBody);
    void onFailed();
    void onComplete();
    void onProgress(long readLength, long totalSize);
}
