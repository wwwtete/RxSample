package com.wangw.rxsample.createoperation;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Interval操作符：创建一个Observable对象从0开始，间隔固定的时间发射一个从0开始无限递增的数字序列
 * Created by wangw on 2016/3/7.
 */
public class Interval extends Operation {
    @Override
    public void onOperation(final Output view) {

        Observable.interval(2, TimeUnit.SECONDS, Schedulers.computation())
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
                        view.output("[onNext] = "+aLong);
                        //如果along值大于20则不再接受Observable的值
                        if(aLong > 20)
                            this.unsubscribe();
                    }
                });
    }

    @Override
    public String toString() {
        return "Interval";
    }
}
