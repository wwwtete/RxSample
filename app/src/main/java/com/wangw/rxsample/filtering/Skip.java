package com.wangw.rxsample.filtering;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 *
 * Skip操作符:将源Observable发射的数据前N项过滤掉
 *  skip重载方法：指定一个时长，过滤掉源Observable开始的那段时间的数据
 *
 * Created by wangw on 2016/3/24.
 */
public class Skip extends Operation {
    @Override
    public void onOperation(Output view) {
        onSample1(view);
        onSample2(view);
    }

    /**
     * skip重载方法：指定一个时长，他它会丢弃源Observable开始的那段时间的数据
     * @param view
     */
    private void onSample2(Output view) {
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .skip(500,TimeUnit.MILLISECONDS)
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        return aLong+"";
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getStringSubscriber(view, "skip time"));
    }

    private void onSample1(Output view) {
        Observable.just(1,2,3,4,5,6,7,8)
                .skip(3)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return integer+"";
                    }
                })
                .subscribe(getStringSubscriber(view,"skip count"));
    }

    @Override
    public String toString() {
        return "Skip";
    }
}
