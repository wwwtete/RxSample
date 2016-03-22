package com.wangw.rxsample.transforming;

import com.exlogcat.L;
import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Window操作符：window操作符与Buffer操作符类似，只不过list缓存的是原始数据列表，
 * 而window操作符产生的结果是一个Observable，订阅者可以再次对着结果进行订阅处理
 *
 * 注意：window操作符产生的结果不依靠任何默认的线程，依靠的是产生源Observable的线程，所以如果想要操作UI，记得切换回主线程
 *
 * Created by wangw on 2016/3/21.
 */
public class Window extends Operation {
    @Override
    public void onOperation(Output view) {
//        onSample1(view);
        onSample2(view);
    }

    /**
     *
     * @param view
     */
    private void onSample2(final Output view) {
        Observable.interval(2,TimeUnit.SECONDS)
                .take(20)
                .window(1, TimeUnit.SECONDS)
                /**注意：由于数据源Observable是在子线程中产生的数据，所以在Subscribe时如果要操作ui一定要记得切换到主线程*/
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Observable<Long>>() {
                    @Override
                    public void onCompleted() {
                        view.output("[subscribe]: " + "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.e("[subscribe]:" + e.getMessage());
                        view.output("[subscribe]: " + "onError");
                    }

                    @Override
                    public void onNext(Observable<Long> longObservable) {
                        view.output("[subscribe]: onNext");
                        longObservable
                                /**注意：由于只有首次创建longObservable才会走这个onNext方法，以后同样的数据不会再走这个方法，
                                 * 所以必须要再次切换回主线程
                                 * */
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<Long>() {
                                    @Override
                                    public void onCompleted() {
                                        view.output("[integerObservable]: onCompleted");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        L.e("[integerObservable]:" + e.getMessage());
                                        view.output("[integerObservable]: onError");
                                    }

                                    @Override
                                    public void onNext(Long aLong) {
                                        view.output("[integerObservable]: onNext = " + aLong);
                                    }
                                });
                    }
                });
    }

    private void onSample1(final Output view) {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .window(2)
                .subscribe(new Subscriber<Observable<Integer>>() {
                    @Override
                    public void onCompleted() {
                        view.output("[subscribe]: " + "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.output("[subscribe]: " + "onError");
                    }

                    @Override
                    public void onNext(Observable<Integer> integerObservable) {
                        view.output("[subscribe]: onNext");
                        integerObservable.subscribe(new Subscriber<Integer>() {
                            @Override
                            public void onCompleted() {
                                view.output("[integerObservable]: onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.output("[integerObservable]: onError");
                            }

                            @Override
                            public void onNext(Integer integer) {
                                view.output("[integerObservable]: onNext = "+integer);
                            }
                        });
                    }
                });
    }

    @Override
    public String toString() {
        return "Window";
    }
}
