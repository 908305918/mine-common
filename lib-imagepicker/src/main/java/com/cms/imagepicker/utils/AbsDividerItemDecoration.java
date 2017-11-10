package com.cms.imagepicker.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by WD on 2017/4/17.
 */

public abstract class AbsDividerItemDecoration extends RecyclerView.ItemDecoration {

    private final int DIVIDER_COLOR = 0xFFD0D0D0;
    private final float DIVIDER_SIZE = 1;

    /**
     * item之间分割线的size，默认为1
     */
    private int mDividerSize = 1;
    /**
     * 绘制item分割线的画笔
     */
    private Paint mPaint;

    public AbsDividerItemDecoration(Context context, float dividerSize, int dividerColor) {
        if (dividerSize == -1) {
            dividerSize = DIVIDER_SIZE;
        }
        if (dividerColor == -1) {
            dividerColor = DIVIDER_COLOR;
        }
        mDividerSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerSize,
                context.getResources().getDisplayMetrics());
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            int itemPosition = ((RecyclerView.LayoutParams) child.getLayoutParams()).getViewLayoutPosition();

            boolean[] sideOffsetBooleans = getItemSidesIsHaveOffsets(itemPosition);
            if (sideOffsetBooleans[0]) {
                drawChildLeft(child, c, parent);
            }
            if (sideOffsetBooleans[1]) {
                drawChildTop(child, c, parent);
            }
            if (sideOffsetBooleans[2]) {
                drawChildRight(child, c, parent);
            }
            if (sideOffsetBooleans[3]) {
                drawChildBottom(child, c, parent);
            }
        }
    }


    private void drawChildTop(View child, Canvas c, RecyclerView parent) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();
        int left = child.getLeft() - params.leftMargin - mDividerSize;
        int right = child.getRight() + params.rightMargin + mDividerSize;
        int bottom = child.getTop() - params.topMargin;
        int top = bottom - mDividerSize;

        c.drawRect(left, top, right, bottom, mPaint);
    }


    private void drawChildBottom(View child, Canvas c, RecyclerView parent) {

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();
        int left = child.getLeft() - params.leftMargin - mDividerSize;
        int right = child.getRight() + params.rightMargin + mDividerSize;
        int top = child.getBottom() + params.bottomMargin;
        int bottom = top + mDividerSize;

        c.drawRect(left, top, right, bottom, mPaint);
    }

    private void drawChildLeft(View child, Canvas c, RecyclerView parent) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();
        int top = child.getTop() - params.topMargin - mDividerSize;
        int bottom = child.getBottom() + params.bottomMargin + mDividerSize;
        int right = child.getLeft() - params.leftMargin;
        int left = right - mDividerSize;

        c.drawRect(left, top, right, bottom, mPaint);
    }

    private void drawChildRight(View child, Canvas c, RecyclerView parent) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();
        int top = child.getTop() - params.topMargin - mDividerSize;
        int bottom = child.getBottom() + params.bottomMargin + mDividerSize;
        int left = child.getRight() + params.rightMargin;
        int right = left + mDividerSize;

        c.drawRect(left, top, right, bottom, mPaint);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        //outRect 看源码可知这里只是把Rect类型的outRect作为一个封装了left,right,top,bottom的数据结构,
        //作为传递left,right,top,bottom的偏移值来用的

        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();

        boolean[] sideOffsetBooleans = getItemSidesIsHaveOffsets(itemPosition);

        int left = sideOffsetBooleans[0] ? mDividerSize : 0;
        int top = sideOffsetBooleans[1] ? mDividerSize : 0;
        int right = sideOffsetBooleans[2] ? mDividerSize : 0;
        int bottom = sideOffsetBooleans[3] ? mDividerSize : 0;

        outRect.set(left, top, right, bottom);
    }

    /**
     * 顺序:left, top, right, bottom
     *
     * @return boolean[4]
     */
    public abstract boolean[] getItemSidesIsHaveOffsets(int itemPosition);
}
