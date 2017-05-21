package com.lucy.common;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.threshold.rxbus2.RxBus;
import com.zhy.http.okhttp.OkHttpUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.OkHttpClient;

/**
 * Created by YJB on 2017/3/11.
 */

public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        RxBus.config(AndroidSchedulers.mainThread());

        Stetho.initializeWithDefaults(this);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}
