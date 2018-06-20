package com.idthk.usercenter.utils;

/**
 * Created by pengyu.
 * 2018/2/8
 */

public enum HostAddress {
    TEST(
            "http://sgv638-test.idtlive.com/api/"
            //"https://app.idtlive.com/java/WRM500/"
    );

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    HostAddress(String address) {
        this.address = address;
    }
}
