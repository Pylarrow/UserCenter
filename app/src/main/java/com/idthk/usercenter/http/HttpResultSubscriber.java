package com.idthk.usercenter.http;


import com.dgrlucky.log.LogX;

import rx.Subscriber;


public class HttpResultSubscriber<T> extends Subscriber<T> {

    private HttpResultListener<T> mListener;

    public HttpResultSubscriber(HttpResultListener<T> listener) {
        mListener = listener;
    }


    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (mListener != null) {
            //todo 通过判断错误码来确定是否需要重新登录
        }
    }

    @Override
    public void onNext(T responseBean) {
        LogX.e(responseBean);
        if (mListener != null) {
            mListener.onSuccess(responseBean);
        }
    }

}
