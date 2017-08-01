package com.lucy.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.State;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cms.imagepicker.ImagePickerActivity;
import com.lucy.bigimage.LargeImageActivity;
import com.lucy.common.activity.MapActivity;
import com.lucy.common.activity.StateButtonActivity;
import com.lucy.common.activity.TestCommonAdapterActivity;
import com.lucy.common.activity.TextViewActivity;
import com.lucy.common.activity.WebViewActivity;
import com.lucy.common.util.NetUtil;
import com.lucy.common.util.Utility;
import com.lucy.common.view.MarqueeTextView;
import com.lucy.tree.TreeActivity;

public class MainActivity extends Activity {
    private RecyclerView mRecyclerView;
    private MarqueeTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new TestAdapter());
        mRecyclerView
                .addItemDecoration(new DividerItemDecoratio(this, LinearLayoutManager.VERTICAL));

        textView = (MarqueeTextView) findViewById(R.id.textview);
        textView.init();
        textView.startScroll();

    }

    public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
        private String[] mData = new String[]{"高清加载巨图", "测试CommonAdapter", "选择图片", "组织导航地图",
                "树形结构", "StateButton", "WebView", "TextView", "item9", "item10", "item11", "item12",
                "item13", "item14", "item15", "item16"};

        @Override
        public int getItemCount() {
            return mData.length;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            viewHolder.itemView.setText(mData[position]);
            viewHolder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    switch (position) {
                        case 0:
                            intent = new Intent(MainActivity.this, LargeImageActivity.class);
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(MainActivity.this, TestCommonAdapterActivity.class);
                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(MainActivity.this, ImagePickerActivity.class);
                            startActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(MainActivity.this, MapActivity.class);
                            startActivity(intent);
                            break;
                        case 4:
                            intent = new Intent(MainActivity.this, TreeActivity.class);
                            startActivity(intent);
                            break;
                        case 5:
                            intent = new Intent(MainActivity.this, StateButtonActivity.class);
                            startActivity(intent);
                            break;
                        case 6:
                            intent = new Intent(MainActivity.this, WebViewActivity.class);
                            startActivity(intent);
                            break;
                        case 7:
                            intent = new Intent(MainActivity.this, TextViewActivity.class);
                            startActivity(intent);
                            break;
                    }
                }
            });
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_textview, parent, false);
            return new ViewHolder(textView);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView itemView;

            public ViewHolder(View view) {
                super(view);
                itemView = (TextView) view;
            }
        }
    }

    public class DividerItemDecoratio extends RecyclerView.ItemDecoration {

        /**
         * RecyclerView的布局方向，默认纵向
         */
        private int mOrientation = LinearLayoutManager.VERTICAL;
        /**
         * item之间分割线的size，默认为1
         */
        private int mItemSize = 1;

        /**
         * 绘制item分割线的画笔
         */
        private Paint mPaint;

        public DividerItemDecoratio(Context context, int orientation) {
            if (orientation != LinearLayoutManager.HORIZONTAL
                    && orientation != LinearLayoutManager.VERTICAL) {
                throw new IllegalArgumentException("请传入正确的参数");
            }
            mItemSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mItemSize,
                    context.getResources().getDisplayMetrics());
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(0xFFD0D0D0);
            mPaint.setStyle(Paint.Style.FILL);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, State state) {
            if (mOrientation == LinearLayoutManager.VERTICAL) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
            if (mOrientation == LinearLayoutManager.VERTICAL) {
                outRect.set(0, 0, 0, mItemSize);
            } else {
                outRect.set(0, 0, mItemSize, 0);
            }
        }

        private void drawVertical(Canvas canvas, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
            final int childSize = parent.getChildCount();
            for (int i = 0; i < childSize; i++) {
                final View child = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = child.getBottom() + layoutParams.bottomMargin;
                final int bottom = top + mItemSize;
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }

        private void drawHorizontal(Canvas canvas, RecyclerView parent) {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
            final int childSize = parent.getChildCount();
            for (int i = 0; i < childSize; i++) {
                final View child = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int left = child.getRight() + layoutParams.rightMargin;
                final int right = left + mItemSize;
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }

    }
}
