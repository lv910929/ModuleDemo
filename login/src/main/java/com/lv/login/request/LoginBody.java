package com.lv.login.request;

import java.io.Serializable;

public class LoginBody implements Serializable{

    private static final long serialVersionUID = 4928923981869494706L;

    private String phone;

    private String msgAuthCode;

    public LoginBody(String phone, String msgAuthCode) {
        this.phone = phone;
        this.msgAuthCode = msgAuthCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMsgAuthCode() {
        return msgAuthCode;
    }

    public void setMsgAuthCode(String msgAuthCode) {
        this.msgAuthCode = msgAuthCode;
    }

}
