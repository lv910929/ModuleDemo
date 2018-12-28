package com.lv.login.request;

import java.io.Serializable;

/**
 * 发送短信验证码请求体
 */
public class SmsCodeBody implements Serializable {

    private static final long serialVersionUID = 3336587301660965689L;

    private String phone;

    public SmsCodeBody(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}

