package com.lucy.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by WD on 2017/7/18.
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private Context mContext;
    private int mItemLayoutId;
    private List<T> mDataList;

    public CommonAdapter(Context context, List<T> datas, int layoutId) {
        mContext = context;
        mItemLayoutId = layoutId;
        mDataList = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.createViewHolder(mContext, parent, mItemLayoutId);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.updatePosition(position);
        convert(holder, mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public abstract void convert(ViewHolder holder, T t);
}
