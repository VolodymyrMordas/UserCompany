package com.usercompany.network;

import com.applog.LogUtils;
import com.octo.android.robospice.persistence.exception.SpiceException;

public abstract class RequestListener<RESULT extends ResponseModel>
        implements com.octo.android.robospice.request.listener.RequestListener<RESULT> {

    public static String TAG = RequestListener.class.getSimpleName();

    public static String ERROR = "ERROR";
    public static String ERROR_TXT = "SERVER ERROR";

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        LogUtils.LOGI(TAG, " - onRequestFailure - ");
        LogUtils.LOGI(TAG, spiceException.getMessage());
    }

    @Override
    public abstract void onRequestSuccess(RESULT result);
}