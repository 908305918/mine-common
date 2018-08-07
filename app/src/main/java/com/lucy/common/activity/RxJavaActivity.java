package com.lucy.common.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lucy.common.R;
import com.lucy.common.databinding.RxJavaBinding;
import com.lucy.common.service.AudioService;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by WD on 2017/9/1.
 */

public class RxJavaActivity extends BaseActivity {
    private RxJavaBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_rxjava);
        super.setCustomTitle("RxJava");

        mBinding.stateButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxjava();
                //Intent intent = new Intent(RxJavaActivity.this, AudioService.class);
                //startService(intent);

            }
        });
        mBinding.stateButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void rxjava() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) {
                try {
                    Thread.sleep(2000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                e.onNext(1);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io());
        Observable<Integer> observable2 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) {
                try {
                    Thread.sleep(1000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                e.onNext(2);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io());
        Disposable disposable = Observable.merge(observable1, observable2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) {
                        String a = integer.toString() + "=" + System.currentTimeMillis() + "\n";
                        mBinding.tvContent.append(a);
                    }
                });
        mBinding.tvContent.append("0=" + System.currentTimeMillis() + "\n");

    }
}
