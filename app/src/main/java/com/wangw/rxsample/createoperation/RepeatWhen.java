package com.wangw.rxsample.createoperation;

import com.exlogcat.L;
import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * RepeatWhen操作符：
 * Created by wangw on 2016/3/7.
 */
public class RepeatWhen extends Operation {
    @Override
    public void onOperation(Output view) {

        Observable.just(1, 2, 3)
                .repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Void> observable) {
                        return observable.zipWith(Observable.range(1, 3), new Func2<Void, Integer, Integer>() {
                            @Override
                            public Integer call(Void aVoid, Integer integer) {
                                L.d("zipWith = "+integer);
                                return integer;
                            }
                        }).flatMap(new Func1<Integer, Observable<?>>() {
                            @Override
                            public Observable<?> call(Integer integer) {
                                L.d("flatMap = "+integer);
                                return Observable.timer(1, TimeUnit.SECONDS);
                            }
                        });
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        L.d("[onNext] integer="+integer);
                    }
                });

    }

    @Override
    public String toString() {
        return "RepeatWhen";
    }
}
