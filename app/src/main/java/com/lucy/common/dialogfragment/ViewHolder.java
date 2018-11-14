package com.lucy.common.dialogfragment;

import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ViewHolder {
    private final SparseArray<View> mViews;
    private View mConvertView;

    private ViewHolder(View view) {
        this.mViews = new SparseArray<>();
        mConvertView = view;
    }

    public static ViewHolder get(View view) {
        return new ViewHolder(view);
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * Through control the Id of the access to control, if not join views
     *
     * @param viewId
     * @return
     */

    public <T extends View> T findView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * set the string for TextView
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, CharSequence text) {
        TextView textView = findView(viewId);
        textView.setText(text);
        return this;
    }

    /**
     * set view visible
     *
     * @param viewId
     * @param visibility
     * @return
     */
    public ViewHolder setVisibility(int viewId, int visibility) {
        View view = findView(viewId);
        view.setVisibility(visibility);
        return this;
    }


    /**
     * set clickListener of View
     *
     * @param viewId
     * @param listener
     */
    public ViewHolder setOnClickListener(int viewId, OnClickListener listener) {
        View view = findView(viewId);
        view.setOnClickListener(listener);
        return this;
    }


}
