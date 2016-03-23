package com.wangw.rxsample.filtering;

import android.view.View;

import com.exlogcat.L;
import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Debounce操作符:中文翻译叫做“防抖”也可以称为限流阀，他主要作用是根据指定的时间间隔或指定的过滤规则进行过滤多余的数据，只发射间隔内最后的一条数据。
 *
 * throttleWithTimeout操作符：和Debounce操作符类似，只不过throttleWithTimeout操作符是在源Observable每次发射新的数据时重新开始计时，
 * 也就是说如果每次发射的数据都在间隔内没有超过间隔时间则一直不会发射数据， 直到重新计时后超过间隔时间或最后调用源OnComplete方法
 *
 * Created by wangw on 2016/3/22.
 */
public class Debounce extends Operation {
    @Override
    public void onOperation(Output view) {
//        onSample1(view);
//        onSample2(view);
        onSample3(view);
    }

    /**
     * debounce的重载方法：这个方法是根据指定的函数来进行限流，这个函数的返回值是一个临时的Observable
     *      如果源Observable在发射一个新的Observable时，上一个数据生成的Observable还没有调用onComplete函数，则上一个数据将被丢弃。
     *      也就是说：如果生成的这个临时Observable不调用onComplete则订阅者永远接收不到数据，直到调用临时Observable的onComplete方法
     * @param view
     */
    private void onSample3(final Output view) {
        Observable.just(1,2,3,4,5,6,7,8)
                .debounce(new Func1<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(final Integer integer) {
                        return Observable.create(new Observable.OnSubscribe<Integer>() {
                            @Override
                            public void call(Subscriber<? super Integer> subscriber) {
                                view.output("call = " + integer);
                                if (integer % 2 == 0) {
                                    subscriber.onNext(integer);
                                    //注意：如果不调用onCompleted方法，则订阅者永远收不到数据
                                    subscriber.onCompleted();
                                }
                            }
                        });
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        view.output("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.output("onError");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        view.output("onNext = " + integer);
                    }
                });
    }

    /**
     *throttleWithTimeout：和debounce操作符类似，但是throttleWithTimeout与debounce最大的不同点在于，
     *      源Observable每次发射一个数据后就会重新开始计时，如果在制定号的间隔时间内源Observable有新的数据发射出来则这个数据将会被丢弃，并且重新开始计时，
     *      如果每次都是在指定的时间间隔内发射数据，则很有可能只发射源Observable的最后一条数据，也就是当源Observable调用onComplete方法时才会被订阅者接收
     * @param view
     */
    private void onSample2(Output view) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    for (int i = 0; i < 10; i++) {
                        subscriber.onNext(i+"");
                        Thread.sleep(100);
                    }
                }catch (Exception e){
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.newThread())
                .throttleWithTimeout(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getStringSubscriber(view,"throttleWithTimeout"));
    }

    /**
     * debounce：在指定的间隔时间之内将最后一条数据交给订阅者
     * 注意：如果在规定的间隔时间内调用了onComplete，那么通过debounce操作符也会把这个结果交给订阅者
     * @param view
     */
    private void onSample1(final Output view) {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if(subscriber.isUnsubscribed())
                    return;
                try {
                    for (int i = 0; i < 10; i++) {
                        subscriber.onNext(i);
                        Thread.sleep(i * 100);
                    }
                }catch(InterruptedException e){
                    e.printStackTrace();
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.newThread())
                .debounce(400, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        L.d("onCompleted");
                        view.output("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.output("onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        L.d("onNext = " + integer);
                        view.output("onNext = " + integer);
                    }
                });
    }

    @Override
    public String toString() {
        return "Debounce";
    }
}
