package com.wangw.rxsample.subject;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * PublishSubject:是Subject的子类，既可以当Observable也可以作为Subscribe使用
 *
 * Created by wangw on 2016/4/7.
 */
public class PublishSubjectSample extends Operation {
    @Override
    public void onOperation(Output view) {
        onSample1(view);
        onSample2(view);
    }

    /**
     * PublishSubject的特殊用法，当一个Observable是私有的时候不能被外部访问的时候，外部不关心这个Observable发射的值，只关心onCompleted的时候可以使用PublishSubject
     * @param view
     */
    private void onSample2(final Output view) {
        //1.创建一个空的Subject
        final PublishSubject publishSubject = PublishSubject.create();

        //2.订阅Subjec
        publishSubject.subscribe(new Observer<String>(){
            @Override
            public void onCompleted() {
                view.output("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                view.output("onError = "+e.getMessage());
            }

            @Override
            public void onNext(String s) {
                view.output("onNext = "+s);
            }
        });

        //3.创建一个私有的Observable，外部不可以访问
        Observable.just(1,2,3,4,5,6)
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        publishSubject.onNext(integer.toString());
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        publishSubject.onError(throwable);
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        publishSubject.onCompleted();
                    }
                })
                .subscribe();   //由于不关心它发射的数据以及onCompleted所以设置一个空的Subscribe，只是为了让Observable开始发射数据



    }

    /**
     * PublishSubject基本用法
     * @param view
     */
    private void onSample1(final Output view) {

        //1.创建一个空的Subject
        PublishSubject publishSubject = PublishSubject.create();

        //2.订阅Subjec
        publishSubject.subscribe(new Observer<String>(){
            @Override
            public void onCompleted() {
                view.output("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                view.output("onError = "+e.getMessage());
            }

            @Override
            public void onNext(String s) {
                view.output("onNext = "+s);
            }
        });

        //3.由subjec发射数据
        for(int i=0;i<10;i++){
            char v = (char) (65+i);
            publishSubject.onNext(String.valueOf(v));
        }

        publishSubject.onCompleted();
    }

    @Override
    public String toString() {
        return "PublishSubject";
    }
}
