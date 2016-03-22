package com.wangw.rxsample.createoperation;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import rx.Observable;
import rx.Subscriber;

/**
 * just操作符：将
 * Created by wangw on 2016/3/7.
 */
public class Just extends Operation {
    @Override
    public void onOperation(Output view) {
        just1(view);
        just2(view);
    }

    private void just2(final Output view) {
        Observable.just(1,2,3,4,5,6,7,8,9,10)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        view.output("[onCompleted]");
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.output("[onError]");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        view.output("[onNext]="+integer);
                    }
                });
    }

    private void just1(final Output view) {
        Observable.just("单个元素")
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        view.output("[onCompleted]");
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.output("[onError]");
                    }

                    @Override
                    public void onNext(String s) {
                        view.output("[onNext]="+s);
                    }
                });
    }


    @Override
    public String toString() {
        return "just";
    }
}
