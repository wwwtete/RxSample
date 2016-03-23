package com.wangw.rxsample.filtering;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * IgnoreElements操作符：忽略源Observable发射的所有数据，只把onCompleted或onError事件通知给订阅者
 *
 * Created by wangw on 2016/3/23.
 */
public class IgnoreElements extends Operation {
    @Override
    public void onOperation(final Output view) {
        Observable.interval(1, TimeUnit.SECONDS)
                .take(10)
                .ignoreElements()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        view.output("[IgnoreElements] onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.output("[IgnoreElements] e");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        view.output("[IgnoreElements] onNext = " + aLong);
                    }
                });
    }

    @Override
    public String toString() {
        return "IgnoreElements";
    }
}
