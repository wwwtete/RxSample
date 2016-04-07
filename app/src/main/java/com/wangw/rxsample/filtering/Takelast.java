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
 * TakeLast操作符：是将源Observable在调用onCompleted后，将产生的结果的最后N项或最后N段时间内的数据发射给订阅者，发射时机是在源Observable调用onCompleted后
 * Created by wangw on 2016/3/24.
 */
public class Takelast extends Operation {
    @Override
    public void onOperation(Output view) {
        onSample1(view);
        onSample2(view);
        onSample3(view);
    }

    /**
     * TODO 注意：takeFirst和first操作符类似，都是指定一个规则，获取符合指定规则的第一个数据后直接调用onComplete结束，
     * TODO 与first不同的是first操作符如果获取不到数据则抛出异常，而TakeFirst则会返回一个空的OBservable，该Observable只有onComplete通知没有onNext通知
     * @param view
     */
    private void onSample3(Output view) {
        getObservable()
                .takeFirst(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        L.d("takeFirst call = "+s);
                        return s.equals("2");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getStringSubscriber(view, "takeFirst"));
    }

    /**
     * TODO 注意：takeLast(time)发射机制和takeLast(count)一样的，只有等源Observable调用onComplete之后才会触发takeLast发射数据
     * @param view
     */
    private void onSample2(Output view) {
        getObservable()
                .takeLast(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getStringSubscriber(view, "takeLast time"));

    }

    /**
     * TODO 注意：takeLast(count)发射机制是在源Observable触发onComplete后才开始发射数据的，如果源Observable不调用onComplete则takeList一直不发射数据
     * @param view
     */
    private void onSample1(Output view) {
        getObservable()
                .takeLast(5)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getStringSubscriber(view,"takeLast count"));
    }


    private Observable<String> getObservable(){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    for (int i = 0; i < 10; i++) {
                        Thread.sleep(200);
                        subscriber.onNext(i+"");
                    }
                }catch (Exception e){
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.newThread());
    }

    @Override
    public String toString() {
        return "TakeLast / TakeFirst";
    }
}
