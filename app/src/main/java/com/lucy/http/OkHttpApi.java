package com.lucy.http;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by WD on 2017/1/12.
 */

public class OkHttpApi implements HttpApi {
    private OkHttpClient mOkHttpClient;
    private Handler mHandler = new Handler(Looper.getMainLooper());


    private OkHttpApi() {
        mOkHttpClient = new OkHttpClient();
    }

    public OkHttpApi(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        } else {
            mOkHttpClient = okHttpClient;
        }
    }

    @Override
    public void get(String url, final HttpCallback callback) {
        get(url, null, callback);
    }

    @Override
    public void get(String url, Map<String, String> params, HttpCallback callback) {
        sendRequest(url, METHOD.GET, params, callback);
    }

    @Override
    public void post(String url, final HttpCallback callback) {
        post(url, null, callback);
    }

    @Override
    public void post(String url, Map<String, String> params, final HttpCallback callback) {
        sendRequest(url, METHOD.POST, params, callback);
    }

    private void sendRequest(String url, String method, Map<String, String> params, final HttpCallback callback) {
        Request request = null;
        if (method.equals(METHOD.GET)) {

            if (params != null) {
                url = appendParams(url, params);
            }

            request = new Request.Builder().url(url).build();

        } else if (method.equals(METHOD.POST)) {

            FormBody.Builder builder = new FormBody.Builder();

            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.add(entry.getKey(), entry.getValue());
                }
            }

            request = new Request.Builder().url(url).post(builder.build()).build();

        }

        Call call = mOkHttpClient.newCall(request);

        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(e);
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(result);
                    }
                });

            }
        });
    }

    private String appendParams(String url, Map<String, String> params) {
        if (url == null || params == null || params.isEmpty()) {
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            builder.appendQueryParameter(key, params.get(key));
        }
        return builder.build().toString();
    }


}
