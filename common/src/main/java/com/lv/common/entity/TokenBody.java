package com.lv.common.entity;

import java.io.Serializable;

public class TokenBody implements Serializable{

    private static final long serialVersionUID = -732134969232430282L;

    private String tokenFixed;

    public TokenBody(String tokenFixed) {
        this.tokenFixed = tokenFixed;
    }

    public String getTokenFixed() {
        return tokenFixed;
    }

    public void setTokenFixed(String tokenFixed) {
        this.tokenFixed = tokenFixed;
    }
}
