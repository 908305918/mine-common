package com.lucy.common.view;

import android.content.Context;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BestWebView extends WebView {

	public BestWebView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

}

class MWebViewClient extends WebViewClient{
	@Override
	public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
		// TODO Auto-generated method stub
		return super.shouldInterceptRequest(view, request);
	}
}
