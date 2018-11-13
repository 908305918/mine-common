package com.lucy.common.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lucy.common.R;
import com.lucy.common.dialog.Loading;
import com.lucy.common.view.StateButton;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/11/15.
 */
public class StateButtonActivity extends AppCompatActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_statebutton);

        final StateButton button = findViewById(R.id.state_button);
        final StateButton button1 = findViewById(R.id.state_cancel);
        mTextView = findViewById(R.id.tv_content);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //request(true);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request(false);
            }
        });

        findViewById(R.id.float_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void request(boolean gzip) {
        final Loading loading = new Loading();
        loading.show(getSupportFragmentManager());
        OkHttpUtils.post().url("https://www.baidu.com/").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mTextView.setText(e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                mTextView.setText(response);
                loading.dismiss();
            }
        });
    }

}
