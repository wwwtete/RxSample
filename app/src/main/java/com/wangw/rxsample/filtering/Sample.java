package com.wangw.rxsample.filtering;

import com.exlogcat.L;
import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Sample操作符:按照指定的时间对源Observable进行采样,只发射最近的数据，其他的数据则丢弃
 * Created by wangw on 2016/3/23.
 */
public class Sample extends Operation {
    @Override
    public void onOperation(Output view) {
        onSample1(view);
//        onSample2(view);
    }

    /**
     * TODO 未能理解。。。。
     * @param view
     */
    private void onSample2(Output view) {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                L.d("sample call");
//                for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                subscriber.onNext("call");
                    subscriber.onCompleted();
//                }

            }
        });
        Observable.interval(200, TimeUnit.MILLISECONDS) //.just("1", "2", "3", "4", "5", "6")
                .sample(observable)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        return aLong+"";
                    }
                })
                .subscribe(getStringSubscriber(view));
    }

    private void onSample1(Output view) {
        Observable.interval(200, TimeUnit.MILLISECONDS)
                .take(20)
                .sample(1,TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        return aLong + "";
                    }
                })
                .subscribe(getStringSubscriber(view));
    }

    @Override
    public String toString() {
        return "Sample";
    }
}
