package com.idthk.usercenter.http;

import com.idthk.usercenter.config.http.internal.JsonInterceptor;
import com.idthk.usercenter.config.http.internal.LogInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



public class RetrofitFactory {

    public static final String BASE_URL = "http://sgv638-test.idtlive.com/api/";//测试后台

    private static final long TIMEOUT = 30;

    static TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        @Override
        public void checkClientTrusted(
                java.security.cert.X509Certificate[] x509Certificates,
                String s) throws java.security.cert.CertificateException {
        }

        @Override
        public void checkServerTrusted(
                java.security.cert.X509Certificate[] x509Certificates,
                String s) throws java.security.cert.CertificateException {
        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }
    }};
    private static OkHttpClient okHttpClient;


    // Retrofit是基于OkHttpClient的，可以创建一个OkHttpClient进行一些配置

    private static OkHttpClient getHttpClient() {
        if(okHttpClient!=null){
            return okHttpClient;
        }
        okHttpClient = new OkHttpClient();
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();


            okHttpClient = okHttpClient.newBuilder()
                    .sslSocketFactory(sslSocketFactory)
                    .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
//                    .addNetworkInterceptor(new JsonInterceptor())
                    .addNetworkInterceptor(new LogInterceptor())

                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request().newBuilder()
                                    .addHeader("content-type", "application/json")
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .build();
        } catch (Exception e) {

        }

        return okHttpClient;
    }


    // 添加通用的Header
            /*
            这里可以添加一个HttpLoggingInterceptor，因为Retrofit封装好了从Http请求到解析，
            出了bug很难找出来问题，添加HttpLoggingInterceptor拦截器方便调试接口
             */
            /*.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {

                }
            }).setLevel(HttpLoggingInterceptor.Level.BASIC))*/


    private static RetrofitService retrofitService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
                    // 添加Gson转换器
            .addConverterFactory(GsonConverterFactory.create())
                    // 添加Retrofit到RxJava的转换器
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(getHttpClient())
            .build()
            .create(RetrofitService.class);

    public static RetrofitService getInstance() {
        return retrofitService;
    }

}
