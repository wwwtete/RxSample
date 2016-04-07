package com.wangw.rxsample;

import android.os.Bundle;

import com.wangw.rxsample.combining.CombineLatest;
import com.wangw.rxsample.combining.Join;

/**
 * Created by wangw on 2016/4/6.
 */
public class CombiningActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        add(CombineLatest.class);
        add(Join.class);
    }
}
