package com.idthk.usercenter.bean.response;

/**
 * Created by pengyu.
 * 2018/2/1
 * 登录成功返回实体bean
 */

public class LoginRspBean{

    private int user_id;
    private int device_id;
    private int age_group;
    private String level;
    private String username;
    private String email;
    private String tel;
    private String sex;

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    public int getAge_group() {
        return age_group;
    }

    public void setAge_group(int age_group) {
        this.age_group = age_group;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
