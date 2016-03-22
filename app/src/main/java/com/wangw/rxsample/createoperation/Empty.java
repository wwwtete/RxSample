package com.wangw.rxsample.createoperation;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by wangw on 2016/3/7.
 */
public class Empty extends Operation {
    @Override
    public void onOperation(Output view) {
        Observable.empty()
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }
                });
        Observable.never();
//        Observable.error();
    }

    @Override
    public String toString() {
        return "Empty";
    }
}
