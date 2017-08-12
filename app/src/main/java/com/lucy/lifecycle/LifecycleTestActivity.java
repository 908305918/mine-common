package com.lucy.lifecycle;

import android.app.Dialog;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.hss01248.lib.StytledDialog;
import com.lucy.common.R;

/**
 * Created by YJB on 2017/8/11.
 */

public class LifecycleTestActivity extends LifecycleActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_lifecycle);
        mTextView = (TextView) findViewById(R.id.tv_content);

        TestViewModel model = ViewModelProviders.of(this).get(TestViewModel.class);
        final Dialog dialog = StytledDialog.showProgressDialog(this, "正在加载...", false, false);
        model.getData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String content) {
                dialog.dismiss();
                mTextView.setText(content);
            }
        });
    }
}
