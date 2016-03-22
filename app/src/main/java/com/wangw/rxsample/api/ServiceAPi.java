package com.wangw.rxsample.api;

import com.wangw.rxsample.sample1.User;


import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wangw on 2016/3/3.
 */
public interface ServiceAPi {


    //user/42/info/basic?token=1234567
    @GET("user/{uid}/info/basic")
    Observable<Repn<User>> getUserInfo(@Path("uid") String uid,@Query("token") String token);


    void demo(Temp<User> callback);

}
