package com.mark.download_lib.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Unique;

/**
 * apk下载请求数据基础类
 * Created by WZG on 2016/10/20.
 */

@Entity
public class DownInfo{
    @Id(autoincrement = true)
    private Long id;
    /*存储位置*/
    private String savePath;
    /*文件总长度*/
    private long totalSize;
    /*下载长度*/
    private long readLength;
    /*state状态数据库保存*/
    private int stateInte;
    /*url*/
    @Unique
    private String url;
    /*是否需要实时更新下载进度,避免线程的多次切换*/
    private boolean updateProgress;

    private int priority;

    @Keep
    public DownInfo(String url, String savePath) {
        this.url = url;
        this.savePath = savePath;
    }

    @Generated(hash = 1687186826)
    public DownInfo(Long id, String savePath, long totalSize, long readLength,
            int stateInte, String url, boolean updateProgress, int priority) {
        this.id = id;
        this.savePath = savePath;
        this.totalSize = totalSize;
        this.readLength = readLength;
        this.stateInte = stateInte;
        this.url = url;
        this.updateProgress = updateProgress;
        this.priority = priority;
    }

    @Generated(hash = 928324469)
    public DownInfo() {
    }

    public DownState getState() {
        switch (getStateInte()){
            case 0:
                return DownState.START;
            case 1:
                return DownState.READY;
            case 2:
                return DownState.DOWN;
            case 3:
                return DownState.PAUSE;
            case 4:
                return DownState.ERROR;
            case 5:
            default:
                return DownState.FINISH;
        }
    }

    public boolean isUpdateProgress() {
        return updateProgress;
    }

    public void setUpdateProgress(boolean updateProgress) {
        this.updateProgress = updateProgress;
    }

    public void setState(DownState state) {
        setStateInte(state.getState());
    }


    public int getStateInte() {
        return stateInte;
    }

    public void setStateInte(int stateInte) {
        this.stateInte = stateInte;
    }

    public String getUrl() {
        return url;
    }

    public String getSavePath() {
        return savePath;
    }
    


    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }


    public long getReadLength() {
        return readLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getUpdateProgress() {
        return this.updateProgress;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DownInfo downInfo = (DownInfo) o;

        return url != null ? url.equals(downInfo.url) : downInfo.url == null;
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
