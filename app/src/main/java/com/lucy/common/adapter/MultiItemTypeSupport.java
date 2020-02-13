package com.lucy.common.adapter;

public interface MultiItemTypeSupport<T> {
	int getLayoutId(int position, T item);

	int getViewTypeCount();

	int getItemViewType(int postion, T item);
}
