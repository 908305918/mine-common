package com.cms.jsbridge;

import android.app.Activity;
import android.webkit.WebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WD on 2017/8/1.
 */

public class JsHandler {
    private Activity mActivity;
    private WebView mWebView;
    private Map<String, Class<? extends JsAction>> mActionMap;

    public JsHandler(Activity context, WebView webView) {
        mActionMap = new HashMap<>();
        mActivity = context;
        mWebView = webView;
    }

    public boolean handle(String message, String callback) {
        try {
            JSONObject jsonObj = new JSONObject(message);
            String action = jsonObj.optString("action");
            String params = jsonObj.optString("data");
            String callbackId = jsonObj.optString("callbackId");
            for (String mAction : mActionMap.keySet()) {
                if (mAction.equals(action)) {
                    try {
                        JsAction mJsAction = mActionMap.get(mAction).newInstance();
                        if (mJsAction != null) {
                            JsCallback jsCallback = new JsCallback(mWebView, callback,callbackId);
                            mJsAction.handle(mActivity, params, jsCallback);
                        }
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Map<String, Class<? extends JsAction>> getActionMap() {
        return mActionMap;
    }

    public void destroy() {
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
        }
        if (mActivity != null) {
            mActivity = null;
        }
        if (mActionMap != null) {
            mActionMap.clear();
        }
    }
}
