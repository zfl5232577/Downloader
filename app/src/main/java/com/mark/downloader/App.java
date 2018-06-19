package com.mark.downloader;

import android.app.Application;

import com.mark.download_lib.db.DbHelper;

/**
 * <pre>
 *     author : Mark
 *     e-mail : makun.cai@aorise.org
 *     time   : 2018/06/13
 *     desc   : TODO
 *     version: 1.0
 * </pre>
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DbHelper.getInstance().init(this);
    }
}
