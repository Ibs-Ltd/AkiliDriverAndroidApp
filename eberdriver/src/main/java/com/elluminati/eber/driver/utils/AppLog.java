package com.elluminati.eber.driver.utils;

import com.elluminati.eber.driver.BuildConfig;

/**
 * Created by elluminati on 29-03-2016.
 */
public class AppLog {

    private static final boolean isDebug = BuildConfig.DEBUG;

    public static void Log(String tag, String message) {
        if (isDebug) {
            android.util.Log.d(tag, message + "");
        }
    }

    public static void handleException(String tag, Exception e) {
        if (isDebug) {
            if (e != null) {
                android.util.Log.e(tag, e.toString());
            }
        }
    }

    public static final void handleThrowable(String tag, Throwable t) {
        if (isDebug) {
            if (t != null) {
                android.util.Log.e(tag, t + "");
            }
        }
    }
}
