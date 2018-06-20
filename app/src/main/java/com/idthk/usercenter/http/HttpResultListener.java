package com.idthk.usercenter.http;



public interface HttpResultListener<T> {

    void onSuccess(T responseBean);

    void onError(Throwable e);
}
