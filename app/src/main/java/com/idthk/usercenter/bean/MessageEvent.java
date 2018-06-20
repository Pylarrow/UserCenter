package com.idthk.usercenter.bean;

import com.idthk.usercenter.bean.response.UserInfoRsp;

/**
 * Created by pengyu.
 * 2018/3/15
 */

public class MessageEvent {
    private String message;
    private int userId;
    private String username;
    private String email;
    private String tel;
    private int ageGroup;
    private String sex;
    private String level;
    private int deviceId;
    private int type;//1 登录 2 修改用户名 3 修改性别 4 修改年龄 5 修改手机号 6 修改邮箱 7 修改级别

    private UserInfoRsp userInfoRsp;
    private boolean isLogin;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(int ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public UserInfoRsp getUserInfoRsp() {
        return userInfoRsp;
    }

    public void setUserInfoRsp(UserInfoRsp userInfoRsp) {
        this.userInfoRsp = userInfoRsp;
    }

    public MessageEvent(boolean isLogin,int type){
        this.isLogin=isLogin;
        this.type=type;
    }

    public MessageEvent(String message) {
        this.message = message;
    }

    public MessageEvent(UserInfoRsp userinfo){
        this.userInfoRsp=userinfo;
    }

    public MessageEvent(String message,int type){
        this.message=message;
        this.type=type;
    }

    /**
     *
     * @param userId
     * @param username
     * @param email
     * @param tel
     * @param ageGroup
     * @param sex
     * @param level
     * @param deviceId
     * @param type
     */
    public MessageEvent(int userId,String username,String email,String tel,int ageGroup,String sex,String level,int deviceId,int type){
        this.userId=userId;
        this.username=username;
        this.email=email;
        this.tel=tel;
        this.ageGroup=ageGroup;
        this.sex=sex;
        this.level=level;
        this.deviceId=deviceId;
        this.type=type;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
