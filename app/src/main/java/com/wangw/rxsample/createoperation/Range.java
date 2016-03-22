package com.wangw.rxsample.createoperation;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import rx.Observable;
import rx.Subscriber;

/**
 * Range操作符：发射一个范围内的有序的整数序列，可以指定起始范围和长度
 * Created by wangw on 2016/3/7.
 */
public class Range extends Operation {
    @Override
    public void onOperation(final Output view) {
        Observable.range(5,11)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        view.output("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        view.output("[onNext]="+integer);
                    }
                });
    }

    @Override
    public String toString() {
        return "Range";
    }
}
