package com.lucy.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;

public class MarqueeTextView extends TextView implements OnClickListener {
	private final static String TAG = MarqueeTextView.class.getSimpleName();
	private final int STEP = 1;// 步长

	private float mTextLength = 0f;// 文本长度
	private float mViewWidth = 0f;
	private float mTextX = 0f;// 文字的横坐标
	private float mTextY = 0f;// 文字的纵坐标
	private float temp_view_plus_text_length = 0.0f;// 用于计算的临时变量
	private float temp_view_plus_two_text_length = 0.0f;// 用于计算的临时变量
	public boolean isRuning = false;// 是否开始滚动
	private Paint mPaint = null;// 绘图样式
	private String mText = "";// 文本内容

	public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MarqueeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MarqueeTextView(Context context) {
		super(context);
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		setOnClickListener(this);
	}

	/** */
	/**
	 * 文本初始化，每次更改文本内容或者文本效果等之后都需要重新初始化一下
	 */
	public void init() {
		mPaint = getPaint();
		mPaint.setColor(getCurrentTextColor());
		mPaint.setTextSize(getTextSize());
		mText = getText().toString();
		mTextLength = mPaint.measureText(mText);
		mTextX = 0;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mViewWidth = getWidth();
		mTextY = getTextSize() + getPaddingTop();
	}

	/** */
	/**
	 * 开始滚动
	 */
	public void startScroll() {
		isRuning = true;
		invalidate();
	}

	/** */
	/**
	 * 停止滚动
	 */
	public void stopScroll() {
		isRuning = false;
		invalidate();
	}

	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawText(mText, mTextX, mTextY, mPaint);
		if (!isRuning) {
			return;
		}
		if (mTextLength > mViewWidth) {
			mTextX -= STEP;
			if (mTextX < mViewWidth - mTextLength)
				mTextX = 0;
			invalidate();
		}
	}

	@Override
	public void onClick(View v) {
		if (isRuning)
			stopScroll();
		else
			startScroll();
	}

	@Override
	public Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
		SavedState ss = new SavedState(superState);
		ss.mCurrentX = mTextX;
		ss.isRuning = isRuning;
		return ss;

	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {
		if (!(state instanceof SavedState)) {
			super.onRestoreInstanceState(state);
			return;
		}
		SavedState ss = (SavedState) state;
		super.onRestoreInstanceState(ss.getSuperState());

		mTextX = ss.mCurrentX;
		isRuning = ss.isRuning;

	}

	public static class SavedState extends BaseSavedState {
		public boolean isRuning = false;
		public float mCurrentX = 0.0f;

		SavedState(Parcelable superState) {
			super(superState);
		}

		@Override
		public void writeToParcel(Parcel out, int flags) {
			super.writeToParcel(out, flags);
			out.writeBooleanArray(new boolean[] { isRuning });
			out.writeFloat(mCurrentX);
		}

		public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {

			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}

			@Override
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}
		};

		private SavedState(Parcel in) {
			super(in);
			boolean[] b = null;
			in.readBooleanArray(b);
			if (b != null && b.length > 0)
				isRuning = b[0];
			mCurrentX = in.readFloat();
		}
	}

}
