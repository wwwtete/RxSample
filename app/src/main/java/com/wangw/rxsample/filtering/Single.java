package com.wangw.rxsample.filtering;

import com.exlogcat.L;
import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Single操作符:和First类似，只不过这个是只允许发射一次值，如果在onComplete之前发射多个值或多个符合指定规则的值则抛出异常
 *
 * Created by wangw on 2016/3/23.
 */
public class Single extends Operation {
    @Override
    public void onOperation(Output view) {
        onSample1(view);
        onSample2(view);
        onSample3(view);
        onSample4(view);

    }

    /**
     * singleOrDefault：如果没有符合指定规则的则发射指定的默认值，如果有多个符合条件的值则抛出异常
     * @param view
     */
    private void onSample4(Output view) {
        Observable.just("a","b","a")
                .singleOrDefault("默认值", new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s.equals("A");
                    }
                })
                .subscribe(getStringSubscriber(view, "singleOrDefault"));

    }


    /**
     * 如果源Observable发射的是个空Observable则发射指定的默认值
     * @param view
     */
    private void onSample3(final Output view) {
        Observable.empty()
                .singleOrDefault("默认值")
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        view.output("[empty] onNext = " + o);
                    }
                });
    }

    /**
     * single方法只允许源Observable发射一个值不管这个值是不是唯一的都只允许发射一次，如果发射多个值则抛出NoSuchElementException异常
     * @param view
     */
    private void onSample2(final Output view) {
        Observable.just(100,200)
                .single()
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {
                        view.output("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.output("[single] onError :"+e.getMessage());
                    }

                    @Override
                    public void onNext(Object o) {
                        view.output("onNext = "+o);
                    }
                });
    }

    /**
     * 只发射符合规定的切是唯一的值，如果没有则抛出NoSuchElementException异常
     * @param view
     */
    private void onSample1(Output view) {
        Observable.just(1,2,3,5,3,2,5)
                .single(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        L.d("single call = "+integer);
                        return integer == 1;
                    }
                })
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return ""+integer;
                    }
                })
                .subscribe(getStringSubscriber(view));
    }

    @Override
    public String toString() {
        return "Single";
    }
}
