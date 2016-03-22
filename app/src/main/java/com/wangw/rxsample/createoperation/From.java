package com.wangw.rxsample.createoperation;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import rx.Observable;
import rx.Subscriber;

/**
 * From 操作符：是将其他类型的对象或数据类型转换成Observable，并且依次将里面的内容发射出去
 * Created by wangw on 2016/3/4.
 */
public class From extends Operation {


    private Integer[] values = {1,2,3,4,5,6,7};

    @Override
    public void onOperation(final Output output) {
        Observable.from(values)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        output.output("[onCompleted]");
                    }

                    @Override
                    public void onError(Throwable e) {
                        output.output("[onError]"+e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        output.output("[onNext]="+integer);
                    }
                });
    }

    @Override
    public String toString() {
        return "From Operation";
    }
}
