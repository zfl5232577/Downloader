package com.mark.download_lib.download;

import android.os.Environment;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.mark.download_lib.R;
import com.mark.download_lib.bean.DownInfo;
import com.mark.download_lib.bean.DownState;
import com.mark.download_lib.db.DbHelper;
import com.mark.download_lib.utils.UrlUtils;

import java.io.File;

/**
 * <pre>
 *     author : Mark
 *     e-mail : makun.cai@aorise.org
 *     time   : 2018/06/14
 *     desc   : TODO
 *     version: 1.0
 * </pre>
 */
public class DownloadTask implements Comparable<DownloadTask> {
    private final DownInfo mDownInfo;
    private DownloadListener mDownloadListener;

    DownloadTask(DownInfo downInfo) {
        this.mDownInfo = downInfo;
    }

    public DownInfo getDownInfo() {
        return mDownInfo;
    }

    public DownloadListener getDownloadListener() {
        return mDownloadListener;
    }

    private int getPriority() {
        return mDownInfo.getPriority();
    }

    public DownState getState(){
        return mDownInfo.getState();
    }

    void setState(DownState state) {
        mDownInfo.setState(state);
    }

    String getUrl(){
        return mDownInfo.getUrl();
    }

    public long getReadLength(){
        long readLength=0;
        File file = getSaveFile();
        if (file.exists()){
            readLength = file.length();
            setReadLength(readLength);
        }
        return readLength;
    }

    public void setReadLength(long readLength) {
        mDownInfo.setReadLength(readLength);
    }

    public File getSaveFile(){
        return new File(mDownInfo.getSavePath());
    }

    public boolean isUpdateProgress() {
        return mDownInfo.isUpdateProgress();
    }

    public void setTotalSize(long totalSize) {
        mDownInfo.setTotalSize(totalSize);
    }
    public long getTotalSize() {
        return mDownInfo.getTotalSize();
    }

    public void enqueue(DownloadListener listener) {
        mDownloadListener = listener;
        DownloadManager.getInstance().enqueue(this);
    }

    public void onPause(){
        DownloadManager.getInstance().onPause(this);
    }

    public void cancel(){
        DownloadManager.getInstance().cancel(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DownloadTask that = (DownloadTask) o;

        return mDownInfo != null ? mDownInfo.equals(that.mDownInfo) : that.mDownInfo == null;
    }

    @Override
    public int hashCode() {
        return mDownInfo != null ? mDownInfo.hashCode() : 0;
    }

    @Override
    public int compareTo(@NonNull DownloadTask task) {
        return task.getPriority() - getPriority();
    }


    /**
     * The builder of download task.
     */
    public static class Builder {
        private DownInfo mDownInfo;

        public Builder(@NonNull String url) {
            String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+File.separator+ UrlUtils.getFileName(url);
            mDownInfo = new DownInfo(url, filePath);
        }

        public Builder(@NonNull String url, @NonNull String filePath) {
            mDownInfo = new DownInfo(url, filePath);
        }

        public Builder updateProgress(boolean updateProgress) {
            mDownInfo.setUpdateProgress(updateProgress);
            return this;
        }

        public Builder priority(int priority) {
            priority = priority < 0 ? 0 : priority;
            priority = priority > 1000 ? 1000 : priority;
            mDownInfo.setPriority(priority);
            return this;
        }

        public DownloadTask build() {
            return new DownloadTask(mDownInfo);
        }
    }
}
