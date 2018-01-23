package com.lucy.common;

import android.app.Activity;
import android.arch.lifecycle.LifecycleActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
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
import com.lucy.common.activity.RxJavaActivity;
import com.lucy.common.activity.StateButtonActivity;
import com.lucy.common.activity.TestCommonAdapterActivity;
import com.lucy.common.activity.TextViewActivity;
import com.lucy.common.activity.WebViewActivity;
import com.lucy.common.databinding.RxJavaBinding;
import com.lucy.common.util.NetUtil;
import com.lucy.common.util.Utility;
import com.lucy.common.view.MarqueeTextView;
import com.lucy.lifecycle.LifecycleTestActivity;
import com.lucy.tree.TreeActivity;

public class MainActivity extends Activity {
    private RecyclerView mRecyclerView;
    private MarqueeTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new TestAdapter());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        textView = findViewById(R.id.textview);
        textView.init();
        textView.startScroll();

    }

    public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
        private String[] mData = new String[]{"高清加载巨图", "测试CommonAdapter", "选择图片", "组织导航地图",
                "树形结构", "StateButton", "WebView", "TextView", "Lifecycle", "RxJava", "item11", "item12",
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
                        case 8:
                            intent = new Intent(MainActivity.this, LifecycleTestActivity.class);
                            startActivity(intent);
                            break;
                        case 9:
                            intent = new Intent(MainActivity.this, RxJavaActivity.class);
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

}
