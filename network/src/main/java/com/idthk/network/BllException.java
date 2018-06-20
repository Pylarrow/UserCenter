package com.idthk.network;

/**
 * Created by MVP on 2016/11/9 0009.
 * 业务异常
 */

public class BllException extends RuntimeException {

    private int code;

    private String msg;


    public BllException(RetCodeEnum retCodeEnum) {
        this(retCodeEnum.getCode(), retCodeEnum.getMsg());
    }

    public BllException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

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
}
