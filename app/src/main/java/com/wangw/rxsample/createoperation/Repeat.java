package com.wangw.rxsample.createoperation;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Repeat操作符：这个操作符不是创建一个新的Observable，而是重复发射原始的Observable的数据序列，
 *              这个序列可以是无限的也可以是通过repeat(n)指定的重复次数
     *         注意：这个操作默认是在Schedulers.trampoline()上执行
 * Created by wangw on 2016/3/7.
 */
public class Repeat extends Operation {
    @Override
    public void onOperation(final Output view) {
        Observable.range(0,5)
                .repeat(3)
                .observeOn(AndroidSchedulers.mainThread())
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
        return "Repeat";
    }
}
