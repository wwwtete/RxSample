package com.wangw.rxsample.api;

import com.exlogcat.L;
import com.wangw.rxsample.api.Output;

import java.util.Objects;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by wangw on 2016/3/4.
 */
public abstract class Operation {

    public abstract void onOperation(Output view);

    public abstract String toString();

    protected Subscriber<String> getStringSubscriber(final Output view){
        return new Subscriber<String>() {
            @Override
            public void onCompleted() {
                view.output("onCompleted\n");
            }

            @Override
            public void onError(Throwable e) {
                view.output("onError:"+e.getMessage());
            }

            @Override
            public void onNext(String s) {
                view.output("onNext = "+s);
            }
        };
    }

    protected Subscriber<String> getStringSubscriber(final Output view,final String operation){
        return new Subscriber<String>() {
            @Override
            public void onCompleted() {
                view.output("["+operation +"]"+ "onCompleted\n");
            }

            @Override
            public void onError(Throwable e) {
                view.output("["+operation +"]" + " onError:"+e.getMessage());
            }

            @Override
            public void onNext(String s) {
                view.output("["+operation +"]"+"onNext = "+s);
            }
        };
    }



    protected Subscriber<Integer> getIntSubscriber(final Output view){
        return new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                view.output("onCompleted\n");
            }

            @Override
            public void onError(Throwable e) {
                view.output("onError");
            }

            @Override
            public void onNext(Integer s) {
                view.output("onNext = "+s);
            }
        };
    }


    protected Observable<Character> getCharObervable(){
        return Observable.create(new Observable.OnSubscribe<Character>() {
                                     @Override
                                     public void call(Subscriber<? super Character> subscriber) {
                                         try {
                                             for (int i = 0;i<10;i++) {
                                                 Thread.sleep(1000);
                                                 L.i("char = %s | time = %s", (char) (65 + i), System.currentTimeMillis());
                                                 subscriber.onNext(new Character((char) (65 + i)));
                                             }
                                             subscriber.onCompleted();
                                         }catch (Exception e){
                                             subscriber.onError(e);
                                         }
                                     }
                                 }

        ).subscribeOn(Schedulers.newThread()
        );
    }

    protected Observable<Integer> getIntObservable(){
        return Observable.create(new Observable.OnSubscribe<Integer>() {
                                     @Override
                                     public void call(Subscriber<? super Integer> subscriber) {
                                         try {
                                             for (int i = 0; i < 10; i++) {
                                                 L.i("int = %s | time = %s", i, System.currentTimeMillis());
                                                 subscriber.onNext(i);
                                                 Thread.sleep(500);
                                             }
                                             subscriber.onCompleted();
                                         } catch (Exception e) {
                                             subscriber.onError(e);
                                         }

                                     }
                                 }
        )
                .subscribeOn(Schedulers.newThread());
    }

}
