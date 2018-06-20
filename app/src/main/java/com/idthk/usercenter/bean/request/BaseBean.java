package com.idthk.usercenter.bean.request;

import com.google.gson.Gson;

import java.io.Serializable;

import okhttp3.RequestBody;

/**
 * Created by Pop on 2018/1/31.
 */

public class BaseBean implements Serializable {
    public BaseBean() {

    }

    public String toJsonString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }

    public static <T> T fromJson(String str, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(str, type);
    }

    public static RequestBody getRequestBody(String jsonStr) {
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);
    }
}
