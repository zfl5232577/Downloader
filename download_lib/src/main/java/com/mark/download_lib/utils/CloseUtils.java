package com.mark.download_lib.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * <pre>
 *     author : Mark
 *     e-mail : makun.cai@aorise.org
 *     time   : 2018/06/12
 *     desc   : TODO
 *     version: 1.0
 * </pre>
 */
public class CloseUtils {
    public static void closeIO(Closeable... closeables){
        if(closeables != null) {
            int size = closeables.length;
            for(int i = 0; i < size; i++) {
                Closeable closeable = closeables[i];
                if(closeable != null) {
                    try {
                        closeable.close();
                    } catch (IOException var6) {
                        var6.printStackTrace();
                    }
                }
            }

        }
    }
}
