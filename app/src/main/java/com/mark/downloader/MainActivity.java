package com.mark.downloader;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.mark.download_lib.bean.DownInfo;
import com.mark.download_lib.bean.DownInfoDao;
import com.mark.download_lib.db.DbHelper;
import com.mark.download_lib.download.DownloadListener;
import com.mark.download_lib.download.DownloadManager;
import com.mark.download_lib.download.DownloadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> urlData = new ArrayList<>();
    int index = 0;
    private DownAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_laod);
        initResource();
        initWidget();
    }

    /*数据*/
    private void initResource() {
        urlData.add("http://dldir1.qq.com/weixin/Windows/WeChatSetup.exe");
        urlData.add("http://dldir1.qq.com/weixin/android/weixin6516android1120.apk");
        urlData.add("https://cdn.llscdn.com/yy/files/tkzpx40x-lls-LLS-5.7-785-20171108-111118.apk");
        urlData.add("https://t.alipayobjects.com/L1/71/100/and/alipay_wap_main.apk");
        urlData.add("https://dldir1.qq.com/qqfile/QQforMac/QQ_V6.2.0.dmg");
        urlData.add("https://zhstatic.zhihu.com/pkg/store/zhihu/futureve-mobile-zhihu-release-5.8.2(596).apk");
        urlData.add("http://d1.music.126.net/dmusic/CloudMusic_official_4.3.2.468990.apk");
        urlData.add("http://d1.music.126.net/dmusic/NeteaseMusic_1.5.9_622_officialsite.dmg");
        urlData.add("https://dldir1.qq.com/foxmail/work_weixin/wxwork_android_2.4.5.5571_100001.apk");
        urlData.add("https://dldir1.qq.com/foxmail/work_weixin/WXWork_2.4.5.213.dmg");

    }

    public void addTask(View view) {
        Log.e("sdsa", "addTask: ");
        if (index == urlData.size()) {
            Toast.makeText(this, "全部添加完毕", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!DownloadManager.getInstance().contains(new DownloadTask.Builder(urlData.get(index)).updateProgress(true).build())) {
            mAdapter.add(new DownloadTask.Builder(urlData.get(index)).updateProgress(true).build());
        }else {
            Toast.makeText(this, "下载列表已经存在", Toast.LENGTH_SHORT).show();
        }
        index++;
    }

    /*加载控件*/
    private void initWidget() {
        EasyRecyclerView recyclerView = (EasyRecyclerView) findViewById(R.id.rv);
        mAdapter = new DownAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        mAdapter.addAll(DownloadManager.getInstance().getAllList());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        /*记录退出时下载任务的状态-复原用*/
//        for (DownInfo downInfo : listData) {
//            dbUtil.update(downInfo);
//        }
    }
}
