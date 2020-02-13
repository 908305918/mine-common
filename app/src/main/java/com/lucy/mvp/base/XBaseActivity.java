package com.lucy.mvp.base;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by WD on 2017/1/6.
 */

public abstract class XBaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenterAndView();
    }

    protected abstract void initPresenterAndView();
}
