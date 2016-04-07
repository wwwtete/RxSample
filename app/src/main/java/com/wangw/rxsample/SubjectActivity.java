package com.wangw.rxsample;

import android.os.Bundle;

import com.wangw.rxsample.subject.AsyncSubjectSample;
import com.wangw.rxsample.subject.BehaviorSubjectSample;
import com.wangw.rxsample.subject.PublishSubjectSample;
import com.wangw.rxsample.subject.ReplaySubjectSample;

/**
 * Created by wangw on 2016/4/7.
 */
public class SubjectActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        add(PublishSubjectSample.class);
        add(BehaviorSubjectSample.class);
        add(ReplaySubjectSample.class);
        add(AsyncSubjectSample.class);
    }
}
