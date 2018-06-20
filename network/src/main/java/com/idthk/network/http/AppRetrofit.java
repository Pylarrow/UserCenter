package com.idthk.network.http;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.idthk.network.NullStringToEmptyAdapterFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MVP on 2016/11/17 0017.
 */

public class AppRetrofit {

    private static AppRetrofit retrofit = null;

    final static Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
            .serializeNulls()
            .create();

    private static Map<String, Object> map = new HashMap<>();
    private BasicParamsInterceptor baseParamsInterceptor;


    private AppRetrofit() {

    }


    public static AppRetrofit getSingleton() {
        if (retrofit == null) {
            synchronized (AppRetrofit.class) {
                if (retrofit == null) {
                    retrofit = new AppRetrofit();
                }
            }
        }
        return retrofit;
    }

    public static <w> w getApi(Class<w> wClass) {
        if (map.containsKey(wClass.getName())) {
            return (w) map.get(wClass.getName());
        } else {
            throw new IllegalArgumentException("Api为空，可能没有进行初始化！");
        }
    }

    public void init(Builder builder) {
        Map<String, String> tempParams = getBaseParams(builder);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("", message);
                Platform.get().log(Platform.WARN, message, null);
            }
        });
        baseParamsInterceptor = new BasicParamsInterceptor.Builder()
                .addParamsMap(tempParams)
                .addQueryParamsMap(tempParams)
                .build();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(baseParamsInterceptor)
                .addNetworkInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(builder.getHostPath())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient).build();
        map.put(builder.getAppClass().getName(), retrofit.create(builder.getAppClass()));

    }

    public void updateBasicParams(Long userId) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId + "");
        baseParamsInterceptor.updateParamsMap(params);
        baseParamsInterceptor.updateQueryParamsMap(params);
    }

    public void removeBasicParams(String userId) {
        baseParamsInterceptor.removeQueryParamsMap(userId);
    }


    public Map<String, String> getBaseParams(Builder builder) {
        String clientSystemType = builder.getClientSystemType();
        String appVersion = builder.getAppVersion();
        Map<String, String> params = new HashMap<>();
        if (clientSystemType != null && appVersion != null) {
            params.put("clientSystemType", clientSystemType);
            params.put("appVersion", builder.getAppVersion());
        }
        return params;
    }
}
