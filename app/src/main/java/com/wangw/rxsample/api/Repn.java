package com.wangw.rxsample.api;

/**
 * Created by wangw on 2016/3/3.
 */
public class Repn<T> {


    /**
     * data : {"info":{"nick_name":"白菜","head_photo":"http://images.langtianhealth.com:8765/v1/image/6e33afb63112e734329c6de0120bb318","gender":true,"home_address":{"address":"住址不知道"}},"uid":42}
     * errcode : 0
     * errmsg : 成功
     */

    private int errcode;
    private String errmsg;

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public int getErrcode() {
        return errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }
}
