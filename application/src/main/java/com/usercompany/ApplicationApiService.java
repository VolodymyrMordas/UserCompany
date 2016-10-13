package com.usercompany;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;
import com.usercompany.network.ApplicationApi;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class ApplicationApiService extends RetrofitGsonSpiceService {
    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(ApplicationApi.class);
    }
    @Override
    protected String getServerUrl() {
        return BuildConfig.API_URL;
    }
    @Override
    protected RestAdapter.Builder createRestAdapterBuilder() {
        GsonConverter gsonConverter = new GsonConverter(Application.getApplication().getGson());
        return super.createRestAdapterBuilder().setLogLevel(
                BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setConverter(gsonConverter);
    }

    @Override
    public int getThreadCount() {
        return super.getThreadCount() + 3;
    }
}