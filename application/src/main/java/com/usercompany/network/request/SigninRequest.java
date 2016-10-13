package com.usercompany.network.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.usercompany.network.ApplicationApi;
import com.usercompany.network.ResponseModel;

public class SigninRequest extends RetrofitSpiceRequest<ResponseModel, ApplicationApi> {

    private FacebookLogin data;

    public SigninRequest(FacebookLogin data) {
        super(ResponseModel.class, ApplicationApi.class);
        this.data = data;
    }

    @Override
    public ResponseModel loadDataFromNetwork() throws Exception {
        return getService().userSignup(data);
    }
}