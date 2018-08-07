package com.lucy.common.activity;

import java.util.ArrayList;
import java.util.List;

import com.lucy.common.R;
import com.lucy.common.adapter.CommonAdapter;
import com.lucy.common.adapter.ViewHolder;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class TestCommonAdapterActivity extends Activity {
	private ListView listView;
	private CommonAdapter<Object> adapter;
	private List<Object> dataList = new ArrayList<Object>();
	private View footView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_common_adapter);
		listView = findViewById(R.id.listview);
		footView = createIndeterminateProgressView();
		getData();
		adapter = new CommonAdapter<Object>(this, dataList, R.layout.item_textview) {
			@Override
			public void convert(ViewHolder viewHolder, Object item) {
				viewHolder.setText(R.id.content, item.toString());
			}
		};
		listView.addFooterView(footView);
		listView.setAdapter(adapter);
		listView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					if (view.getLastVisiblePosition() == view.getCount() - 1) {
						view.postDelayed(new Runnable() {
							@Override
							public void run() {
								getData();
								adapter.notifyDataSetChanged();
							}
						}, 5000);
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
					int totalItemCount) {
			}
		});

	}

	private void getData() {
		for (int i = 0; i < 20; i++) {
			dataList.add(new Object());
		}
	}

	private View createIndeterminateProgressView() {
		FrameLayout container = new FrameLayout(this);
		container.setForegroundGravity(Gravity.CENTER);
		ProgressBar progress = new ProgressBar(this);
		container.addView(progress);
		return container;
	}

}
