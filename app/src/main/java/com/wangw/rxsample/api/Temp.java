package com.wangw.rxsample.api;

import com.wangw.rxsample.sample1.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wangw on 2016/3/17.
 */
public abstract class Temp<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

    }
}
