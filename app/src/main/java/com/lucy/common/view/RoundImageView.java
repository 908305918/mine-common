package com.lucy.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.lucy.common.R;

/**
 * 圆角图片控件
 * 
 * @author Administrator
 * 
 */
public class RoundImageView extends ImageView {

	public static final int TYPE_CIRCLE = 0;
	public static final int TYPE_ROUND = 1;
	/**
	 * 图片的类型，圆形or圆角
	 */
	private int type = TYPE_ROUND;
	/**
	 * 绘图的Paint
	 */
	private Paint mBitmapPaint = new Paint();
	/**
	 * 渲染图像，使用图像为绘制图形着色
	 */
	private BitmapShader mBitmapShader;
	/**
	 * 矩阵，主要用于缩小放大
	 */
	private Matrix mMatrix;
	/**
	 * 圆角的范围
	 */
	private RectF mRoundRect;
	/**
	 * 圆角的大小
	 */
	private int mBorderRadius;
	/**
	 * View的宽度（圆形时）
	 */
	private int mWidth;
	/**
	 * 圆形半径
	 */
	private int mRadius;

	public RoundImageView(Context context) {
		this(context, null);
	}

	public RoundImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mMatrix = new Matrix();
		mBitmapPaint = new Paint();
		mBitmapPaint.setAntiAlias(true);
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
		mBorderRadius = array.getDimensionPixelSize(R.styleable.RoundImageView_borderRadius,
				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources()
						.getDisplayMetrics()));
		type = array.getInt(R.styleable.RoundImageView_type, TYPE_CIRCLE);
		array.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (type == TYPE_CIRCLE) {
			mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
			mRadius = mWidth / 2;
			setMeasuredDimension(mWidth, mWidth);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// super.onDraw(canvas);
		if (null == getDrawable()) {
			return;
		}
		initBitmapShader();
		if (type == TYPE_CIRCLE) {
			canvas.drawCircle(mRadius, mRadius, mRadius, mBitmapPaint);
		} else {
			canvas.drawRoundRect(mRoundRect, mBorderRadius, mBorderRadius, mBitmapPaint);
		}

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mRoundRect = new RectF(0, 0, w, h);
	}

	private void initBitmapShader() {
		Drawable drawable = getDrawable();
		if (drawable == null) {
			return;
		}
		Bitmap bmp = getBitmapFromDrawable(drawable);
		mBitmapShader = new BitmapShader(bmp, TileMode.CLAMP, TileMode.CLAMP);
		updateShaderMatrix(bmp);
		mBitmapShader.setLocalMatrix(mMatrix);
		mBitmapPaint.setShader(mBitmapShader);
	}

	private Bitmap getBitmapFromDrawable(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			BitmapDrawable bd = (BitmapDrawable) drawable;
			return bd.getBitmap();
		}
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * 这个函数为设置BitmapShader的Matrix参数，设置最小缩放比例，平移参数。 作用：保证图片损失度最小和始终绘制图片正中央的那部分
	 */
	private void updateShaderMatrix(Bitmap bitmap) {
		float dx = 0, dy = 0, scale = 1.0f;
		int bitmapW = bitmap.getWidth();
		int bitmapH = bitmap.getHeight();
		mMatrix.set(null);
		if (type == TYPE_ROUND) {
			if (!(bitmapW == getWidth() && bitmapH == getHeight())) {
				// 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；
				// 缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
				scale = Math.max(getWidth() * 1.0f / bitmapW, getHeight() * 1.0f / bitmapH);
			}
		} else {
			if (bitmapH > bitmapW) {// 高大于宽 图片向上平移使其居中
				scale = mWidth * 1.0f / bitmapW;
				dy = (mWidth - bitmapH * scale) * 0.5f;
			} else {
				scale = mWidth * 1.0f / bitmapH;
				dx = (mWidth - bitmapW * scale) * 0.5f;
			}
		}
		mMatrix.setScale(scale, scale);
		mMatrix.postTranslate(dx, dy);
	}
}
