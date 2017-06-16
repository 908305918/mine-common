package com.lucy.common.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.threshold.rxbus2.RxBus;
import com.threshold.rxbus2.annotation.RxSubscribe;
import com.threshold.rxbus2.util.EventThread;

/**
 * Created by YJB on 2017/5/21.
 */

public class BaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isRegisterRxBus()) {
            RxBus.getInstance().register(this);
        }
    }

    protected boolean isRegisterRxBus() {
        return false;
    }

    @RxSubscribe(observeOnThread = EventThread.MAIN)
    public void onReceiveEvent(Object object){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRegisterRxBus()) {
            RxBus.getInstance().unregister(this);
        }
    }
}
