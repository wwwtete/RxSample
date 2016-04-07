package com.wangw.rxsample.combining;

import com.exlogcat.L;
import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.functions.FuncN;

/**
 * combineLatest：操作符可以将2~9个或一个List的Observable发射的数据按照指定的规则组合起来再发射，但是有两个前提
 * 1.必须保证所有的Observable都发射过至少一条数据
 * 2.满足条件1的情况下，任何一个Observable发射的数据都将与其它的Observable最后一条数据按照指定的规则发射出去
 * 注意：必须是所有的Observable都调用onCompleted方法才会调用订阅者的onCompleted方法
 *
 * Created by wangw on 2016/4/6.
 */
public class CombineLatest extends Operation {


    @Override
    public void onOperation(Output view) {

        onSample1(view);
//        onSample2(view);
//         onSample3(view);
    }

    /**
     * combineLatest方法中必须保证传入所有的Observable都发射一条数据，如果有一个Observable一个也不发射则其他的Observable也不会发射给订阅者
     * @param view
     */
    private void onSample3(Output view) {
        Observable.combineLatest(getIntObservable(), Observable.empty(), new Func2<Integer, Object, String>() {
            @Override
            public String call(Integer integer, Object o) {
                return integer.toString()+o;
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getStringSubscriber(view));
    }

    /**
     * combineLatest重载方法接收一个list为参数
     * @param view
     */
    private void onSample2(Output view) {
        List<Observable<Character>> list = new ArrayList<>();
        list.add(getCharObervable());
        list.add(getCharObervable());
        list.add(getCharObervable());
        list.add(getCharObervable());
        Observable.combineLatest(list, new FuncN<String>() {
            @Override
            public String call(Object... args) {
                StringBuilder builder = new StringBuilder();
                for (Object obj : args){
                    builder.append(obj);
                }
                return builder.toString();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getStringSubscriber(view));
    }

    /**
     * combineLatest方法中必须所有的源Observable都调用了onCompleted方法才会进入到onCompleted
     * @param view
     */
    private void onSample1(Output view) {

        Observable.combineLatest(getIntObservable(), getCharObervable(), new Func2<Integer, Character, String>() {
            @Override
            public String call(Integer integer, Character character) {
                L.d("[call] int = %s | char = %s",integer,character);
                return String.valueOf(integer.toString()+(char)character);
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getStringSubscriber(view));


    }



    @Override
    public String toString() {
        return "CombineLatest";
    }
}
