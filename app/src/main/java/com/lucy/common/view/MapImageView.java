package com.lucy.common.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.json.JSONObject;

import com.lucy.common.util.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MapImageView extends ImageView {
	private Bitmap mMapBitmap, mTextBitmap, mBitmap;
	private int mWidth, mHeight, mViewWidth, mViewHeight, mScreenWidth;
	private float mRatio, downX, downY;
	private Map<String, JSONObject> map = new HashMap<String, JSONObject>();

	public MapImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public MapImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MapImageView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		mScreenWidth = context.getResources().getDisplayMetrics().widthPixels;
		mViewWidth = mScreenWidth;
		// initMap();
	}

	public void setMapAndTextBitmap(Bitmap mapBitmap, Bitmap textBitmap) {
		mMapBitmap = mapBitmap;
		mTextBitmap = textBitmap;
		mBitmap = combineBitmap(mapBitmap, textBitmap);
		mWidth = mBitmap.getWidth();
		mHeight = mBitmap.getHeight();
		setImageBitmap(mBitmap);
	}

	public void setDataMap(Map<String, JSONObject> map) {
		this.map = map;
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mRatio = mViewWidth * 1.0f / mWidth;
		mViewHeight = (int) (mHeight * mRatio);
		setMeasuredDimension(mViewWidth, mViewHeight);
	}

	public Bitmap getTransparentBitmap(Bitmap sourceImg, int number) {
		int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];
		sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0, sourceImg.getWidth(),
				sourceImg.getHeight());// 获得图片的ARGB值
		number = number * 255 / 100;
		for (int i = 0; i < argb.length; i++) {
			if (argb[i] != 0) {
				argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);
			}
		}
		sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(), sourceImg.getHeight(),
				Config.ARGB_8888);
		return sourceImg;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX = event.getX();
			downY = event.getY();
			int clickColor = getBitmapColorWithPoint(mMapBitmap, getActualPoint(downX, downY));
			if (map.containsKey(Integer.toHexString(clickColor).toUpperCase())) {
				int[] pixels = new int[mWidth * mHeight];
				Bitmap bitmap = mMapBitmap.copy(Bitmap.Config.ARGB_8888, true);
				bitmap.getPixels(pixels, 0, mWidth, 0, 0, mWidth, mHeight);
				Point point = getActualPoint(downX, downY);
				fillColor(pixels, point.x, point.y, mWidth, mHeight, clickColor,
						changeColorBrightness(clickColor, -50));
				bitmap.setPixels(pixels, 0, mWidth, 0, 0, mWidth, mHeight);
				setImageDrawable(new BitmapDrawable(combineBitmap(bitmap, mTextBitmap)));
			}
			return true;
		case MotionEvent.ACTION_UP:
			JSONObject department = getClickDepartment(downX, downY);
			if (department != null ) {
				 Utility.showToast(getContext(), department.optString("name"));
			}
			return true;
		}
		return super.onTouchEvent(event);
	}

	// 获取点击点在图片上的坐标
	private Point getActualPoint(float x, float y) {
		Point point = new Point();
		point.x = (int) (x / mRatio);
		point.y = (int) (y / mRatio);
		return point;
	}

	private int getBitmapColorWithPoint(Bitmap bitmap, Point p) {
		return bitmap.getPixel(p.x, p.y);
	}

	private JSONObject getClickDepartment(float x, float y) {
		int color = getBitmapColorWithPoint(mMapBitmap, getActualPoint(x, y));
		return map.get(Integer.toHexString(color).toUpperCase());
	}

	/**
	 * 合并两张bitmap为一张
	 * 
	 * @param background
	 * @param foreground
	 * @return Bitmap
	 */
	public static Bitmap combineBitmap(Bitmap background, Bitmap foreground) {
		if (background == null) {
			return null;
		}
		if (foreground == null) {
			return background;
		}
		int bgWidth = background.getWidth();
		int bgHeight = background.getHeight();
		int fgWidth = foreground.getWidth();
		int fgHeight = foreground.getHeight();
		Bitmap newmap = Bitmap.createBitmap(bgWidth, bgHeight, Config.ARGB_8888);
		Canvas canvas = new Canvas(newmap);
		canvas.drawBitmap(background, 0, 0, null);
		canvas.drawBitmap(foreground, (bgWidth - fgWidth) / 2, (bgHeight - fgHeight) / 2, null);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		return newmap;
	}

	/**
	 * 改变颜色的亮度
	 * 
	 * @param color
	 * @param brightness
	 * @return
	 */
	private int changeColorBrightness(int color, int brightness) {
		int newR = Color.red(color) + brightness;
		int newG = Color.green(color) + brightness;
		int newB = Color.blue(color) + brightness;
		if (newR > 255) {
			newR = 255;
		}
		if (newR < 0) {
			newR = 0;
		}
		if (newG > 255) {
			newG = 255;
		}
		if (newG < 0) {
			newG = 0;
		}
		if (newB > 255) {
			newB = 255;
		}
		if (newB < 0) {
			newB = 0;
		}
		return Color.rgb(newR, newG, newB);
	}

	private Stack<Point> mStacks = new Stack<Point>();

	private void fillColor(int[] pixels, int x, int y, int w, int h, int pixel, int newColor) {
		// 步骤1：将种子点(x, y)入栈；
		mStacks.push(new Point(x, y));
		// 步骤2：判断栈是否为空，
		// 如果栈为空则结束算法，否则取出栈顶元素作为当前扫描线的种子点(x, y)，
		// y是当前的扫描线；
		while (!mStacks.isEmpty()) {
			/**
			 * 步骤3：从种子点(x, y)出发，沿当前扫描线向左、右两个方向填充，
			 * 直到边界。分别标记区段的左、右端点坐标为xLeft和xRight；
			 */
			Point seed = mStacks.pop();
			int count = fillLineLeft(pixels, seed.x, seed.y, w, h, pixel, newColor);
			int left = seed.x - count + 1;
			count = fillLineRight(pixels, seed.x + 1, seed.y, w, h, pixel, newColor);
			int right = seed.x + count;
			/**
			 * 步骤4： 分别检查与当前扫描线相邻的y - 1和y + 1两条扫描线在区间[xLeft, xRight]中的像素，
			 * 从xRight开始向xLeft方向搜索，假设扫描的区间为AAABAAC（A为种子点颜色），
			 * 那么将B和C前面的A作为种子点压入栈中，然后返回第（2）步；
			 */
			if (seed.y - 1 > 0) {
				findSeedInNewLine(pixels, pixel, w, h, seed.y - 1, left, right);
			}
			if (seed.y + 1 < h) {
				findSeedInNewLine(pixels, pixel, w, h, seed.y + 1, left, right);
			}
		}
	}

	/**
	 * 在新行找种子节点
	 */
	private void findSeedInNewLine(int[] pixels, int pixel, int w, int h, int y, int left, int right) {
		// 获得该行扫描的开始索引
		int begin = y * w + left;
		// 获得该行扫描的结束索引
		int end = y * w + right;
		boolean hasSeed = false;
		int rx = -1, ry = -1;
		ry = y;
		while (end >= begin) {
			if (pixels[end] == pixel) {
				if (!hasSeed) {
					rx = end % w;
					mStacks.push(new Point(rx, ry));
					hasSeed = true;
				}
			} else {
				hasSeed = false;
			}
			end--;
		}
	}

	/**
	 * 往左填色，返回填色的数量值
	 * 
	 * @return
	 */
	private int fillLineLeft(int[] pixels, int x, int y, int w, int h, int pixel, int newColor) {
		int count = 0;
		while (x > 0) {
			int index = y * w + x;
			if (pixels[index] == pixel) {
				pixels[index] = newColor;
				count++;
				x--;
			} else {
				break;
			}
		}
		return count;
	}

	/**
	 * 往右填色，返回填色的数量值
	 * 
	 * @return
	 */
	private int fillLineRight(int[] pixels, int x, int y, int w, int h, int pixel, int newColor) {
		int count = 0;
		while (x < w) {
			int index = y * w + x;
			if (pixels[index] == pixel) {
				pixels[index] = newColor;
				count++;
				x++;
			} else {
				break;
			}
		}
		return count;
	}

	private void fillColor2(int[] pixels, int x, int y, int w, int h, int pixel, int newColor) {
		mStacks.push(new Point(x, y));
		while (!mStacks.isEmpty()) {
			Point seed = mStacks.pop();
			int index = seed.y * w + seed.x;
			pixels[index] = newColor;
			if (seed.y > 0) {
				int top = index - w;
				if (pixels[top] == pixel) {
					mStacks.push(new Point(seed.x, seed.y - 1));
				}
			}
			if (seed.y < h - 1) {
				int bottom = index + w;
				if (pixels[bottom] == pixel) {
					mStacks.push(new Point(seed.x, seed.y + 1));
				}
			}
			if (seed.x > 0) {
				int left = index - 1;
				if (pixels[left] == pixel) {
					mStacks.push(new Point(seed.x - 1, seed.y));
				}
			}
			if (seed.x < w - 1) {
				int right = index + 1;
				if (pixels[right] == pixel) {
					mStacks.push(new Point(seed.x + 1, seed.y));
				}
			}
		}
	}

	private void fillColor1(int[] pixels, int x, int y, int w, int h, int pixel, int newColor) {
		int index = y * w + x;
		if (pixels[index] != pixel || x < 0 || y < 0 || x >= w || y >= h) {
			return;
		}
		pixels[index] = newColor;
		fillColor(pixels, x - 1, y, w, h, pixel, newColor);
		fillColor(pixels, x + 1, y, w, h, pixel, newColor);
		fillColor(pixels, x, y - 1, w, h, pixel, newColor);
		fillColor(pixels, x, y + 1, w, h, pixel, newColor);
	}
}
