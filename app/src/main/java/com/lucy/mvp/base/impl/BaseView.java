package com.lucy.mvp.base.impl;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lucy.mvp.base.IBaseView;

/**
 * Created by WD on 2017/1/6.
 */

public abstract class BaseView<T extends BasePresenter> implements IBaseView<T> {
    private static final String TAG = "BaseView";

    protected Context mContext;
    protected View mRootView;
    protected BasePresenter mPresenter;

    public BaseView(Context context) {
        mContext = context;
    }

    @Override
    public void setRootView(View view) {
        mRootView = view;
    }

    @Override
    public void setPresenter(T presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public <V extends View> V findView(int viewId) {
        if (mRootView != null) {
            try {
                return (V) mRootView.findViewById(viewId);
            } catch (Exception e) {
                Log.e(TAG, "Could not cast View to concrete class.", e);
            }
        }
        return null;
    }

    @Override
    public void showToast(String msg) {
        if (mContext != null && !TextUtils.isEmpty(msg)) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
