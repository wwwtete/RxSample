package com.wangw.rxsample.transforming;

import android.app.Activity;
import android.view.View;

import com.exlogcat.L;
import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import java.io.File;
import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Flat Map操作符：
 * Created by wangw on 2016/3/11.
 */
public class FlatMap extends Operation {
    @Override
    public void onOperation(Output view) {
        onSample01(view);
//        onSample02(view);
//        onSample03(view);
    }



    private void onSample03(Output view) {
        Observable.just(((Activity)view).getCacheDir())
                .flatMap(new Func1<File, Observable<File>>() {
                    @Override
                    public Observable<File> call(File file) {
                        L.d("onSample03 = "+file.getAbsolutePath());
                        return listFiles(file);
                    }
                })
                .map(new Func1<File, String>() {
                    @Override
                    public String call(File file) {
                        return file.getAbsolutePath();
                    }
                })
                .subscribe(getStringSubscriber(view));
    }

    private static Observable<File> listFiles(File file){
        if(file.isDirectory()){
            return Observable.from(file.listFiles())
                    .flatMap(new Func1<File, Observable<File>>() {
                        @Override
                        public Observable<File> call(File file) {
                            L.d("listFiles = "+file.getAbsolutePath());
                            return listFiles(file);
                        }
                    });
        }else {
            L.d("else = "+file.getAbsolutePath());
            return Observable.just(file);
        }
    }

    /**
     * flatMapIterable:
     * @param view
     */
    private void onSample02(Output view) {
        Observable.range(1,5)
                .flatMapIterable(new Func1<Integer, Iterable<String>>() {
                    @Override
                    public Iterable<String> call(Integer integer) {
                        ArrayList<String> arr = new ArrayList<String>();
                        for (int i = 0; i < integer; i++) {
                            arr.add("flatmapIterable " + integer);
                        }
                        return arr;
                    }
                })
                .subscribe(getStringSubscriber(view));
    }

    /**
     * flatMap
     * @param view
     */
    private void onSample01(final Output view) {
        Observable.range(1,5)
                .flatMap(new Func1<Integer, Observable<String>>() {
                    @Override
                    public Observable<String> call(Integer integer) {
                        return Observable.just("flatMap "+integer);
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        view.output("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.output("onError");
                    }

                    @Override
                    public void onNext(String s) {
                        view.output("onNext = "+s);
                    }
                });

    }


    @Override
    public String toString() {
        return "FlatMap";
    }
}
