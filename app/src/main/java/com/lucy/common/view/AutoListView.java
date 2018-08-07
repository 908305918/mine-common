package com.lucy.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.lucy.common.R;
import com.lucy.common.util.ViewHelper;

public class AutoListView extends ListView implements OnScrollListener {
	private View footerView;
	private int mTotalItemCount;
	private int mPageSize = 2;

	private boolean loadEnable = true;// 开启或者关闭加载更多功能
	private boolean isLoading;// 是否正在加载
	private boolean isLoadFull; // 数据是否加载完毕

	private OnLoadListener onLoadListener;

	public AutoListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public AutoListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public AutoListView(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		footerView = LayoutInflater.from(context).inflate(R.layout.listview_footer, null);
		this.addFooterView(footerView);
		this.setOnScrollListener(this);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		ifNeedLoad(view, scrollState);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
			int totalItemCount) {
		this.mTotalItemCount = totalItemCount;
	}

	// 根据listview滑动的状态判断是否需要加载更多
	private void ifNeedLoad(AbsListView view, int scrollState) {
		if (!loadEnable) {
			return;
		}
		try {
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && !isLoading && !isLoadFull) {
				if (view.getLastVisiblePosition() >= mTotalItemCount - mPageSize / 2) {//滑动到一半时自动加载
					onLoad();
					isLoading = true;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 用于加载更多结束后的回调
	public void onLoadComplete() {
		isLoading = false;
	}

	public void setOnLoadListener(OnLoadListener listener) {
		this.onLoadListener = listener;
	}

	public void setHasMoreData(boolean hasMoreData) {
		isLoadFull = !hasMoreData;
		if (isLoadFull) {
			ViewHelper.setVisibility(footerView, R.id.loading, View.GONE);
			ViewHelper.setText(footerView, R.id.loading_text, "已经到底啦！");
		} else {
			ViewHelper.setVisibility(footerView, R.id.loading, View.VISIBLE);
			ViewHelper.setText(footerView, R.id.loading_text, "正在加载");
		}
	}

	public void setPageSize(int pagesize) {
		this.mPageSize = pagesize;
	}

	private void onLoad() {
		if (onLoadListener != null) {
			onLoadListener.onLoad();
		}
	}

	/*
	 * 定义加载更多接口
	 */
	public interface OnLoadListener {
		void onLoad();
	}

}
