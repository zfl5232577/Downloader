package com.mark.download_lib.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.mark.download_lib.download.DownloadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * <pre>
 *     author : Mark
 *     e-mail : makun.cai@aorise.org
 *     time   : 2018/06/12
 *     desc   : TODO
 *     version: 1.0
 * </pre>
 */
public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();

    public static void writeFile(@NonNull DownloadTask task, ResponseBody body) {

        File file = task.getSaveFile();
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            byte[] fileReader = new byte[4096];
            //已经下载的文件长度
            long fileSizeDownloaded = task.getReadLength();
            long fileSize = fileSizeDownloaded == 0 ? body.contentLength() : fileSizeDownloaded + body.contentLength();
            task.setTotalSize(fileSize);
            inputStream = body.byteStream();
            outputStream = new FileOutputStream(file, true);
            int len;
            while ((len = inputStream.read(fileReader)) != -1) {
                outputStream.write(fileReader, 0, len);
                fileSizeDownloaded += len;
                task.setReadLength(fileSizeDownloaded);
                if (task.isUpdateProgress()&&task.getDownloadListener()!=null){
                    Observable.just(fileSizeDownloaded)
                            .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            task.getDownloadListener().onProgress(aLong,fileSize);
                        }
                    });
                }
            }
            outputStream.flush();
        } catch (IOException e) {
        } finally {
            CloseUtils.closeIO(inputStream, outputStream);
        }
    }

    public static void deleteFile(String filePath){
        File file = new File(filePath);
        deleteFile(file);
    }

    public static void deleteFile(File file){
        if (file.exists()){
            file.delete();
        }
    }

}
