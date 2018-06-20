package com.idthk.network;

/**
 * Created by MVP on 2016/11/9 0009.
 *  服务端接口返回类
 */
public class ResultData<T> {
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }



    public boolean success() {
        return code == 200;
    }
}
