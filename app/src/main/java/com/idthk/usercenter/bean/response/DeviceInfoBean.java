package com.idthk.usercenter.bean.response;

import java.io.Serializable;

/**
 * Created by pengyu.
 * 2018/3/23
 */

public class DeviceInfoBean implements Serializable {

    private boolean expired;

    private long expire_time;
    private long activate_time;

    public long getActivate_time() {
        return activate_time;
    }

    public void setActivate_time(long activate_time) {
        this.activate_time = activate_time;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public long getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(long expire_time) {
        this.expire_time = expire_time;
    }
}
