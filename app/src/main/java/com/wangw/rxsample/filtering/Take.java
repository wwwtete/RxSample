package com.wangw.rxsample.filtering;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Take操作符：只发射源Observable的前N个数据或指定前N段时间内的数据给订阅者，然后调用onComplete结束
 *
 * Created by wangw on 2016/3/24.
 */
public class Take extends Operation {
    @Override
    public void onOperation(Output view) {
        onSample1(view);
        onSample2(view);
    }

    private void onSample2(Output view) {
        Observable.interval(100,TimeUnit.MILLISECONDS)
                .take(1, TimeUnit.SECONDS)
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        return aLong+"";
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getStringSubscriber(view,"take time"));
    }

    private void onSample1(Output view) {
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .take(10)
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        return aLong+"";
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getStringSubscriber(view));
    }

    @Override
    public String toString() {
        return "Take";
    }
}
