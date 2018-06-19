package com.mark.download_lib.bean;

/**
 * 下载状态
 * Created by WZG on 2016/10/21.
 */

public enum  DownState {
    START(0),
    READY(1),
    DOWN(2),
    PAUSE(3),
    ERROR(4),
    FINISH(5);
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    DownState(int state) {
        this.state = state;
    }
}
