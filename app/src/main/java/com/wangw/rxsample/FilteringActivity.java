package com.wangw.rxsample;

import android.os.Bundle;

import com.wangw.rxsample.filtering.Debounce;
import com.wangw.rxsample.filtering.Distinct;
import com.wangw.rxsample.filtering.ElementAt;
import com.wangw.rxsample.filtering.Filter;
import com.wangw.rxsample.filtering.First;
import com.wangw.rxsample.filtering.IgnoreElements;
import com.wangw.rxsample.filtering.Last;
import com.wangw.rxsample.filtering.OfType;
import com.wangw.rxsample.filtering.Sample;
import com.wangw.rxsample.filtering.Single;
import com.wangw.rxsample.filtering.ThrottleFirst;

/**
 *
 * Filtering：过滤类的操作符
 * Created by wangw on 2016/3/22.
 */
public class FilteringActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        add(Debounce.class);
        add(Distinct.class);
        add(ElementAt.class);
        add(Filter.class);
        add(OfType.class);
        add(First.class);
        add(Single.class);
        add(Last.class);
        add(Sample.class);
        add(ThrottleFirst.class);

        add(IgnoreElements.class);
    }
}
