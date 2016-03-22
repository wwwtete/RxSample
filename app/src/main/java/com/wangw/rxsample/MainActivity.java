package com.wangw.rxsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.exlogcat.L;
import com.wangw.rxsample.api.RestAdapter;
import com.wangw.rxsample.sample1.SampleActivity1;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    @Bind(R.id.lv_samples)
    ListView mLvSamples;
    private String[] mValues = {"基本应用","create操作符","transforming 操作符"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mValues);
        mLvSamples.setAdapter(adapter);
        mLvSamples.setOnItemClickListener(this);
        init();
    }

    private void init() {
        RestAdapter.init();
        L.init("RxSample");
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Class clz = null;
        switch (position){
            case 0:
                clz = SampleActivity1.class;
                break;
            case 1:
                clz = CreateOperationActivity.class;
                break;
            case 2:
                clz = TransformingOperationActivity.class;
                break;
        }
        if(clz != null){
            startActivity(new Intent(this,clz));
        }

    }
}
