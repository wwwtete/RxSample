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
 * SkipLast操作符：丢弃源Observable最后的指定的N项数据，而把前面的结果交给订阅者，
 * 也就是说skipLast操作符是在源Observable调用OnComplete方法后，才去判断是否符合指定的规则，并不是上来就把前几个数据交给订阅者，这里会有一个延迟效果
 *  TODO 实现机制：延迟原始Observable发射的任何数据，直到源Observable发射了够N项数据后才发射，如果数据不够则直接调用onComplete
 *  skipLast重载方法：之前源Observable前指定的时间的数据，后面的丢弃
 * Created by wangw on 2016/3/24.
 */
public class SkipLast extends Operation {
    @Override
    public void onOperation(Output view) {
        onSample1(view);
//        onSample2(view) ;
    }

    /**
     * TODO skipLast重载方法实现机制：丢弃最后指定后时长内的数据，这个和skipLast(count)不一样，它是不抑制数据发射的
     * @param view
     */
    private void onSample2(Output view) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    for (int i = 1; i < 21; i++) {
                        Thread.sleep(500);
                        L.d("call"+i);
                        subscriber.onNext(i + "");
                    }
                    L.d("onCompleted");
                    subscriber.onCompleted();
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        })
                .skipLast(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getStringSubscriber(view, "skipLast Time"));

    }


    /**
     * TODO 注意：skipLast机制是：并不是上来就把前几个数据交给订阅者，而是只有等源Observable调用了onComplete方法后，
     * TODO 才会把符合规则的前几个数据交给订阅者，如果源Observable不调用onComplete方法则skipLast一直不发射数据，也就是会有一个延迟效果
     * @param view
     */
    private void onSample1(Output view) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    for (int i = 0; i < 10; i++) {
                        Thread.sleep(500);
                        subscriber.onNext(i+"");
                    }
                }catch (Exception e){
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.newThread())
                .skipLast(6)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getStringSubscriber(view));
    }

    @Override
    public String toString() {
        return "SkipLast";
    }
}
