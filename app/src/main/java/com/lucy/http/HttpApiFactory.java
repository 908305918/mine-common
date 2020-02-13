package com.lucy.http;

import okhttp3.OkHttpClient;

/**
 * Created by WD on 2017/1/13.
 */

public class HttpApiFactory {

    public static HttpApi createHttpApi() {
        OkHttpClient okHttpClient = new OkHttpClient();
        return new OkHttpApi(okHttpClient);
    }

}
