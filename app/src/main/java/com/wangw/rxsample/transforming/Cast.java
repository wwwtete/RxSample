package com.wangw.rxsample.transforming;

import android.view.View;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import rx.Observable;
import rx.functions.Action1;

/**
 * Cast 操作符：其实跟Map操作符功能类似，不同的地方在于，map操作符可以自定义转换规则，而cast只需要传入一个类型Class即可
 * 如果不能转换成指定类型的class则会抛出ClassCastException异常，直接调用onError方法
 *
 * Created by wangw on 2016/3/18.
 */
public class Cast extends Operation {
    @Override
    public void onOperation(Output view) {
        onSample1(view);
        onSample2(view);
    }

    /**
     *  转换成功的Bug
     * @param view
     */
    private void onSample2(final Output view) {
        Temp temp = new Temp();
        Observable.just(temp)
                .cast(BaseTemp.class)
                .subscribe(new Action1<BaseTemp>() {
                    @Override
                    public void call(BaseTemp baseTemp) {
                        view.output("name = " + baseTemp.name);
                    }
                });
    }

    /**
     * 转换失败的Demo
     * @param view
     */
    private void onSample1(Output view) {
        Observable.just(1,2,3,4,5,6,7,8)
                .cast(String.class)
                .subscribe(getStringSubscriber(view,"String"));
    }

    @Override
    public String toString() {
        return "Cast";
    }

    class BaseTemp{
        public String name ="base";
    }

    class Temp extends BaseTemp{
        public int age;
    }


}
