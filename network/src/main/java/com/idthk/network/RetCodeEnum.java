package com.idthk.network;

/**
 * Created by MVP on 2016/11/9 0009.
 * 公共返回码枚举
 *
 */
public enum RetCodeEnum {

    //todo 需要把相关的的code跟对应的msg添加进来
    //SYS_ERROR(-1, "系统异常"),
    APP_ERROR(405, "系统异常"),
    NET_WORK_ERROR(110, "网络错误");


    private int code;

    private String msg;

    RetCodeEnum(int code, String msg) {
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
