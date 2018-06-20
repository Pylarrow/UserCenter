package com.idthk.usercenter.bean.response;

import java.io.Serializable;

/**
 * Created by pengyu.
 * 2018/3/23
 */

public class RenewBean implements Serializable {
    private double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
