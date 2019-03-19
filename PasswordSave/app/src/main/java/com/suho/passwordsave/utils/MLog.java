package com.suho.passwordsave.utils;

import android.util.Log;

import com.suho.passwordsave.BuildConfig;


/**
 *
 * @author suho
 * @date 2017/8/07
 */

public class MLog {
    final static String TAG = "IncomeTax";

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg);
        }
    }

}
