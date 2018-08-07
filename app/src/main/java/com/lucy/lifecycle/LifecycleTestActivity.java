package com.lucy.lifecycle;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import com.lucy.common.R;

/**
 * Created by YJB on 2017/8/11.
 */

public class LifecycleTestActivity extends AppCompatActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_lifecycle);
        mTextView = findViewById(R.id.tv_content);

        TestViewModel model = ViewModelProviders.of(this).get(TestViewModel.class);
        model.getData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String content) {
                mTextView.setText(Html.fromHtml(content));
            }
        });
    }
}
