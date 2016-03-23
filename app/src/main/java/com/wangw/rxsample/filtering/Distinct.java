package com.wangw.rxsample.filtering;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 *
 * Distinct操作符：主要用来数据去重，所有重复的数据都会被过滤掉，只发射没有发射过的数据
 *      Distinct操作符有个重载方法，可以指定一个规则函数，返回一个Key，对比这个key来判断是否相同
 * distinctUntilChanged操作符：和Distinct函数类似，只不过这个操作符是去除连续重复的数据，
 *    distinctUntilChanged也有一个重载方法，和Distinct类似，也是返回一个Key，对比这个key来判断是否相同
 *
 * Created by wangw on 2016/3/22.
 */
public class Distinct extends Operation {
    @Override
    public void onOperation(Output view) {
//        onSample1(view);
//        onSample2(view);
//        onSample3(view);
        onSample4(view);
    }

    /**
     *
     * @param view
     */
    private void onSample4(final Output view) {
        Observable.just(1,2,3,1,1,3,3,5,5)
                .distinctUntilChanged(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return "distinctUntilChanged"+integer;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        view.output("[distinctUntilChanged] onNext = "+integer);
                    }
                });
    }


    private void onSample3(final Output view) {
        Observable.just(1,2,2,3,4,4,2,5)
                .distinct(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return "demo"+integer;
                    }
                }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                view.output("onNext = " + integer);
            }
        });
    }

    /**
     *
     * @param view
     */
    private void onSample2(final Output view) {
        Observable.just(1,2,2,5,3,3,2,5,5)
                .distinctUntilChanged()
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        view.output("[连续去重]onNext = "+integer);
                    }
                });
    }

    /**
     *
     * @param view
     */
    private void onSample1(final Output view) {
        Observable.just(1,2,3,4,3,5,3,2,1)
                .distinct()
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        view.output("onNext = " + integer);
                    }
                });
    }

    @Override
    public String toString() {
        return "Distinct";
    }
}
