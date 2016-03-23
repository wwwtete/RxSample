package com.wangw.rxsample.filtering;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import rx.Observable;
import rx.functions.Func1;

/**
 * Filter操作符:只有满足自定义的过滤条件才会被发射给订阅者
 *
 * Created by wangw on 2016/3/23.
 */
public class Filter extends Operation {
    @Override
    public void onOperation(Output view) {
        onSample1(view);
    }

    private void onSample1(Output view) {
        Observable.just(1,2,3,4,5,6,8,9)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer % 2 == 0;
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

    @Override
    public String toString() {
        return "Filter";
    }
}
