package com.lucy.http;

import java.util.Map;

/**
 * Created by WD on 2017/1/12.
 */

public interface HttpApi {

    void get(String url, HttpCallback callback);

    void get(String url,  Map<String, String> params,HttpCallback callback);

    void post(String url, HttpCallback callback);

    void post(String url, Map<String, String> params, HttpCallback callback);

    interface METHOD {
        String GET = "GET";
        String POST = "POST";
    }

}
