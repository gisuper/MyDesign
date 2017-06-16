package com.yangxiong.gisuper.mideaplayer.global;

import android.util.Log;

/**
 * Created by yangxiong on 2017/6/16.
 */

public class LogUtil {

    /*private static LogUtil instance;

    private LogUtil() {
    }

    public static LogUtil getInstance() {
        if (instance == null) {
            synchronized (LogUtil.class) {
                if (instance == null) {
                    instance = new LogUtil( );
                }
            }
        }
        return instance;
    }*/

    public static void i(String tag, String str) {
        Log.i(GlobalConfig.logPre + tag, str);
    }

    public static void d(String tag, String str) {
        if (!GlobalConfig.logOn) return;
        Log.d(GlobalConfig.logPre + tag, str);
    }

    public static void v(String tag, String str) {
        if (!GlobalConfig.logOn) return;
        Log.v(GlobalConfig.logPre + tag, str);
    }

    public static void e(String tag, String str) {
        Log.e(GlobalConfig.logPre + tag, str);
    }
}
