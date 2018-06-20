package com.idthk.usercenter;

import com.idthk.network.ResultData;
import com.idthk.usercenter.bean.response.DeviceInfoBean;
import com.idthk.usercenter.bean.response.LoginRspBean;
import com.idthk.usercenter.bean.response.RegisterRspBean;
import com.idthk.usercenter.bean.response.RenewBean;
import com.idthk.usercenter.bean.response.SendSmsRspBean;
import com.idthk.usercenter.bean.response.TestRspBean;
import com.idthk.usercenter.bean.response.UserInfoRsp;

import butterknife.BindView;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by pengyu.
 * 2018/2/8
 */

public interface CloudApi {

    /**
     * 发短信
     * @param to
     * @return
     */
    @GET("v1.user/sendsms/to/{to}")
    Observable<ResultData<SendSmsRspBean>> sendSms(@Path("to") String to);

    @GET("v1/user/login")
    Observable<ResultData<TestRspBean>> test(@Query("logname") String logname,
                                             @Query("logpass") String logpass,
                                             @Query("lng") String lng,
                                             @Query("lat") String lat);

    /**
     * 注册接口
     * @param info
     * @param type
     * @param password
     * @param register_time
     * @return
     */
    @GET("v1.user/register/info/{info}/type/{type}/password/{password}/register_time/{register_time}/age_group/{age_group}")
    Observable<ResultData<Object>> getUserRegister(@Path("info") String info,
                                                   @Path("type") String type,
                                                   @Path("password") String password,
                                                   @Path("register_time") String register_time,
                                                   @Path("age_group") int age_group);


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
    Observable<ResultData<LoginRspBean>> getUserLogin(@Path("info") String info,
                                          @Path("type") String type,
                                          @Path("password") String password,
                                          @Path("serial_number") String serial_number,
                                          @Path("device_number") String device_number);


    /**
     * 编辑信息接口
     * @param user_id
     * @param type
     * @param info
     * @return
     */
    @GET("v1.user/update/user_id/{user_id}/type/{type}/info/{info}")
    Observable<ResultData<Object>> getUserUpdate(@Path("user_id") int user_id,
                                                 @Path("type") String type,
                                                 @Path("info") String info);

    /**
     * 获取用户信息
     * @param user_id
     * @return
     */
    @GET("v1.user/read/user_id/{user_id}")
    Observable<ResultData<UserInfoRsp>> getUserInfo(@Path("user_id") int user_id);

    /**
     * 发送邮件
     * @param to
     * @return
     */
    @GET("v1.user/sendmail/to/{to}")
    Observable<ResultData<SendSmsRspBean>> sendEmail(@Path("to") String to);

    /**
     * 重置密码
     * @param type
     * @param info
     * @param password
     * @return
     */
    @GET("v1.user/resetPassword/type/{type}/info/{info}/password/{password}")
    Observable<ResultData<Object>> resetPwd(@Path("type") String type,
                                            @Path("info") String info,
                                            @Path("password") String password);

    /**
     * 家长验证
     * @param type
     * @param info
     * @param password
     * @return
     */
    @GET("v1.user/validate/type/{type}/info/{info}/password/{password}")
    Observable<ResultData<Object>> validate(@Path("type") String type,
                                            @Path("info") String info,
                                            @Path("password") String password);

    /**
     * 获取设备信息
     * @param device_id
     * @return
     */
    @GET("v1.device/info/device_id/{device_id}")
    Observable<ResultData<DeviceInfoBean>> getDeviceInfo(@Path("device_id") int device_id);

    /**
     * 续费
     * @param type
     * @return
     */
    @GET("v1.device/renewPrice/type/{type}")
    Observable<ResultData<RenewBean>> renewPrice(@Path("type") String type);

    /**
     * 重置密码前验证
     * @param type
     * @param info
     * @return
     */
    @GET("v1.user/validateBeforeResetPassword/type/{type}/info/{info}")
    Observable<ResultData<Object>> validateBeforeResetPassword(@Path("type") String type,
                                                               @Path("info") String info);

}
