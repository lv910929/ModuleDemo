package com.lv.login.entity;

import java.io.Serializable;

public class LoginEntity implements Serializable{

    private static final long serialVersionUID = -2353001166988116040L;

    private String tokenTemp;

    private String tokenFixed;

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

}
