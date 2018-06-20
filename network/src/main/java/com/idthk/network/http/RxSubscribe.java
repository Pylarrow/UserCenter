package com.idthk.network.http;

import android.app.Application;
import android.content.Context;

import com.dgrlucky.log.LogX;
import com.idthk.network.BllException;
import com.idthk.network.RetCodeEnum;
import com.idthk.network.dialog.WaitDialog;
import com.idthk.network.utils.NetWorkUtils;

import rx.Subscriber;

/**
 * Created by MVP on 2016/11/17 0017.
 */

public abstract class RxSubscribe<T> extends Subscriber<T> {


    private Context mContext;


    private WaitDialog dialog;

    private boolean isShowDialog;


    public RxSubscribe(Context context, boolean isShowDialog) {
        this.mContext = context;
        this.isShowDialog = isShowDialog;
    }

    public RxSubscribe(Context context) {
        this.mContext = context;
        this.isShowDialog = true;
    }

    public RxSubscribe() {
    }

    @Override
    public void onCompleted() {
        if (isShowDialog())
            dialog.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isShowDialog()) {
            if(mContext!=null){
                dialog = new WaitDialog.Builder(mContext, "").create();
                dialog.show();
            }
            super.onStart();
        }
    }

    @Override
    public void onNext(T t) {
        LogX.e(t);
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {

        if (!NetWorkUtils.isNetConnection(mContext)) {
            onError(RetCodeEnum.NET_WORK_ERROR.getCode(), RetCodeEnum.NET_WORK_ERROR.getMsg());
        } else if (e instanceof BllException) {
            onError(((BllException) e).getCode(), ((BllException) e).getMsg());
        } else {
            e.printStackTrace();
            onError(RetCodeEnum.APP_ERROR.getCode(), RetCodeEnum.APP_ERROR.getMsg());
        }

        if (isShowDialog()){
            dialog.dismiss();
        }
    }

    protected abstract void onSuccess(T t);

    protected abstract void onError(int code, String message);

    public boolean isShowDialog() {
        return isShowDialog;
    }

}
