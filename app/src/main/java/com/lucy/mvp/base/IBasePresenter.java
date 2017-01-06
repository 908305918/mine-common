package com.lucy.mvp.base;

/**
 * Created by WD on 2017/1/6.
 */

public interface IBasePresenter<T extends IBaseView, E extends IBaseModel> {

    /**
     * 设置View和Model
     * @param v
     * @param m
     */
    void setViewAndModel(T v, E m);
}
