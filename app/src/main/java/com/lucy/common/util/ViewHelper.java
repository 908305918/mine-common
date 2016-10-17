package com.lucy.common.util;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHelper {
	private final static String TAG = "ViewHelper";

	public static <T extends View> T getView(Activity activity, int viewId) {
		try {
			return (T) activity.findViewById(viewId);
		} catch (Exception e) {
			Log.e(TAG, "Could not cast View to concrete class.", e);
		}
		return null;
	}

	public static <T extends View> T getView(View rootView, int viewId) {
		try {
			return (T) rootView.findViewById(viewId);
		} catch (Exception e) {
			Log.e(TAG, "Could not cast View to concrete class.", e);
		}
		return null;
	}

	/**
	 * 设置 TextView的Text
	 * 
	 * @param activity
	 * @param viewId
	 * @param content
	 */
	public static void setText(Activity activity, int viewId, CharSequence content) {
		TextView textView = getView(activity, viewId);
		if (textView != null) {
			textView.setText(content);
		}

	}

	/**
	 * 设置 TextView的Text
	 * 
	 * @param rootView
	 * @param viewId
	 * @param content
	 */
	public static void setText(View rootView, int viewId, CharSequence content) {
		TextView textView = getView(rootView, viewId);
		if (textView != null) {
			textView.setText(content);
		}
	}

	/**
	 * 设置view的监听器
	 * 
	 * @param activity
	 * @param viewId
	 * @param l
	 */
	public static void setOnClickListener(Activity activity, int viewId, OnClickListener l) {
		View view = getView(activity, viewId);
		if (view != null) {
			view.setOnClickListener(l);
		}
	}

	/**
	 * 设置view的监听器
	 * 
	 * @param rootView
	 * @param viewId
	 * @param l
	 */
	public static void setOnClickListener(View rootView, int viewId, OnClickListener l) {
		View view = getView(rootView, viewId);
		if (view != null) {
			view.setOnClickListener(l);
		}
	}

	/**
	 * 设置view的visibility
	 * 
	 * @param rootView
	 * @param viewId
	 * @param l
	 */
	public static void setVisibility(Activity activity, int viewId, int visibility) {
		View view = getView(activity, viewId);
		if (view != null) {
			view.setVisibility(visibility);
		}
	}

	/**
	 * 设置view的visibility
	 * 
	 * @param rootView
	 * @param viewId
	 * @param l
	 */
	public static void setVisibility(View rootView, int viewId, int visibility) {
		View view = getView(rootView, viewId);
		if (view != null) {
			view.setVisibility(visibility);
		}
	}
	/**
	 * 设置ImageView的ImageResource
	 * 
	 * @param rootView
	 * @param viewId
	 * @param l
	 */
	public static void setImageResource(Activity activity, int viewId, int resId) {
		ImageView imageView = getView(activity, viewId);
		if (imageView != null) {
			imageView.setImageResource(resId);
		}
	}
	/**
	 * 设置ImageView的ImageResource
	 * 
	 * @param rootView
	 * @param viewId
	 * @param l
	 */
	public static void setImageResource(View rootView, int viewId, int resId) {
		ImageView imageView = getView(rootView, viewId);
		if (imageView != null) {
			imageView.setImageResource(resId);
		}
	}

}
