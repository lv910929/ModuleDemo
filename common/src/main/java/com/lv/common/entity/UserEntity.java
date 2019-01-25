package com.lv.common.entity;

import java.io.Serializable;

public class UserEntity implements Serializable{

    private static final long serialVersionUID = 9155326784037152567L;

    private String uid;

    private String username;

    private String nickname;

    private String avatar;

    private String gender;

    private String mobile;

    private String email;

    private String tokenTemp;

    private String tokenFixed;

    private String birthday;

    private String signature;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTokenTemp() {
        return tokenTemp;
    }

    public void setTokenTemp(String tokenTemp) {
        this.tokenTemp = tokenTemp;
    }

    public String getTokenFixed() {
        return tokenFixed;
    }

    public void setTokenFixed(String tokenFixed) {
        this.tokenFixed = tokenFixed;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
