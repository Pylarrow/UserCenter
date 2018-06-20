package com.idthk.usercenter.bean.response;

import java.io.Serializable;

/**
 * Created by pengyu.
 * 2018/3/19
 */

public class UserInfoRsp implements Serializable {
    private int user_id;
    private String username;
    private String email;
    private String tel;
    private int age_group;
    private String sex;
    private String level;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public int getAge_group() {
        return age_group;
    }

    public void setAge_group(int age_group) {
        this.age_group = age_group;
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
}
