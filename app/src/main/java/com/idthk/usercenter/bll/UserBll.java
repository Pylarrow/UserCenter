package com.idthk.usercenter.bll;

import android.content.Context;


import com.idthk.network.http.RxSubscribe;
import com.idthk.network.http.RxUtil;
import com.idthk.usercenter.bean.response.DeviceInfoBean;
import com.idthk.usercenter.bean.response.LoginRspBean;
import com.idthk.usercenter.bean.response.RenewBean;
import com.idthk.usercenter.bean.response.SendSmsRspBean;
import com.idthk.usercenter.bean.response.UserInfoRsp;

import rx.functions.Action1;
import rx.functions.Action2;

/**
 * Created by pengyu.
 * 2018/2/8
 */

public class UserBll extends BaseBll{

    /**
     * 发短信
     * @param context
     * @param mobile
     * @param success
     * @param error
     */
    public void sendSms(Context context, String mobile, final Action1 success, final Action2<Integer, String> error) {
        cloudApi.sendSms(mobile).compose(RxUtil.<SendSmsRspBean>transResult()).subscribe(new RxSubscribe<SendSmsRspBean>(context) {

            @Override
            protected void onSuccess(SendSmsRspBean sendSmsRspBean) {
                success.call(sendSmsRspBean);
            }

            @Override
            protected void onError(int code, String message) {
                error.call(code,message);
            }
        });
    }

    /**
     * 注册
     * @param context
     * @param info
     * @param type
     * @param password
     * @param register_time
     * @param success
     * @param error
     */
    public void register(Context context,String info,String type,String password,String register_time,int age_group,
                         final Action1 success,final Action2<Integer,String> error){
        cloudApi.getUserRegister(info,type,password,register_time,age_group).compose(RxUtil.<Object>transResult()).subscribe(new RxSubscribe<Object>(context) {
            @Override
            protected void onSuccess(Object o) {
                success.call(o);
            }

            @Override
            protected void onError(int code, String message) {
                error.call(code,message);
            }
        });
    }

    /**
     * 登录
     * @param context
     * @param info
     * @param type
     * @param password
     * @param serial_number
     * @param device_number
     * @param success
     * @param error
     */
    public void login(Context context,String info,String type,String password,String serial_number,String device_number,
                      final Action1 success,final Action2<Integer,String> error){
        cloudApi.getUserLogin(info,type,password,serial_number,device_number).compose(RxUtil.<LoginRspBean>transResult()).subscribe(new RxSubscribe<LoginRspBean>() {
            @Override
            protected void onSuccess(LoginRspBean loginRspBean) {
                success.call(loginRspBean);
            }

            @Override
            protected void onError(int code, String message) {
                error.call(code,message);
            }
        });
    }

    /**
     * 用户信息编辑
     * @param context
     * @param userId
     * @param type
     * @param info
     * @param success
     * @param error
     */
    public void updateUserInfo(Context context,int userId,String type,String info,
                               final  Action1 success,final Action2<Integer,String> error){
        cloudApi.getUserUpdate(userId,type,info).compose(RxUtil.<Object>transResult()).subscribe(new RxSubscribe<Object>(context) {
            @Override
            protected void onSuccess(Object o) {
                success.call(o);
            }

            @Override
            protected void onError(int code, String message) {
                error.call(code,message);
            }
        });
    }


    /**
     * 获取用户信息接口
     * @param context
     * @param userId
     * @param success
     * @param error
     */
    public void getUserInfo(Context context,int userId,final Action1 success,final Action2<Integer,String> error){
        cloudApi.getUserInfo(userId).compose(RxUtil.<UserInfoRsp>transResult()).subscribe(new RxSubscribe<UserInfoRsp>() {
            @Override
            protected void onSuccess(UserInfoRsp userInfoRsp) {
                success.call(userInfoRsp);
            }

            @Override
            protected void onError(int code, String message) {
                error.call(code,message);
            }
        });
    }

    /**
     * 发送邮件
     * @param context
     * @param to
     * @param success
     * @param error
     */
    public void sendEmail(Context context,String to,final Action1 success,final Action2<Integer,String> error){
        cloudApi.sendEmail(to).compose(RxUtil.<SendSmsRspBean>transResult()).subscribe(new RxSubscribe<SendSmsRspBean>(context) {
            @Override
            protected void onSuccess(SendSmsRspBean sendSmsRspBean) {
                success.call(sendSmsRspBean);
            }

            @Override
            protected void onError(int code, String message) {
                error.call(code,message);
            }
        });
    }

    /**
     * 重置密码
     * @param context
     * @param type
     * @param info
     * @param password
     * @param success
     * @param error
     */
    public void resetPwd(Context context,String type,String info,String password,final Action1 success,final Action2<Integer,String> error){
        cloudApi.resetPwd(type,info,password).compose(RxUtil.<Object>transResult()).subscribe(new RxSubscribe<Object>() {
            @Override
            protected void onSuccess(Object o) {
                success.call(o);
            }

            @Override
            protected void onError(int code, String message) {
                error.call(code,message);
            }
        });
    }

    /**
     * 家长验证
     * @param context
     * @param type
     * @param info
     * @param password
     * @param success
     * @param error
     */
    public void validate(Context context,String type,String info,String password,final Action1 success,final Action2<Integer,String> error){
        cloudApi.validate(type,info,password).compose(RxUtil.<Object>transResult()).subscribe(new RxSubscribe<Object>(context) {
            @Override
            protected void onSuccess(Object o) {
                success.call(o);
            }

            @Override
            protected void onError(int code, String message) {
                error.call(code,message);
            }
        });
    }

    /**
     * 获取设备信息
     * @param context
     * @param device_id
     * @param success
     * @param error
     */
    public void getDeviceInfo(Context context,int device_id,final Action1 success,final Action2<Integer,String> error){
        cloudApi.getDeviceInfo(device_id).compose(RxUtil.<DeviceInfoBean>transResult()).subscribe(new RxSubscribe<DeviceInfoBean>() {
            @Override
            protected void onSuccess(DeviceInfoBean deviceInfoBean) {
                success.call(deviceInfoBean);
            }

            @Override
            protected void onError(int code, String message) {
                error.call(code,message);
            }
        });
    }

    /**
     * 续费
     * @param context
     * @param type
     * @param success
     * @param error
     */
    public void renewPrice(Context context,String type,final Action1 success,final Action2<Integer,String> error){
        cloudApi.renewPrice(type).compose(RxUtil.<RenewBean>transResult()).subscribe(new RxSubscribe<RenewBean>() {
            @Override
            protected void onSuccess(RenewBean renewBean) {
                success.call(renewBean);
            }

            @Override
            protected void onError(int code, String message) {
                error.call(code,message);
            }
        });
    }

    /**
     * 重置密码前验证用户信息
     * @param context
     * @param type
     * @param info
     * @param sucess
     * @param error
     */
    public void validateBeforeResetPassword(Context context,String type,String info,final Action1 sucess,final Action2<Integer,String> error){
        cloudApi.validateBeforeResetPassword(type,info).compose(RxUtil.<Object>transResult()).subscribe(new RxSubscribe<Object>() {
            @Override
            protected void onSuccess(Object o) {
                sucess.call(o);
            }

            @Override
            protected void onError(int code, String message) {
                error.call(code,message);
            }
        });
    }
}
