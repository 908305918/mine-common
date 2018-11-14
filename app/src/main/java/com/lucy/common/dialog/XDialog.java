package com.lucy.common.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.lucy.common.R;


/**
 * Created by WD on 2017/3/20.
 */

public class XDialog {
    protected Context mContext;
    protected Dialog mDialog;
    protected Window mDialogWindow;

    protected ViewHolder mViewHolder;


    public XDialog(Context context, int themeRes, int layoutRes) {
        if (null == context) return;
        mContext = context;
        mDialog = new Dialog(context, themeRes);
        mDialogWindow = mDialog.getWindow();

        mViewHolder = ViewHolder.get(context, layoutRes);
        mDialog.setContentView(mViewHolder.getDialogView());
    }

    public XDialog(Context context, int layoutRes) {
        this(context, R.style.CustomDialog, layoutRes);
    }

    /**
     * 自定义布局
     *
     * @param creator
     */
    public XDialog setViewCreator(ViewCreator creator) {
        creator.onCreateView(mViewHolder);
        return this;
    }

    public Dialog obtainDialog() {
        return mDialog;
    }

    public ViewHolder obtainDialogViewHolder() {
        return mViewHolder;
    }

    /**
     * show dialog
     */
    public XDialog show() {
        if (mContext instanceof Activity) {
            Activity activity = (Activity) mContext;
            if (!activity.isFinishing()) {
                if (mDialog != null && !mDialog.isShowing()) {
                    mDialog.show();
                }
            }
        }
        return this;
    }

    /**
     * dismiss dialog
     */
    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    /**
     * set dialog width and height
     *
     * @param width
     * @param height
     * @return
     */
    public XDialog setWidthAndHeight(int width, int height) {
        WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
        wl.width = width;
        wl.height = height;
        mDialog.onWindowAttributesChanged(wl);
        return this;
    }

    /**
     * Dialog居中显示，Y轴可上下偏移
     *
     * @param offsetY Y轴偏移量
     * @return
     */
    public XDialog setOffsetY(int offsetY) {
        WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
        mDialogWindow.setGravity(Gravity.CENTER);
        wl.y = offsetY;
        mDialog.onWindowAttributesChanged(wl);
        return this;
    }


    public XDialog setOnKeyListener(DialogInterface.OnKeyListener listener) {
        mDialog.setOnKeyListener(listener);
        return this;
    }

    public XDialog setOnDismissListener(DialogInterface.OnDismissListener listener) {
        mDialog.setOnDismissListener(listener);
        return this;
    }

    public XDialog setOnCancelListener(DialogInterface.OnCancelListener listener) {
        mDialog.setOnCancelListener(listener);
        return this;
    }

    public XDialog setCancelable(boolean cancel) {
        mDialog.setCancelable(cancel);
        return this;
    }

    public XDialog setCanceledOnTouchOutside(boolean cancel) {
        mDialog.setCanceledOnTouchOutside(cancel);
        return this;
    }


    public interface ViewCreator {
        void onCreateView(ViewHolder holder);
    }
}
