package com.wangw.rxsample.createoperation;

import com.exlogcat.L;
import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Timer操作符：在给定的时间段后发射一个0值后结束
 * 注意：timer操作符默认是在Schedulers.computation()线程上执行，可以通过参数设置所在线程执行
 * Created by wangw on 2016/3/7.
 */
public class Timer extends Operation {
    @Override
    public void onOperation(final Output view) {
        view.output("timer启动，在"+5+"秒后发射一个0值");
        Observable.timer(5, TimeUnit.SECONDS, Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        view.output("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        L.d("onNext: along = %s thread = " + Thread.currentThread().getName(), aLong);
                        view.output("[onNext]=" + aLong);
                    }
                });


    }

    @Override
    public String toString() {
        return "Timer";
    }
}
