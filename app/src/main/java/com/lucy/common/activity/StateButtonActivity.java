package com.lucy.common.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hss01248.lib.StytledDialog;
import com.lucy.common.R;
import com.lucy.common.util.NotificationsUtils;
import com.lucy.common.view.StateButton;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/11/15.
 */
public class StateButtonActivity extends Activity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_statebutton);

        final StateButton button = (StateButton) findViewById(R.id.state_button);
        final StateButton button1 = (StateButton) findViewById(R.id.state_cancel);
        mTextView = (TextView) findViewById(R.id.tv_content);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //request(true);
                boolean isOpen = NotificationsUtils.isNotificationEnabled(StateButtonActivity.this);
                mTextView.setText("通知栏是否开启：" + isOpen);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request(false);
            }
        });
    }

    private void request(boolean gzip) {
        final Dialog dialog = StytledDialog.showProgressDialog(this, "正在加载...", false, false);
        final long startTime = System.currentTimeMillis();
        Map<String, String> headers = new HashMap<>();
        if (!gzip) {
            headers.put("Accept-Encoding", "");
        }
        OkHttpUtils.post().url("http://192.168.30.3:9999/").headers(headers).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialog.dismiss();
                mTextView.setText(e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                dialog.dismiss();
                response = (System.currentTimeMillis() - startTime) + "\n" + response;
                mTextView.setText(response);
            }
        });
    }

}
