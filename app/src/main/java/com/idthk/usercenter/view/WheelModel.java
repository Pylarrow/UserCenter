package com.idthk.usercenter.view;

/**
 * Created by AddBean on 2016/6/27.
 */
public class WheelModel {
    int code;
    String name;
    Object obj;

    public WheelModel(String name, int code) {
        this.code = code;
        this.name = name;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
