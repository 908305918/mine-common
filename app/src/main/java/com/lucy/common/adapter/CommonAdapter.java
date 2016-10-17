package com.lucy.common.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CommonAdapter<T> extends BaseAdapter {
	protected Context mContext;
	protected List<T> mDataList;
	protected int mItemLayoutId;
	protected MultiItemTypeSupport<T> mMultiItemTypeSupport;

	public CommonAdapter(Context context, List<T> dataList, int itemLayoutId) {
		this.mContext = context;
		this.mDataList = dataList;
		this.mItemLayoutId = itemLayoutId;
	}

	public CommonAdapter(Context context, List<T> dataList,
			MultiItemTypeSupport<T> multiItemTypeSupport) {
		this.mContext = context;
		this.mDataList = dataList;
		this.mMultiItemTypeSupport = multiItemTypeSupport;
	}

	@Override
	public int getCount() {
		int size = mDataList == null ? 0 : mDataList.size();
		return size;
	}

	@Override
	public T getItem(int position) {
		if (position >= mDataList.size()) {
			return null;
		}
		return mDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		if (mMultiItemTypeSupport != null) {
			return mMultiItemTypeSupport.getViewTypeCount();
		} else {
			return 1;
		}
	}

	@Override
	public int getItemViewType(int position) {
		if (mMultiItemTypeSupport != null) {
			return mMultiItemTypeSupport.getItemViewType(position, mDataList.get(position));
		} else {
			return 0;
		}
	}

	@Override
	public boolean isEnabled(int position) {
		return position < mDataList.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder = getViewHolder(position, convertView, parent);
		convert(viewHolder, mDataList.get(position));
		return viewHolder.getConvertView();
	}

	private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
		if (mMultiItemTypeSupport != null) {
			int layoutId = mMultiItemTypeSupport.getLayoutId(position, mDataList.get(position));
			return ViewHolder.get(mContext, convertView, parent, layoutId, position);
		}
		return ViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
	}

	public abstract void convert(ViewHolder viewHolder, T item);
}