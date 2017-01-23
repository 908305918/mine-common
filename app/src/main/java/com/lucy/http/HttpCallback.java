package com.lucy.http;

/**
 * Created by WD on 2017/1/12.
 */

public interface HttpCallback {

    void onSuccess(String response);

    void onFailure(Throwable error);

}
