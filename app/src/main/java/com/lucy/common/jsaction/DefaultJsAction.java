package com.lucy.common.jsaction;

import android.app.Activity;

import com.cms.jsbridge.JsAction;
import com.cms.jsbridge.JsCallback;

/**
 * Created by WD on 2017/8/1.
 */

public class DefaultJsAction implements JsAction {
    public static final String ACTION = "default";

    @Override
    public void handle(Activity activity, String data, JsCallback callback) {
        callback.onCallback("输入参数：" + data);
    }
}
