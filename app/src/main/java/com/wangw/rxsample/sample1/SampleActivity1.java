package com.wangw.rxsample.sample1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.exlogcat.L;
import com.wangw.rxsample.R;
import com.wangw.rxsample.api.Repn;
import com.wangw.rxsample.api.RestAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * RxJava基本应用
 * 所用到的操作符：
 * create：创建一个Obserable
 * map:将一个事件流转换成另一个事件流
 * subscribe：事件流接收处理者
 */
public class SampleActivity1 extends AppCompatActivity {

    @Bind(R.id.tv_content)
    TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample1);
        ButterKnife.bind(this);
    }


    public void onClick(View view){
        RestAdapter.getApi().getUserInfo("88", "1234567")
                .subscribeOn(Schedulers.io())
                .map(new Func1<Repn<User>, User>() {
                    @Override
                    public User call(Repn<User> userRepn) {
                        L.d("call Thread=%s", Thread.currentThread().getName());
                        return userRepn.getData();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        L.d("onCompleted Thread=%s", Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.e("onError error=%s", e.getMessage());
                    }

                    @Override
                    public void onNext(User s) {
                        L.d("onNext Thread=%s", Thread.currentThread().getName());
                        mTvContent.setText(s.getInfo().getNick_name());
                    }
                });
    }


}
