package com.wangw.rxsample.createoperation;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import rx.Observable;
import rx.Subscriber;

/**
 * create操作符：创建一个Observable对象的基本方法
 *
 * Created by wangw on 2016/3/4.
 */
public class Create extends Operation {





    @Override
    public void onOperation(final Output output) {
        //1.调用Observable.create(subscribe)静态方法创建一个Observable对象
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                //3.得到Subscribe对象，调用Subscribe.onNex(T)方法将处理结果交给Subscribe对象
                subscriber.onNext("Hell Word");
                //4.处理完毕后调用onCompleted方法通知Subscribe事件处理结束
                subscriber.onCompleted();
            }
        })//2.调用Observable.subscribe(subscribe)方法监听回调方法
        .subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                output.output("[onCompleted]");
            }

            @Override
            public void onError(Throwable e) {
                output.output("[onError]");
            }

            @Override
            public void onNext(String s) {
                output.output("[onNext]="+s);
            }
        });
    }

    @Override
    public String toString() {
        return "Create Operation";
    }
}
