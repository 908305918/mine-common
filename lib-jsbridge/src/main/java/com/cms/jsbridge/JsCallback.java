package com.cms.jsbridge;

import android.webkit.WebView;

/**
 * Created by WD on 2017/8/1.
 */

public class JsCallback {
    private WebView mWebView;
    private String mCallback;
    private String mCallbackId;

    public JsCallback(WebView webView, String callback, String callbackId) {
        mWebView = webView;
        mCallback = callback;
        mCallbackId = callbackId;
    }

    public void onCallback(final String data) {
        mWebView.post(new Runnable() {
            public void run() {
                String url = "javascript:" + mCallback + "('" + mCallbackId +"','"+data+ "')";
                mWebView.loadUrl(url);
            }
        });
    }

}
