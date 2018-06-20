package com.idthk.usercenter.utils;

import android.util.Log;


public class LogUtil {

    public static final boolean DEBUG = true;//BuildConfig.DEBUG;

    public static void i(String tag, String msg) {
        if (DEBUG)
            Log.i(tag, msg);
    }


    public static void e(String tag, String msg) {
        if (DEBUG)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (DEBUG)
            Log.v(tag, msg);
    }

    public static void i(String msg) {
        if (DEBUG)
            Log.i("legend", msg);
    }


    public static void e(String msg) {
        if (DEBUG)
            Log.e("legend", msg);
    }

    public static void v(String msg) {
        if (DEBUG)
            Log.v("legend", msg);
    }

    public static void w(String tag, String msg) {
        if (DEBUG)
            Log.w("legend", msg);
    }

    public static void d(String tag, String msg) {
        if (DEBUG)
            Log.d(tag, msg);
    }

    public static void d(String msg) {
        if (DEBUG)
            Log.d("legend", msg);
    }

    /**
     * 输出系统错误
     *
     * @param msg
     */
    public static void ee(String msg) {
        Log.v("legend", msg);
    }

    public static void wtf(String msg) {
        Log.i("wtf", msg);
    }
}
