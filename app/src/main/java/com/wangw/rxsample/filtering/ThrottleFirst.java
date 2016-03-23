package com.wangw.rxsample.filtering;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * ThrottleFirst操作符:发射指定的时间范围内源Observable的第一个数据，其他的则被丢弃
 * throttleLast操作符：发射指定时间范围内源Observable的最后一个数据，其它的则被丢弃
 * Created by wangw on 2016/3/23.
 */
public class ThrottleFirst extends Operation {
    @Override
    public void onOperation(Output view) {
        onSample1(view);
        onSample2(view);
    }

    private void onSample2(Output view) {
        Observable.interval(200, TimeUnit.MILLISECONDS)
                .take(20)
                .throttleLast(1, TimeUnit.SECONDS)
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        return aLong+"";
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getStringSubscriber(view, "last"));

    }

    private void onSample1(Output view) {
        Observable.interval(200, TimeUnit.MILLISECONDS)
                .take(20)
                .throttleFirst(1, TimeUnit.SECONDS)
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        return aLong+"";
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getStringSubscriber(view,"frist"));
    }

    @Override
    public String toString() {
        return "ThrottleFirst / ThrotteLast";
    }
}
