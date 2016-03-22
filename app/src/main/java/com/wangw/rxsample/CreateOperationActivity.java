package com.wangw.rxsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wangw.rxsample.api.Output;
import com.wangw.rxsample.createoperation.Create;
import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.createoperation.Defer;
import com.wangw.rxsample.createoperation.From;
import com.wangw.rxsample.createoperation.Interval;
import com.wangw.rxsample.createoperation.Just;
import com.wangw.rxsample.createoperation.Range;
import com.wangw.rxsample.createoperation.Repeat;
import com.wangw.rxsample.createoperation.RepeatWhen;
import com.wangw.rxsample.createoperation.Timer;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * create类型的操作符
 */
public class CreateOperationActivity extends BaseActivity{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Create Operation");
        add(Create.class);
        add(From.class);
        add(Just.class);
        add(Defer.class);
        add(Timer.class);
        add(Interval.class);
        add(Range.class);
        add(Repeat.class);
        add(RepeatWhen.class);
    }



}



