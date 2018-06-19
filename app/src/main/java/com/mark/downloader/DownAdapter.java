package com.mark.downloader;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.mark.download_lib.bean.DownInfo;
import com.mark.download_lib.download.DownloadTask;

/**
 * Created by Mark on 2016/10/21.
 */

public class DownAdapter extends RecyclerArrayAdapter<DownloadTask> {

    public DownAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new DownHolder(parent);
    }

}