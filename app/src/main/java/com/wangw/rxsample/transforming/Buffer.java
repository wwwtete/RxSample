package com.wangw.rxsample.transforming;

import android.util.Log;

import com.exlogcat.L;
import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Buffer操作符：
 * Created by wangw on 2016/3/10.
 */
public class Buffer extends Operation {

    @Override
    public void onOperation(Output view) {

        onSampe1();
        onSample2();
        onSample3();
        onSample4(view);
        onSampe5(view);

    }

    private void onSampe5(final Output view) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                //1.没隔一秒发送一次邮件
                Observable.interval(1,TimeUnit.SECONDS)
                        .subscribe(new Subscriber<Long>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Long aLong) {
                                if(aLong > 20)
                                this.unsubscribe();
                                subscriber.onNext("email"+aLong);

                                if(aLong == 15)
                                    subscriber.onError(new Throwable("Test"));
                            }
                        });
            }
        })
                .buffer(3, TimeUnit.SECONDS)//每隔三秒通知客户查收一次
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {
                        view.output("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.output("onError");
                    }

                    @Override
                    public void onNext(List<String> strings) {
                        String v = "";
                        for (String str : strings) {
                            v += "\n" + str;
                        }
                        view.output("onNext =" + v);
                    }
                });
    }

    private void onSample4(final Output view) {
        Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                for (long i=0;i<20;i++){
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        }).buffer(new Func0<Observable<?>>() {
            @Override
            public Observable<?> call() {
                L.d("buffer");
                return Observable.empty();
            }
        }).subscribe(new Subscriber<List<Long>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Long> longs) {
                String str = "";
                for (Long v : longs) {
                    str += v + ",";
                }
                view.output(str);
                L.d("onNext = " + str);
            }
        });
    }

    private void onSample3() {
        Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(final Subscriber<? super Long> subscriber) {
                Observable.interval(2, TimeUnit.SECONDS)
                        .subscribe(new Subscriber<Long>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Long aLong) {
                                subscriber.onNext(aLong);
                            }
                        });
            }
        })
                .buffer(1, TimeUnit.SECONDS)
                .subscribe(new Subscriber<List<Long>>() {
                    @Override
                    public void onCompleted() {
                        L.d("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Long> integers) {
                        String str = "";
                        for (Long v : integers) {
                            str += v + ",";
                        }
                        L.d("onNext = " + str);
                    }
                });
    }

    private void onSample2() {
        Observable.just(1,2,3,4,5,6,7,8)
                .buffer(3,1)
                .subscribe(new Subscriber<List<Integer>>() {
                    @Override
                    public void onCompleted() {
                        L.d("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        String str = "";
                        for (Integer v : integers) {
                            str += v + ",";
                        }
                        L.d("onNext = " + str);
                    }
                });
    }

    private void onSampe1() {
        Observable.interval(1, TimeUnit.SECONDS)
                .buffer(3)
                .subscribe(new Subscriber<List<Long>>() {
                    @Override
                    public void onCompleted() {
                        L.d("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.e("onError");
                    }

                    @Override
                    public void onNext(List<Long> longs) {
                        String str = "";
                        for (Long l:longs){
                            str += l+",";
                        }
                        L.d("onNext  = "+str);

                    }
                });
    }

    @Override
    public String toString() {
        return "Buffer";
    }
}
