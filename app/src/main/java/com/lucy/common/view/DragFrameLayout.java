package com.lucy.common.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.lucy.common.util.DisplayUtil;

/**
 * Created by WD on 2017/8/7.
 */

public class DragFrameLayout extends FrameLayout {

    private int screenWidth;
    private int screenHeight;
    private int screenWidthHalf;
    private int statusHeight;


    private int lastX;
    private int lastY;

    private boolean isDrag;

    public DragFrameLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public DragFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Context context = getContext();
        screenWidth = DisplayUtil.getScreenWidth(context);
        screenWidthHalf = screenWidth / 2;
        screenHeight = DisplayUtil.getScreenHeight(context);
        statusHeight = DisplayUtil.getStatusHeight(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                isDrag = false;
                getParent().requestDisallowInterceptTouchEvent(true);
                lastX = rawX;
                lastY = rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                isDrag = true;
                //计算手指移动了多少
                int dx = rawX - lastX;
                int dy = rawY - lastY;
                //这里修复一些华为手机无法触发点击事件的问题
                int distance = (int) Math.sqrt(dx * dx + dy * dy);
                if (distance == 0) {
                    //isDrag = false;
                    break;
                }
                float x = getX() + dx;
                float y = getY() + dy;
                Log.e("Move", x + "===" + y);
                //检测是否到达边缘 左上右下
                x = x < 0 ? 0 : x > screenWidth - getWidth() ? screenWidth - getWidth() : x;
                y = y < 0 ? 0 : (y + getHeight() > screenHeight - statusHeight ? screenHeight - statusHeight - getHeight() : y);
                setX(x);
                setY(y);
                lastX = rawX;
                lastY = rawY;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (isDrag) {
                    //恢复按压效果
                    setPressed(false);
                    if (rawX >= screenWidthHalf) {
                        animate().setInterpolator(new DecelerateInterpolator())
                                .setDuration(500)
                                .xBy(screenWidth - getWidth() - getX())
                                .start();
                    } else {
                        ObjectAnimator oa = ObjectAnimator.ofFloat(this, "x", getX(), 0);
                        oa.setInterpolator(new DecelerateInterpolator());
                        oa.setDuration(500);
                        oa.start();
                    }
                }
                break;
        }
        return true;
    }
}
