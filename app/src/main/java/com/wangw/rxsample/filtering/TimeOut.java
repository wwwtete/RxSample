package com.wangw.rxsample.filtering;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * timeout:如果超过指定的时间范围源Observable没有发射数据的话则会触发onError事件
 *
 * Created by wangw on 2016/4/7.
 */
public class TimeOut extends Operation {
    @Override
    public void onOperation(Output view) {
        onSample1(view);
    }

    /**
     *
     * @param view
     */
    private void onSample1(final Output view) {
        //模仿一个温度计，每隔500毫秒报一下当前的温度，如果超过800毫秒没有报温度则进入Error
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                try {
                    for (int i = 0; i < 10; i++) {
                        subscriber.onNext((int)( Math.random() * 100) + "°");
                        if(i != 6) {
                            Thread.sleep(500);
                        }else {
                            Thread.sleep(1000);
                        }
                    }
                }catch(Exception e){
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        })
                .timeout(800, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.output("超时异常！");
                    }

                    @Override
                    public void onNext(String s) {
                        view.output("onNext = "+s);
                    }
                });

    }

    @Override
    public String toString() {
        return "TimeOut";
    }
}
