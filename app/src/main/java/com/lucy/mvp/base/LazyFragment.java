package com.lucy.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by WD on 2017/1/6.
 * 延时加载数据的Fragment
 * 界面显示时才加载数据
 * 适用于ViewPager中加载
 */

public abstract class LazyFragment extends Fragment {

    protected View mRootView;

    /**
     * 控件是否初始化完成
     */
    protected boolean isViewCreated;

    /**
     * 数据是否已加载
     */
    protected boolean isLoadData;

    /**
     * 界面是否显示
     */
    protected boolean isVisibleToUser = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = getRootView(inflater, container, savedInstanceState);
        isViewCreated = true;
        return mRootView;
    }

    public abstract View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser && isViewCreated) {
            isLoadData = loadData(isLoadData);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isVisibleToUser && !isLoadData) {
            isLoadData = loadData(isLoadData);
        }
    }

    /**
     * 子类实现加载数据的方法
     * @param isLoadData
     * @return
     */
    public abstract boolean loadData(boolean isLoadData);


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
        isLoadData = false;
    }

}
