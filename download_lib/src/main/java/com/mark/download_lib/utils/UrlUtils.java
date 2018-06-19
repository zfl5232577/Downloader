package com.mark.download_lib.utils;

import android.os.Environment;

import java.io.File;

import io.reactivex.annotations.NonNull;

/**
 * <pre>
 *     author : Mark
 *     e-mail : makun.cai@aorise.org
 *     time   : 2018/06/15
 *     desc   : TODO
 *     version: 1.0
 * </pre>
 */
public class UrlUtils {

    /**
     * 读取baseurl
     * @param url
     * @return
     */
    public static String getBasUrl(@NonNull String url) {
        String head = "";
        int index = url.indexOf("://");
        if (index != -1) {
            head = url.substring(0, index + 3);
            url = url.substring(index + 3);
        }
        index = url.indexOf("/");
        if (index != -1) {
            url = url.substring(0, index + 1);
        }
        return head + url;
    }

    /**
     * FileName
     * @param url
     * @return
     */
    public static String getFileName(@NonNull String url) {
        int index = url.lastIndexOf(File.pathSeparator);
        return url.substring(index + 1);
    }


}
