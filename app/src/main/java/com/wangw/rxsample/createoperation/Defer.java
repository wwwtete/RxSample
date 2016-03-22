package com.wangw.rxsample.createoperation;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;

/**
 * Created by wangw on 2016/3/7.
 */
public class Defer extends Operation {

    private Observable<Long> mJustObservable;
    private Observable<Long> mDeferObservable;

    public Defer(){
        mJustObservable = Observable.just(System.currentTimeMillis());
        mDeferObservable = Observable.defer(new Func0<Observable<Long>>() {
            @Override
            public Observable<Long> call() {
                return Observable.create(new Observable.OnSubscribe<Long>() {
                    @Override
                    public void call(Subscriber<? super Long> subscriber) {
                        subscriber.onNext(System.currentTimeMillis());
                        subscriber.onCompleted();
                    }
                });
            }
        });
    }


    @Override
    public void onOperation(final Output view) {
        mJustObservable.subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
            view.output("Just Observable 创建时间："+aLong);
            }
        });

        mDeferObservable.subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                view.output("defer Observale 创建时间："+aLong);
            }
        });

    }

    @Override
    public String toString() {
        return "Defer";
    }
}
