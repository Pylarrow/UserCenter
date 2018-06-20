package com.idthk.usercenter.bll;

import com.idthk.network.http.AppRetrofit;
import com.idthk.usercenter.CloudApi;

/**
 * Created by pengyu.
 * 2018/2/8
 */

public class BaseBll {
    protected CloudApi cloudApi;
    public BaseBll(){
        cloudApi = AppRetrofit.getApi(CloudApi.class);
    }
}
