package com.mark.download_lib.utils;

import android.util.Log;

import com.mark.download_lib.bean.DownState;
import com.mark.download_lib.db.DbHelper;
import com.mark.download_lib.download.DownloadTask;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

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

    public static void writeFile(@NonNull DownloadTask task, ResponseBody body) throws Exception {
        File file = task.getSaveFile();
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        byte[] fileReader = new byte[1024 * 4];
        //已经下载的文件长度
        long fileSizeDownloaded = task.getReadLength();
        long fileSize = fileSizeDownloaded == 0 ? body.contentLength() : fileSizeDownloaded + body.contentLength();
        task.setTotalSize(fileSize);
        DbHelper.getInstance().getDaoSession().getDownInfoDao().insertOrReplace(task.getDownInfo());
        InputStream inputStream = body.byteStream();
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
        FileChannel channelOut = randomAccessFile.getChannel();
        MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE,
                fileSizeDownloaded, fileSize - fileSizeDownloaded);
        int len;
        while (task.getState()== DownState.DOWN &&(len = inputStream.read(fileReader)) != -1) {
            mappedBuffer.put(fileReader, 0, len);
            fileSizeDownloaded += len;
            task.setReadLength(fileSizeDownloaded);
            if (task.isUpdateProgress() && task.getDownloadListener() != null) {
                Observable.just(fileSizeDownloaded)
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        task.getDownloadListener().onProgress(aLong, fileSize);
                    }
                });
            }
        }
        CloseUtils.closeIO(inputStream,channelOut,randomAccessFile);
    }

    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        deleteFile(file);
    }

    public static void deleteFile(File file) {
        if (file.exists()) {
            file.delete();
        }
    }

}
