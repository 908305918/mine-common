package com.lucy.common;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.OkHttpClient;

/**
 * Created by YJB on 2017/3/11.
 */

public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}
