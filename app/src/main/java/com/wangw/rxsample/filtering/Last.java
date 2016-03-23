package com.wangw.rxsample.filtering;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Last操作符：只发射最后一条数据或最后一条符合指定规则的数据，如果没有找到则抛出异常结束
 *
 * Created by wangw on 2016/3/23.
 */
public class Last extends Operation {
    @Override
    public void onOperation(Output view) {
        onSample1(view);
        onSample2(view);
        onSample3(view);
        onSample4(view);
    }

    /**
     * lastOrDefault:如果源Observable没有符合指定规则的数据，则发射指定的默认值数据
     * @param view
     */
    private void onSample4(Output view) {
        Observable.just("a","b","c","a","b","d")
                .lastOrDefault("默认值", new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s.equals("e");
                    }
                })
                .subscribe(getStringSubscriber(view));
    }

    /**
     * lastOrDefault：如果源Observable没有发射任何数据，则发射指定的默认值
     * @param view
     */
    private void onSample3(final Output view) {
        Observable.empty()
                .lastOrDefault("a")
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        view.output("[lastOrDefault] call = "+o+"\n");
                    }
                });
    }

    /**
     * last:发射源Observable最后一个符合指定规则的数据，如果没有则抛出异常结束
     * @param view
     */
    private void onSample2(Output view) {
        Observable.just("a","b","c","a","d")
                .last(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s.equals("a");
                    }
                })
                .subscribe(getStringSubscriber(view));
    }

    private void onSample1(Output view) {
        Observable.just("a","b","c","d","a","b")
                .last()
                .subscribe(getStringSubscriber(view));
    }

    @Override
    public String toString() {
        return "Last";
    }
}
