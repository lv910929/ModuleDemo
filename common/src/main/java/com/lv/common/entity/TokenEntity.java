package com.lv.common.entity;

import java.io.Serializable;

public class TokenEntity implements Serializable{

    private static final long serialVersionUID = 6043243299214757814L;

    private String tokenTemp;

    public String getTokenTemp() {
        return tokenTemp;
    }

    public void setTokenTemp(String tokenTemp) {
        this.tokenTemp = tokenTemp;
    }
}
