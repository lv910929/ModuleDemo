package com.lv.common.entity;

import java.io.Serializable;

/**
 * Created by Lv on 2016/4/28.
 */
public class HttpResult<T> implements Serializable {

    private int status;

    private String message;

    private T data;

    public HttpResult() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
