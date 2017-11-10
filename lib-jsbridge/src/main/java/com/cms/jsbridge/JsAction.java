package com.cms.jsbridge;

import android.app.Activity;

/**
 * Created by WD on 2017/8/1.
 */

public interface JsAction {
    void handle(Activity activity, String data, JsCallback callback);
}
