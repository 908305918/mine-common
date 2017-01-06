package com.lucy.mvp.base;

import android.view.View;

/**
 * Created by WD on 2017/1/6.
 */

public interface IBaseView<T extends IBasePresenter> {

    /**
     * 设置
     *
     * @param view
     */
    void setRootView(View view);

    /**
     * 设置
     *
     * @param p
     */
    void setPresenter(T p);

    /**
     * 显示loading
     */
    void showLoadingView(String msg);

    /**
     * 隐藏loading
     */
    void dismissLoadingView();

    /**
     * 显示Toast
     */
    void showToast(String msg);

    /**
     * 查找子View
     *
     * @param viewId
     */
    <V extends View> V findView(int viewId);
}
