package com.lucy.common.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lucy.common.R;

import java.util.HashSet;
import java.util.Set;

public class PokeView extends View {

    private int mPokeType = 1; //1、一行 2、三行
    private float mTopSpacing; //选中时顶部突出距离
    private float mPokeHeight; //扑克牌的高度
    private float mPokeWidth;  //扑克牌的宽度
    private Bitmap[] mBitmaps = new Bitmap[9];
    private boolean[] mPokeFlag = new boolean[9]; //扑克的选中状态
    private Paint mPaint;
    private Set<Integer> mSelectIndex = new HashSet<>();
    private Rect mDstRect = new Rect();
    private RectF mBorderRect = new RectF();

    public PokeView(Context context) {
        super(context);
        init();
    }

    public PokeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PokeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        for (int i = 0; i < 9; i++) {
            if(i%3==0){
                mBitmaps[i] = BitmapFactory.decodeResource(getResources(), R.drawable.poke_1);
            }else if(i%3==1){
                mBitmaps[i] = BitmapFactory.decodeResource(getResources(), R.drawable.poke_2);
            }else {
                mBitmaps[i] = BitmapFactory.decodeResource(getResources(), R.drawable.poke_6);
            }

        }
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#D0D0D0"));
        mPaint.setStrokeWidth(2);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);

        if (mPokeType == 1) {
            mPokeHeight = height * 9.0f / 10;
            mTopSpacing = height - mPokeHeight;
            mPokeWidth = mPokeHeight * 7f / 10;
            width = (int) (mPokeWidth * 5);
        } else if (mPokeType == 2) {
            mPokeHeight = height * 1.0f / 2;
            mPokeWidth = mPokeHeight * 7f / 10;
            width = (int) (mPokeWidth * 2);
        }
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float space = mPokeWidth / 2;
        if (mPokeType == 1) {
            for (int i = 0; i < 9; i++) {
                int top = mPokeFlag[i] ? 0 : (int) mTopSpacing;
                mDstRect.set((int) (i * space), top,
                        (int) (i * space + mPokeWidth), (int) (top + mPokeHeight));
                mBorderRect.set(mDstRect);
                canvas.drawBitmap(mBitmaps[i], null, mDstRect, null);
                canvas.drawRoundRect(mBorderRect, 10, 10, mPaint);
            }
        } else if (mPokeType == 2) {
            float heightSpace = mPokeHeight / 2.5f;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    mDstRect.set((int) (j * space), (int) (i * heightSpace),
                            (int) (j * space + mPokeWidth),
                            (int) (i * heightSpace + mPokeHeight));
                    mBorderRect.set(mDstRect);
                    canvas.drawBitmap(mBitmaps[i * 3 + j], null, mDstRect, null);
                    canvas.drawRoundRect(mBorderRect, 10, 10, mPaint);
                }
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int space = (int) (mPokeWidth / 2);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mSelectIndex.clear();
        }
        for (int i = 8; i >= 0; i--) {
            if (inRect(x, y, i * space, (int) mTopSpacing, (int) mPokeWidth, (int) mPokeHeight)) {
                mSelectIndex.add(i);
                break;
            }
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            for (Integer i : mSelectIndex) {
                mPokeFlag[i] = !mPokeFlag[i];
            }
            invalidate();
        }
        return true;
    }

    public static boolean inRect(int x, int y, int rectX, int rectY, int rectW,
                                 int rectH) {
        if (x < rectX || x > rectX + rectW || y < rectY || y > rectY + rectH) {
            return false;
        }
        return true;
    }
}
