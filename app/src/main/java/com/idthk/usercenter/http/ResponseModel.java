package com.idthk.usercenter.http;

import java.io.Serializable;

/**
 * @Company lianyikeji
 * @Author dell
 * @Time  2017/10/20 9:44
 * @Version v1.0
 * @Description
 */


public class ResponseModel<E> implements Serializable{

    private int code;
    private String msg;
    private E data;

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

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseModel{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
