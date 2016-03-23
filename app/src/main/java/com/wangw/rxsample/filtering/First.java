package com.wangw.rxsample.filtering;

import com.exlogcat.L;
import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.BlockingObservable;
import rx.schedulers.Schedulers;

/**
 * First操作符：只发射第一项数据（或满足指定的规则的第一项数据）
 * 注意：如果源Observable在调用onComplete之前没有发射数据或没有找到指定符合规则的数据则会抛出NoSuchElementException异常
 *
 * Created by wangw on 2016/3/23.
 */
public class First extends Operation {
    @Override
    public void onOperation(Output view) {
        onSample1(view);
        onSample2(view);
        onSample3(view);
        onSample4(view);
        onSample5(view);
    }

    /**
     * 如果源Observable在调用onComplete之前没有发射数据或没有发射符合指定规则的数据则抛出NoSuchElementException异常
     * @param view
     */
    private void onSample5(final Output view) {
        Observable.empty()
                .first()
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.output("[empty] onError : " + e.getMessage());
                    }

                    @Override
                    public void onNext(Object o) {
                        view.output("onNext = " + 0);
                    }
                });
    }

    /**
     * firstOrDefault：指定一个过滤规则，然后发射这个规则为true的第一项数据，如果都没有通过则发射指定默认的值
     * @param view
     */
    private void onSample4(Output view) {
        Observable.just(2,4,6,8)
                .firstOrDefault(1, new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        L.d("call = "+integer);
                        return integer % 2 != 0;
                    }
                })
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return integer+"";
                    }
                })
                .subscribe(getStringSubscriber(view));
    }

    /**
     * firstOrDefault：和first方法类似，但是在源Observable没有发射任何数据的时候发射一个指定的默认值
     * @param view
     */
    private void onSample3(final Output view) {
        Observable.empty()
                .firstOrDefault("默认值")
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        view.output("call  = " + o);
                    }
                });
    }

    /**
     * first方法重载：指定一个过滤规则，只发射满足这个规则的第一个数据
     * @param view
     */
    private void onSample2(Output view) {
        Observable.just(1,2,3,4,5,6,8,10)
                .first(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer % 5 == 0;
                    }
                })
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return integer+"";
                    }
                })
                .subscribe(getStringSubscriber(view));
    }

    private void onSample1(Output view) {
        Observable.just("a","b","c")
                .first()
                .subscribe(getStringSubscriber(view));
    }

    @Override
    public String toString() {
        return "First";
    }
}
