package com.wangw.rxsample.filtering;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import rx.Observable;
import rx.functions.Func1;

/**
 * ElementAt操作符：只发射指定索引位置的数据，索引是从0开始的，如果索引值小于0则抛出IndexOutOfBoundsException异常，
 * elementAtOrDefault操作符:和elementAt方法类似，只不过在找不到索引位置的数据的时候发射默认的值
 * Created by wangw on 2016/3/23.
 */
public class ElementAt extends Operation {
    @Override
    public void onOperation(Output view) {
        onSample1(view);
        onSample2(view);
    }

    /**
     * elementAtOrDefault操作符:和elementAt方法类似，只不过在找不到索引位置的数据的时候发射默认的值
     * @param view
     */
    private void onSample2(Output view) {
        Observable.just("a","b","c","d")
                .elementAtOrDefault(7,"默认值")
                .subscribe(getStringSubscriber(view));
    }

    private void onSample1(Output view) {
        Observable.just(1,2,3,4,5,6)
                .elementAt(2)
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
        return "ElementAt";
    }
}
