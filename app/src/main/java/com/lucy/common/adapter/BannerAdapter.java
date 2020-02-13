package com.lucy.common.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * 无限循环滚动的广告页
 * 
 * @author Administrator
 * 
 */
public class BannerAdapter extends PagerAdapter {
	public final int BANNER_SIZE = Integer.MAX_VALUE;
	private int mCount;// 真实的数量

	private List<View> mViews;

	public BannerAdapter(Context context, List<View> views) {
		mViews = views;
		mCount = mViews.size();

	}

	@Override
	public int getCount() {
		return BANNER_SIZE;
	}

	@Override
	public boolean isViewFromObject(View v, Object o) {
		return v == o;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = mViews.get(position %= mCount);
		container.addView(view);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mViews.get(position %= mCount));
	}

	// @Override
	// public void finishUpdate(ViewGroup container) {
	// ViewPager viewPager = (ViewPager) container;
	// int position = viewPager.getCurrentItem();
	// if (position == 0) {
	// position = mCount;
	// viewPager.setCurrentItem(position, false);
	// } else if (position == FAKE_BANNER_SIZE - 1) {
	// position = (FAKE_BANNER_SIZE - 1) % mCount;
	// viewPager.setCurrentItem(position, false);
	// }
	// super.finishUpdate(container);
	// }

}
