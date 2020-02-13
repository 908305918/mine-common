package com.lucy.http;

import java.util.Map;

/**
 * Created by WD on 2017/1/13.
 */

public class HttpUtil {
    private static HttpApi mHttpApi = HttpApiFactory.createHttpApi();


    public static void post(String url, Map<String, String> params, HttpCallback callback) {
        mHttpApi.post(url, params, callback);
    }

}
