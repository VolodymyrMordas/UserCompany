package com.applog;

import android.util.Log;

/**
 * Created by volodymyrmordas on 10/13/16.
 */
public class LogUtils {
    public static final boolean DEVELOPER_MODE = BuildConfig.DEBUG;

    public static void LOGD(final String tag, String message) {
        if(DEVELOPER_MODE && message!=null) {
            Log.d(tag, message);
        }
    }

    public static void LOGI(final String tag, String message) {
        if(DEVELOPER_MODE && message!=null) {
            Log.i(tag, message);
        }
    }

    public static void LOGE(final String tag, String message) {
        if(DEVELOPER_MODE && message!=null) {
            Log.e(tag, message);
        }
    }

    public static void LOGW(final String tag, String message) {
        if(DEVELOPER_MODE && message!=null) {
            Log.w(tag, message);
        }
    }
}
