package com.wangw.rxsample.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangw on 2016/3/3.
 */
public class RestAdapter {

    private static Retrofit mRetorfit;
    private static ServiceAPi mApi;

    public static void init(){
        if(mRetorfit != null)
            return;
        mRetorfit  = new Retrofit.Builder()
                .baseUrl("http://192.168.1.52:20083/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static ServiceAPi getApi(){
        if(mRetorfit == null){
            init();
        }
        if(mApi == null){
           mApi =  mRetorfit.create(ServiceAPi.class);
        }
        return mApi;
    }

}
