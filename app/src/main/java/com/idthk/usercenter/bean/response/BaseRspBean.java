package com.idthk.usercenter.bean.response;

import com.idthk.usercenter.bean.request.BaseBean;

/**
 * Created by pengyu on 2018/1/31.
 */

public class BaseRspBean extends BaseBean {
    private String code;
    private String msg;

    public BaseRspBean() {

    }

    public String getcode() {
        return code;
    }

    public void setcode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
