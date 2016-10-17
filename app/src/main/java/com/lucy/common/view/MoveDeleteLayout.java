package com.lucy.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

public class MoveDeleteLayout extends HorizontalScrollView {
	private int screenWidth;
	private ViewGroup wrapViewGroup;
	private View actionView;// 操作按钮的视图
	private View contentView;// 显示内容的视图

	public MoveDeleteLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		screenWidth = context.getResources().getDisplayMetrics().widthPixels;
	}

	public MoveDeleteLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MoveDeleteLayout(Context context) {
		this(context, null);
	}

	boolean isFisrt = true;

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (isFisrt) {
			init();
			isFisrt = false;
		}
	}

	private void init() {
		wrapViewGroup = (ViewGroup) getChildAt(0);
		contentView = wrapViewGroup.getChildAt(0);
		actionView = wrapViewGroup.getChildAt(1);
		contentView.getLayoutParams().width = screenWidth;
		setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return handleOnTouch(v, event);
			}
		});
	}

	private int mDownX;

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mDownX = (int) ev.getX();
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

	private boolean handleOnTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			// 获得操作区域的长度
			int actionW = actionView.getWidth();
			// 获得HorizontalScrollView滑动的水平方向值.
			int scrollX = this.getScrollX();
			int currentX = (int) event.getX();
			System.out.println(currentX + "=" + mDownX + "=" + (currentX - mDownX));
			if (Math.abs(currentX - mDownX) < 50) {// 手指移动距离<50,返回之前状态
				// 水平方向的移动值<操作区域的长度的一半,复原
				if (scrollX < actionW / 2) {
					this.smoothScrollTo(0, 0);
					return false;
				} else {// 显示操作区域
					this.smoothScrollTo(actionW, 0);
					return true;
				}
			} else {
				// 注意使用smoothScrollTo,这样效果看起来比较圆滑,不生硬
				if (currentX - mDownX > 0) {// 右移
					this.smoothScrollTo(0, 0);// 复原
				} else {
					this.smoothScrollTo(actionW, 0);
				}
				return true;
			}
		}
		return false;
	}

}
