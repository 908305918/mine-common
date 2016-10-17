package com.lucy.common.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * 通用的ViewHolder
 * 
 * @author Administrator
 * 
 */
public class ViewHolder {
	private final SparseArray<View> mViews;
	private View mConvertView;
	private int mLayoutId;
	private int mPosition;

	private ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
		this.mViews = new SparseArray<View>();
		this.mLayoutId = layoutId;
		this.mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		this.mConvertView.setTag(this);
		this.mPosition = position;
	}

	/**
	 * 获取一个ViewHolder对象
	 * 
	 * @param context
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @param position
	 * @return
	 */
	public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId,
			int position) {
		if (convertView == null) {
			return new ViewHolder(context, parent, layoutId, position);
		} else {
			ViewHolder viewHolder = (ViewHolder) convertView.getTag();
			if (viewHolder.mLayoutId != layoutId) {
				return new ViewHolder(context, parent, layoutId, position);
			}
			viewHolder.mPosition = position;
			return viewHolder;
		}
	}

	/**
	 * 获取Position
	 * 
	 * @return
	 */
	public int getPosition() {
		return mPosition;
	}

	/**
	 * 获取LayoutID
	 * 
	 * @return
	 */
	public int getLayoutID() {
		return mLayoutId;
	}

	/**
	 * 根据控件ID获取view，没有则加入view
	 * 
	 * @param viewId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	/**
	 * 获取convertView
	 * 
	 * @return
	 */
	public View getConvertView() {
		return mConvertView;
	}

	/**
	 * 设置TextView的内容
	 * 
	 * @param viewId
	 * @param content
	 */
	public ViewHolder setText(int viewId, CharSequence content) {
		TextView view = getView(viewId);
		if (view != null) {
			view.setText(content);
		}
		return this;
	}

	/**
	 * Sets the visibility of the view.
	 * 
	 * @param viewId
	 * @param visibility
	 * @return
	 */
	public ViewHolder setVisibility(int viewId, int visibility) {
		View view = getView(viewId);
		if (view != null) {
			view.setVisibility(visibility);
		}
		return this;
	}

	/**
	 * Sets the ImageResource of the ImageView.
	 * 
	 * @param viewId
	 * @param visibility
	 * @return
	 */
	public ViewHolder setImageResource(int viewId, int resId) {
		ImageView imageView = getView(viewId);
		if (imageView != null) {
			imageView.setImageResource(resId);
		}
		return this;
	}

	/**
	 * 设置View的click listener
	 * 
	 * @param viewId
	 * @param item
	 * @param listener
	 */
	public ViewHolder setOnClickListener(int viewId, OnClickListener listener) {
		View view = getView(viewId);
		if (view != null) {
			view.setOnClickListener(listener);
		}
		return this;
	}

	/**
	 * Sets the tag of the view.
	 * 
	 * @param viewId
	 * @param tag
	 */
	public ViewHolder setTag(int viewId, Object tag) {
		View view = getView(viewId);
		if (view != null) {
			view.setTag(tag);
		}
		return this;
	}

	/**
	 * Sets the checked status of a checkable.
	 * 
	 * @param viewId
	 * @param checked
	 */
	public ViewHolder setChecked(int viewId, boolean checked) {
		Checkable view = (Checkable) getView(viewId);
		if (view != null) {
			view.setChecked(checked);
		}
		return this;
	}

	/**
	 * Sets the checked listener of a CompoundButton.
	 * 
	 * @param viewId
	 * @param listener
	 */
	public ViewHolder setOnCheckedChangeListener(int viewId, OnCheckedChangeListener listener) {
		CompoundButton view = getView(viewId);
		if (view != null) {
			view.setOnCheckedChangeListener(listener);
		}
		return this;
	}

}
