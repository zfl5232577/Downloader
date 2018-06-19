package com.mark.download_lib.download;

import com.mark.download_lib.bean.DownInfo;

/**
 * <pre>
 *     author : Mark
 *     e-mail : makun.cai@aorise.org
 *     time   : 2018/06/12
 *     desc   : TODO
 *     version: 1.0
 * </pre>
 */
public interface DownloadListener {
    void onReady();
    void onStart();
    void onConflict(DownloadTask task);
    void onNext(DownloadTask task);
    void onFailed();
    void onComplete();
    void onProgress(long readLength, long totalSize);
    void onPause();
    void onCancel();
}
