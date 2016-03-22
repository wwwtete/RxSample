package com.wangw.rxsample.transforming;

import com.exlogcat.L;
import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * switchMap操作符：与Flatmap操作符类似，只是不同的是switchmap会保存最新的Observable产生的结果，丢弃旧的结果，
 *  比如：源Observable是A、B、C，通过switchMap自定义映射规则，正常映射后应该会产生A1，A2，B1,B2,C1,C2,
 *  但是如果在产生B2的时候，C1已经产生了，那么这样的最后结果应该是：A1,A2,B1,C1,C2，显然B2被丢弃了
 * Created by wangw on 2016/3/11.
 */
public class SwitchMap extends Operation {
    @Override
    public void onOperation(Output view) {
        onFlatMap(view);
        onSwitchMap(view);
        onConcatMap(view);
    }

    /**
     * FlatMap:把源Observable产生的结果转化成多个Observable，然后把这个多个Observable扁平化merge成一个Observable，并通知给订阅者
     * 注意：由于采用的是扁平化，merge的方式，所以发送的数据是无序的，
     * @param view
     */
    private void onFlatMap(Output view) {
        Observable.just("A","B","C")
                .flatMap(new Func1<String,Observable<String>>(){
                    @Override
                    public Observable<String> call(String s) {
                        int delay = 1;
                        if(s == "A")
                            delay = 2;

                        return Observable.from(new String[]{s+"1",s+"2"}).delay(delay,TimeUnit.SECONDS);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getStringSubscriber(view,"flatMap"));
    }

    /**
     * ConcatMap:和FlatMap类似，但是他发送的顺序是有序的，是按照源Observable的顺序来的，采用链接方式
     * @param view
     */
    private void onConcatMap(Output view) {
        Observable.just("A","B","C")
                .concatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        int delay = 1;
                        if(s == "A")
                            delay = 2;

                        return Observable.from(new String[]{s+"1",s+"2"}).delay(delay,TimeUnit.SECONDS);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getStringSubscriber(view,"concatMap"));
    }

    /**
     * switchMap：功能与flatmap类似，但是它会保存最新的源Observable，旧的Observable会被丢弃
     * @param view
     */
    private void onSwitchMap(Output view) {
        Observable.just("A","B","C")
                .switchMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        L.d("switchMap="+s);
                        int delay = 1;
                        if (s == "A")
                            delay = 2;
                        return Observable.from(new String[]{s + "1", s + "2"}).delay(delay, TimeUnit.SECONDS);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getStringSubscriber(view, "switchmap"));
    }

    @Override
    public String toString() {
        return "SwitchMap";
    }
}
