package com.wangw.rxsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wangw.rxsample.api.Operation;
import com.wangw.rxsample.api.Output;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wangw on 2016/3/10.
 */
public class BaseActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,Output {

    @Bind(R.id.tv_content)
    TextView mTvContent;
    @Bind(R.id.list)
    ListView mList;

    private List<Operation> operations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);
        ButterKnife.bind(this);
        initListView();
    }

    public void add(Class clz){
        try {
            operations.add(((Operation)clz.newInstance()));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    private void initListView() {
        ArrayAdapter<Operation> adapter = new ArrayAdapter<Operation>(this,android.R.layout.simple_list_item_1,operations);
        mList.setAdapter(adapter);
        mList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        clear();
        operations.get(position).onOperation(this);
    }

    @Override
    public void output(String content) {
        mTvContent.setText(mTvContent.getText() + "\n" + content);
    }

    @Override
    public void clear() {
        mTvContent.setText("");
    }


}
