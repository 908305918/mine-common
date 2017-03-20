package com.lucy.common.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lucy.common.R;
import com.lucy.common.view.StateButton;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/11/15.
 */
public class StateButtonActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_statebutton);

        final StateButton button = (StateButton) findViewById(R.id.state_button);
        final TextView textView = (TextView) findViewById(R.id.tv_content);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpUtils.get().url("http://gc.ditu.aliyun.com/geocoding?a=合肥市").build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        textView.setText("请求失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        textView.setText(response);
                    }
                });
            }
        });
    }
}
