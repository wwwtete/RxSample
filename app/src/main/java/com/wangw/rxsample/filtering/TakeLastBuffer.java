package com.wangw.rxsample.filtering;

import android.view.View;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * TakeLastBuffer操作符：和TakeLast操作符功能类似，只不过TakeLast操作符是一次一次发射数据，而takeLastBuffer操作符是将所有符合条件的数据封装成一个List一次性发射出去而已
 * TODO 注意：不管是TakeLast还是TakeLastBuffer操作符都是在源Observable调用onComplete方法后才会触发发射机制，如果源Observable不调用onCompleted方法则takeLast则一直不发射数据
 *
 * Created by wangw on 2016/3/24.
 */
public class TakeLastBuffer extends Operation {
    @Override
    public void onOperation(Output view) {
        onSample1(view);
        onSample2(view);
        onSample3(view);
    }

    /**
     * takeLastBuffer(count,time)操作符：满足其中任意一个条件(count或time)则发射数据，调用onCompleted结束
     * @param view
     */
    private void onSample3(Output view) {
        getObservable()
                .takeLastBuffer(3,800,TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSubscriber(view,"takeLastBuffer count time"));

    }

    private void onSample2(Output view) {
        getObservable()
                .takeLastBuffer(600, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSubscriber(view,"takeLastBuffer time"));
    }

    private void onSample1(final Output view) {
        getObservable()
                .takeLastBuffer(20)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSubscriber(view,"takelastBuffer count"));
    }


    private Subscriber<List<String>> getSubscriber(final Output view, final String operation){
        return new Subscriber<List<String>>() {
            @Override
            public void onCompleted() {
                view.output("["+operation+"] onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                view.output("["+operation+"] onError = "+e.getMessage());
            }

            @Override
            public void onNext(List<String> strings) {
                String str = "";
                for(String v:strings){
                    str += v+"、";
                }
                view.output("["+operation+"] onNext ="+str);
            }
        };
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
        return "TakeLastBuffer";
    }
}
