package com.wangw.rxsample.combining;

import com.exlogcat.L;
import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by wangw on 2016/4/6.
 */
public class Join extends Operation {
    @Override
    public void onOperation(Output view) {
//        onSample1(view);
        onSample2(view);
    }

    private void onSample2(Output view) {
        getIntObservable().join(getCharObervable(), new Func1<Integer, Observable<Object>>() {
            @Override
            public Observable<Object> call(Integer integer) {
                L.d("[call] int = %s",integer);
                return Observable.create(new Observable.OnSubscribe<Object>() {
                    @Override
                    public void call(Subscriber<? super Object> subscriber) {
                        try {
                            Thread.sleep(500);
                            subscriber.onNext(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        subscriber.onCompleted();
                    }
                });
            }
        }, new Func1<Character, Observable<Object>>() {
            @Override
            public Observable<Object> call(Character character) {
                L.d("[call] char = %s",character);
                return Observable.create(new Observable.OnSubscribe<Object>() {
                    @Override
                    public void call(Subscriber<? super Object> subscriber) {
                        try {
                            Thread.sleep(500);
                            subscriber.onNext(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        subscriber.onCompleted();
                    }
                });
            }
        }, new Func2<Integer, Character, String>() {
            @Override
            public String call(Integer integer, Character character) {
                L.d("[call] int = %s | char = %s",integer,character);
                return integer.toString()+character;
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getStringSubscriber(view));

    }

    private void onSample1(Output view) {
        getIntObservable().join(getCharObervable(), new Func1<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(Integer integer) {
                L.d("[call] int = %s",integer);
                return Observable.just(integer+"").delay(1000, TimeUnit.MILLISECONDS);
            }
        }
                , new Func1<Character, Observable<String>>() {
            @Override
            public Observable<String> call(Character character) {
                L.d("[call] char = %s",character);
                return Observable.just(character+"").delay(500, TimeUnit.MILLISECONDS);
            }
        }
                , new Func2<Integer, Character, String>() {
            @Override
            public String call(Integer integer, Character character) {
                L.d("[call] int = %s | char = %s",integer,character);
                return integer.toString()+character;
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getStringSubscriber(view));
    }

    @Override
    public String toString() {
        return "Join";
    }
}
