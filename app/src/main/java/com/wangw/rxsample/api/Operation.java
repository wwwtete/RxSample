package com.wangw.rxsample.api;

import com.wangw.rxsample.api.Output;

import java.util.Objects;

import rx.Subscriber;

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

}
