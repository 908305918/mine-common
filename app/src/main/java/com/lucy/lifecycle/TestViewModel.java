package com.lucy.lifecycle;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.zhy.http.okhttp.OkHttpUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;

/**
 * Created by YJB on 2017/8/11.
 */

public class TestViewModel extends ViewModel {
    private MutableLiveData<String> mDatas;

    public LiveData<String> getData() {
        if (mDatas == null) {
            mDatas = new MutableLiveData<>();
            loadData();
        }
        return mDatas;
    }

    private void loadData() {
        Observable.just(1).map(new Function<Integer, Response>() {
            @Override
            public Response apply(@NonNull Integer integer) throws Exception {
                return OkHttpUtils.get().url("http://www.baidu.com").build().execute();
            }
        }).map(new Function<Response, String>() {
            @Override
            public String apply(@NonNull Response response) throws Exception {
                return response.body().string();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        mDatas.setValue(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mDatas.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
