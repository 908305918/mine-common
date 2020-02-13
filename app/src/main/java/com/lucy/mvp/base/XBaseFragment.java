package com.lucy.mvp.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by WD on 2017/1/6.
 */

public abstract class XBaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenterAndView();
    }

    protected abstract void initPresenterAndView();
}
