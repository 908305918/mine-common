package com.lucy.common.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.VideoView;

import com.cms.jsbridge.JsBridge;
import com.lucy.common.R;
import com.lucy.common.jsaction.DefaultJsAction;
import com.lucy.common.util.ViewHelper;

/**
 * Created by WD on 2016/12/27.
 */

public class WebViewActivity extends Activity {
    private WebView mWebView;
    private FrameLayout mVideoContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_webview);
        mWebView = ViewHelper.getView(this, R.id.webview);
        mVideoContainer = ViewHelper.getView(this, R.id.video_container);

        initView();

        mWebView.loadUrl("file:///android_asset/test_js.html");
    }

    private void initView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);//启用支持javascript
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mWebView.setWebChromeClient(new CustomWebViewChromeClient());

        JsBridge.getInstance().init(this, mWebView)
                .addJsAction(DefaultJsAction.ACTION, DefaultJsAction.class);
    }


    private class CustomWebViewChromeClient extends WebChromeClient {
        private CustomViewCallback mCallBack;

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            fullScreen();
            mWebView.setVisibility(View.GONE);
            mVideoContainer.setVisibility(View.VISIBLE);
            mVideoContainer.addView(view);
            mCallBack = callback;
            super.onShowCustomView(view, callback);
        }

        @Override
        public void onHideCustomView() {
            fullScreen();
            if (mCallBack != null) {
                mCallBack.onCustomViewHidden();
            }
            mWebView.setVisibility(View.VISIBLE);
            mVideoContainer.removeAllViews();
            mVideoContainer.setVisibility(View.GONE);
            super.onHideCustomView();
        }
    }

    private void fullScreen() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
