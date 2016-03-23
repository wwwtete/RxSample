package com.wangw.rxsample.filtering;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import rx.Observable;

/**
 * OfType操作符：是Filter操作符的一个特殊形式，它只发射指定class类型的数据给订阅者
 *
 * Created by wangw on 2016/3/23.
 */
public class OfType extends Operation {
    @Override
    public void onOperation(Output view) {

        Observable.just("a",1,"b",2,"c","3")
                .ofType(String.class)
                .subscribe(getStringSubscriber(view));

    }

    @Override
    public String toString() {
        return "OfType";
    }
}
