package com.mark.download_lib.upload;

import android.support.annotation.NonNull;

/**
 * <pre>
 *     author : Mark
 *     e-mail : makun.cai@aorise.org
 *     time   : 2018/06/15
 *     desc   : TODO
 *     version: 1.0
 * </pre>
 */
public class UploadTask {

    private final String url;
    private final String filePath;
    private String description;
    private boolean updateProgress;
    private int priority;
    private UploadListener mListener;

    public UploadTask(String url,String filePath,String description, boolean updateProgress, int priority) {
        this.url = url;
        this.filePath = filePath;
        this.description = description;
        this.updateProgress = updateProgress;
        this.priority = priority;
    }

    public String getUrl() {
        return url;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getDescription() {
        return description;
    }

    public boolean isUpdateProgress() {
        return updateProgress;
    }

    public int getPriority() {
        return priority;
    }
    public UploadListener getListener(){
        return mListener;
    }

    public void upload(UploadListener listener){
        this.mListener = listener;
        UploadManager.getInstance().upload(this);
    }

    public static class Builder {
        private final String url;
        private final String filePath;
        private String description;
        private boolean updateProgress;
        private int priority;

        public Builder(@NonNull String url, @NonNull String filePath) {
            this.filePath = filePath;
            this.url = url;
        }

        public UploadTask.Builder updateProgress(boolean updateProgress) {
            this.updateProgress=updateProgress;
            return this;
        }

        public UploadTask.Builder priority(int priority) {
            priority = priority < 0 ? 0 : priority;
            priority = priority > 1000 ? 1000 : priority;
            this.priority = priority;
            return this;
        }

        public UploadTask.Builder description(String description) {
            this.description=description;
            return this;
        }

        public UploadTask build() {
            return new UploadTask(url,filePath,description,updateProgress,priority);
        }
    }
}
