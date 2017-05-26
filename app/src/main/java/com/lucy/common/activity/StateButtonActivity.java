package com.lucy.common.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.lucy.common.R;
import com.lucy.common.view.StateButton;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.concurrent.Callable;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.Call;
import okhttp3.Response;

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

        RxView.clicks(button).flatMap(new Function<Object, ObservableSource<Response>>() {
            @Override
            public ObservableSource<Response> apply(Object o) throws Exception {
                return loadData();
            }
        }).materialize().subscribe(new Consumer<Notification<Response>>() {
            @Override
            public void accept(Notification<Response> o) throws Exception {
                if (o.isOnNext()) {
                    textView.setText(o.getValue().body().string());
                } else if (o.isOnError()) {
                    textView.setText(o.getError().toString());
                }
            }
        });
    }

    private Observable<Response> loadData() {
        return Observable.fromCallable(new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                String url = "http://gc.ditu.aliyun.com/geocoding?a=合肥市";
                return OkHttpUtils.get().url(url).build().execute();
            }
        });
    }
}
