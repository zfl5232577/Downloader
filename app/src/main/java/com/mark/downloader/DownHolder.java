package com.mark.downloader;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.mark.download_lib.bean.DownInfo;
import com.mark.download_lib.bean.DownState;
import com.mark.download_lib.download.DownloadListener;
import com.mark.download_lib.download.DownloadManager;
import com.mark.download_lib.download.DownloadTask;

/**
 * 下载item
 * Created by WZG on 2016/10/21.
 */

public class DownHolder extends BaseViewHolder<DownloadTask> implements View.OnClickListener, DownloadListener {
    private TextView tvMsg;
    private NumberProgressBar progressBar;
    private DownloadTask mDownloadTask;
    private long time;
    private Context mContext;
    private long lastLength;

    public DownHolder(ViewGroup parent) {
        super(parent, R.layout.view_item_holder);
        mContext = parent.getContext();
        $(R.id.btn_rx_down).setOnClickListener(this);
        $(R.id.btn_rx_pause).setOnClickListener(this);
        progressBar = $(R.id.number_progress_bar);
        tvMsg = $(R.id.tv_msg);
    }

    @Override
    public void setData(DownloadTask data) {
        super.setData(data);
        mDownloadTask = data;
        progressBar.setMax(100);
        progressBar.setProgress(mDownloadTask.getTotalSize() == 0 ? 0 : (int) (mDownloadTask.getReadLength() * 100 / mDownloadTask.getTotalSize()));
        /*第一次恢复 */
        switch (mDownloadTask.getState()) {
            case START:
                mDownloadTask.enqueue(this);
                /*起始状态*/
                break;
            case READY:
                tvMsg.setText("准备就绪");
                break;
            case PAUSE:
                tvMsg.setText("暂停中");
                break;
            case DOWN:
                mDownloadTask.enqueue(this);
                break;
            case ERROR:
                tvMsg.setText("下載錯誤");
                break;
            case FINISH:
                tvMsg.setText("下载完成");
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rx_down:
                mDownloadTask.enqueue(this);
                break;
            case R.id.btn_rx_pause:
                mDownloadTask.onPause();
                break;
        }
    }


    @Override
    public void onReady() {
        tvMsg.setText("提示:准备就绪等待下载");
    }

    @Override
    public void onStart() {
        tvMsg.setText("提示:开始下载");
        time = System.currentTimeMillis();
    }

    @Override
    public void onConflict(DownloadTask task) {
        Toast.makeText(mContext, "任务正在下载...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNext(DownloadTask task) {
        tvMsg.setText("提示：下载完成/文件地址->" + task.getSaveFile().getAbsolutePath());
    }

    @Override
    public void onFailed() {
        tvMsg.setText("失败");
    }

    @Override
    public void onComplete() {
        tvMsg.setText("下载完成");
        Toast.makeText(getContext(), "提示：下载结束", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgress(long readLength, long totalSize) {
        Log.e("DownHolder", "onProgress: "+Thread.currentThread().getName() );
        if (lastLength == 0) {
            lastLength = readLength;
        }
        long currentTime = System.currentTimeMillis();
        long timediff = currentTime - time;
        if (timediff > 500) {
            double rate;
            time = currentTime;
            rate = (readLength - lastLength) * 1000 / timediff;
            lastLength = readLength;
            String rateUnit;
            if (rate > 1024 * 1024) {
                rate = (double) Math.round(rate / 1024 / 1024 * 100) / 100;
                rateUnit = "MB/s";
            } else if (rate > 1024) {
                rate = (double) Math.round(rate / 1024 * 100) / 100;
                rateUnit = "KB/s";
            } else {
                rate = (double) Math.round(rate * 100) / 100;
                rateUnit = "B/s";
            }
            tvMsg.setText("提示:下载中" + rate + rateUnit);
        }
        progressBar.setProgress((int) (readLength * 100 / totalSize));
    }

    @Override
    public void onPause() {
        tvMsg.setText("提示:暂停");
    }

    @Override
    public void onCancel() {
        tvMsg.setText("提示:已经取消");
    }
}