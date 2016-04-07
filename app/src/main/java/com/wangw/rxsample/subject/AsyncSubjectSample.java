package com.wangw.rxsample.subject;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import rx.Observer;
import rx.subjects.AsyncSubject;

/**
 * AsyncSubject:当源Observable完成时AsyncSubject只会发布最后一条数据给已经订阅的每一个观察者
 *
 * Created by wangw on 2016/4/7.
 */
public class AsyncSubjectSample extends Operation {
    @Override
    public void onOperation(Output view) {
        onSample1(view);
    }

    private void onSample1(final Output view) {
        AsyncSubject<String> asyncSubject = AsyncSubject.create();
        asyncSubject.subscribe(getObserver(view,"first"));
        asyncSubject.subscribe(getObserver(view,"second"));

        //3.由subjec发射数据
        for(int i=0;i<10;i++){
            char v = (char) (65+i);
            asyncSubject.onNext(String.valueOf(v));
        }

        asyncSubject.onCompleted();
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
        return "AsyncSubject";
    }
}
