package com.wangw.rxsample.subject;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import rx.Observer;
import rx.subjects.BehaviorSubject;

/**
 *BehaviorSubject:是Subject子类，它会先向她的订阅者发送一条截止订阅前最新的一条数据或初始值，然后正常发送数据
 *
 * Created by wangw on 2016/4/7.
 */
public class BehaviorSubjectSample extends Operation {
    @Override
    public void onOperation(Output view) {
        onSample1(view);
    }

    private void onSample1(final Output view) {

        BehaviorSubject<String> behaviorSubject = BehaviorSubject.create("默认值");

        //2.订阅Subjec
        behaviorSubject.subscribe(new Observer<String>(){
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
            behaviorSubject.onNext(String.valueOf(v));
        }

        behaviorSubject.onCompleted();

    }

    @Override
    public String toString() {
        return "BehaviorSubject";
    }
}
