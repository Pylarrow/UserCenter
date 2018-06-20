package com.idthk.usercenter.http;


import com.idthk.usercenter.bean.response.BaseRspBean;
import com.idthk.usercenter.bean.response.LoginRspBean;
import com.idthk.usercenter.bean.response.RegisterRspBean;
import com.idthk.usercenter.bean.response.SendSmsRspBean;


import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface RetrofitService {
    /**
     * 登录接口
     * @param info
     * @param type
     * @param password
     * @param serial_number
     * @param device_number
     * @return
     */
    @GET("v1.user/login/info/{info}/type/{type}/password/{password}/serial_number/{serial_number}/device_number/{device_number}")
    Observable<LoginRspBean> getUserLogin(@Path("info") String info,
                                          @Path("type") String type,
                                          @Path("password") String password,
                                          @Path("serial_number") String serial_number,
                                          @Path("device_number") String device_number);

    /**
     * 注册接口
     * @param info
     * @param type
     * @param password
     * @param register_time
     * @return
     */
    @GET("v1.user/register/info/{info}/type/{type}/password/{password}/register_time/{register_time}")
    Observable<RegisterRspBean> getUserRegister(@Path("info") String info,
                                                @Path("type") String type,
                                                @Path("password") String password,
                                                @Path("register_time") String register_time);

    /**
     * 发短信接口
     * @param to
     * @return
     */
    @GET("v1.user/sendsms/to/{to}")
    Observable<SendSmsRspBean> sendSms(@Path("to") String to);

    /**
     * 发邮件接口
     * @param to
     * @return
     */
    @GET("v1.user/sendmail/to/{to}")
    Observable<ResponseModel> sendMail(@Path("to") String to);

    /**
     * 用户行为记录
     * @param user_id
     * @param device_id
     * @param app_v
     * @param data_v
     * @param longitude
     * @param latitude
     * @param action
     * @return
     */
    @GET("v1.log/create/user_id/{user_id}/device_id/{device_id}/app_v/{app_v}/data_v/{data_v}/longitude/{longitude}/latitude/{latitude}/log_time/{log_time}/action/{action}")
    Observable<BaseRspBean> create(@Path("user_id") String user_id,
                                   @Path("device_id") String device_id,
                                   @Path("app_v") String app_v,
                                   @Path("data_v") String data_v,
                                   @Path("longitude") String longitude,
                                   @Path("latitude") String latitude,
                                   @Path("action") int action);

















}
