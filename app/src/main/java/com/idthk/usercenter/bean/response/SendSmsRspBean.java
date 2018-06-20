package com.idthk.usercenter.bean.response;


import java.io.Serializable;

/**
 * Created by pengyu.
 * 2018/2/1
 */

public class SendSmsRspBean implements Serializable {
    private String randNum;

    public String getRandNum() {
        return randNum;
    }

    public void setRandNum(String randNum) {
        this.randNum = randNum;
    }


}
