package com.wangw.rxsample.transforming;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Scan操作符：相当于是对源Observable进行一个迭代，在迭代的过程中，将上一次迭代的结果作为参数传递给下一次迭代使用
 *
 * Created by wangw on 2016/3/21.
 */
public class Scan extends Operation {
    @Override
    public void onOperation(Output view) {
        onSample1(view);
        onSample2(view);
    }


    /**
     * Scan的重载方法：scan的第一个参数作为一个种子值传递给func2，作为第一项数据，先遍历这个种子值然后再遍历源Observable中的值
     * 注意：种子值传null和不传是两种情况，传null也是合法的
     *
     * @param view
     */
    private void onSample2(Output view) {
        Observable.just("A","B","C","D","E","F")
                .scan("A",new Func2<String, String, String>() {
                    @Override
                    public String call(String s, String s2) {
                        return s+s2;
                    }
                })
                .subscribe(getStringSubscriber(view));
    }

    private void onSample1(Output view) {
        Observable.just(1,2,3,4,5)
                .scan(new Func2<Integer, Integer, Integer>() {
                    /**
                     * @param integer 代表上一次遍历的结果
                     * @param integer2 遍历源Observable中的数据
                     * @return
                     */
                    @Override
                    public Integer call(Integer integer, Integer integer2) {
                        //integers
                        return integer+integer2;
                    }
                })
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return "" + integer;
                    }
                })
                .subscribe(getStringSubscriber(view));
    }

    @Override
    public String toString() {
        return "scan";
    }
}
