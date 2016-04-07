package com.wangw.rxsample.subject;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;


import rx.Observer;
import rx.subjects.ReplaySubject;

/**
 * ReplaySubject:会缓存它所订阅的所有数据，然后向任意一个订阅它的观察者重发，可以限制缓存数量或时间段内的数据
 *
 * Created by wangw on 2016/4/7.
 */
public class ReplaySubjectSample extends Operation {
    @Override
    public void onOperation(Output view) {
        onSample1(view);

    }

    private void onSample1(Output view) {

        ReplaySubject<String> replaySubject = ReplaySubject.create();

        Observer<String> first = getObserver(view, "first");
        replaySubject.subscribe(first);
        for(int i=0;i<10;i++){
            char v = (char) (65+i);
            replaySubject.onNext(String.valueOf(v));
        }
        replaySubject.onCompleted();
        Observer<String> second = getObserver(view,"second");
        replaySubject.subscribe(second);

    }

    public Observer<String> getObserver(final Output view,final String tag){
        return new Observer<String>() {
            @Override
            public void onCompleted() {
                view.output("["+tag+"]onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                view.output("["+tag+"]onError="+e.getMessage());
            }

            @Override
            public void onNext(String s) {
                view.output("["+tag+"]onNext = "+s);
            }
        };
    }

    @Override
    public String toString() {
        return "ReplaySubject";
    }
}
