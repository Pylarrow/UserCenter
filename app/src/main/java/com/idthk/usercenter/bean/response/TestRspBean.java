package com.idthk.usercenter.bean.response;

import com.idthk.network.ResultData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pengyu.
 * 2018/2/8
 */

public class TestRspBean implements Serializable{

    private String token;
    private String uid;
    private List<String> devs;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<String> getDevs() {
        return devs;
    }

    public void setDevs(List<String> devs) {
        this.devs = devs;
    }
}
