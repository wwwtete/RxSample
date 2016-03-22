package com.wangw.rxsample;

import android.os.Bundle;

import com.wangw.rxsample.transforming.Buffer;
import com.wangw.rxsample.transforming.Cast;
import com.wangw.rxsample.transforming.FlatMap;
import com.wangw.rxsample.transforming.GroupBy;
import com.wangw.rxsample.transforming.Scan;
import com.wangw.rxsample.transforming.SwitchMap;
import com.wangw.rxsample.transforming.Window;

/**
 *
 * Created by wangw on 2016/3/10.
 */
public class TransformingOperationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        add(Buffer.class);
        add(FlatMap.class);
        add(Cast.class);
        add(SwitchMap.class);
        add(GroupBy.class);
        add(Scan.class);
        add(Window.class);
    }
}
