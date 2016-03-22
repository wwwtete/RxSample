package com.wangw.rxsample.transforming;

import com.exlogcat.L;
import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.GroupedObservable;

/**
 * GroupBy操作符：相当于是对源Observable按照指定的规则进行分组，得到一个 Observable<GroupedObservable<K, T>>新数据源
 *  GroupedObservable中有一个方法：getkey()用于获取指定规则返回的Key，可以通过该值获取新数据源的数据
 * groupBy有一个重载方法，第一个参数代表指定规则，第二个参数代表转换规则相当于map操作
 *  注意：由于goupBy操作符是将源Observable按照指定规则分解成多个GroupedObservable的Observable，一旦有subscribe操作，
 *  那么GroupedObservable就开始缓存数据了，因此如果不去处理这个GroupedObservable就会一直保存，就会有可能引起内存溢出
 *  所以如果不想处理这个GoupedObservable那么也一定要调用take(0)操作符来丢弃这些缓存
 *
 *
 * Created by wangw on 2016/3/18.
 */
public class GroupBy extends Operation {
    @Override
    public void onOperation(Output view) {

        onSample1(view);
        onSample2(view);
        onSample3(view);
    }

    /**
     * 不处理GroupedObservable就会引起内存溢出的Demo
     * @param view
     */
    private void onSample3(Output view) {
        Observable.interval(100,TimeUnit.MICROSECONDS)
                .groupBy(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        return aLong % 2 == 0 ? "偶数" : "基数";
                    }
                })
                .subscribe(new Subscriber<GroupedObservable<String, Long>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        L.e("onError:"+e.getMessage());
                    }

                    @Override
                    public void onNext(final GroupedObservable<String, Long> stringLongGroupedObservable) {
                        //如果是基数则不去处理
                        if("偶数".equals(stringLongGroupedObservable.getKey())){
                            stringLongGroupedObservable.subscribe(new Action1<Long>() {
                                @Override
                                public void call(Long aLong) {
                                    L.d("key = "+ stringLongGroupedObservable.getKey()+" | value = "+aLong);
                                }
                            });
                        }else {
                            //如果不对某个GroupedObservable对象处理应该调用take(0)操作符来丢弃缓存，如果不调用则会引起内存溢出的风险
                            stringLongGroupedObservable.take(0);
                        }
                    }
                });
    }

    /**
     *  groupBy(func1):func1参数代表指定分组规则
     * @param view
     */
    private void onSample1(final Output view) {
        Observable.interval(1, TimeUnit.SECONDS)
                .take(20)
                .groupBy(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        //1.将原始数据根据奇偶数分成两组数据
                        L.d("call:" + aLong);
                        return aLong % 2 != 0 ? "基数: " : "偶数: ";
                    }
                })
                        //2.由于产生原始数据是在异步线程，所以想要在UI上展示必须将数据转换到Main线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GroupedObservable<String, Long>>() {
                    @Override
                    public void onCompleted() {
                        view.output("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.e(e.getMessage());
                    }

                    @Override
                    public void onNext(final GroupedObservable<String, Long> stringLongGroupedObservable) {
                        //3.stringLongGroupedObservable 相当于是一个map，存储分组后的数据，通过getkey方法可以获取到当前的Key值，value必须通过.subscribe方法获取
                        L.d("onNext" + stringLongGroupedObservable.getKey());
                        view.output("onNext:" + stringLongGroupedObservable.getKey());
                        stringLongGroupedObservable
                                //4.虽然上面已经指定了Main线程，但是只在第一次的时候走onNext方法，以后同一个Key的数据会直接进入stringLongGroupedObservable的onNext方法，所以必须再次指定Main线程
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<Long>() {
                                    @Override
                                    public void onCompleted() {
                                        view.output(stringLongGroupedObservable.getKey() + ":onCompleted");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        L.e(e.getMessage());
                                    }

                                    @Override
                                    public void onNext(Long aLong) {

                                        view.output("key = " + stringLongGroupedObservable.getKey() + " | value=" + aLong);
                                    }
                                });
                    }
                });
    }

    /**
     * GroupBy的重载方法：
     *  groupBy(func1,func1)第一个参数指定分组规则，第二参数用于将源Observable中的数据转换，相当于Map操作
     * @param view
     */
    private void onSample2(final Output view) {
        Observable.interval(1,TimeUnit.SECONDS)
                .take(10)
                .groupBy(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        return aLong % 2 == 0 ? "偶数" : "基数";
                    }
                }, new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        return "call_"+aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GroupedObservable<String, String>>() {
                    @Override
                    public void onCompleted() {
                        view.output("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.output("onError:"+e.getMessage());
                    }

                    @Override
                    public void onNext(final GroupedObservable<String, String> stringStringGroupedObservable) {
                        view.output("onNext:"+stringStringGroupedObservable.getKey());
                        stringStringGroupedObservable.subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {
                                view.output(stringStringGroupedObservable.getKey()+":onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(String s) {
                                view.output("key:"+stringStringGroupedObservable.getKey()+" | value = "+s);
                            }
                        });
                    }
                });
    }

    @Override
    public String toString() {
        return "GroupBy";
    }
}
