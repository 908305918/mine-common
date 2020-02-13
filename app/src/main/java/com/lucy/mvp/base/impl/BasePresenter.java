package com.lucy.mvp.base.impl;

import android.content.Context;

import com.lucy.mvp.base.IBasePresenter;

/**
 * Created by WD on 2017/1/6.
 */

public class BasePresenter<T extends BaseView, E extends BaseModel> implements IBasePresenter<T, E> {
    protected Context mContext;
    protected E mModel;
    protected T mView;

    public BasePresenter(Context context) {
        this.mContext = context;
    }

    @Override
    public void setViewAndModel(T v, E m) {
        this.mView = v;
        this.mModel = m;
    }
}
