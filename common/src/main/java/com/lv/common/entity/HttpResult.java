package com.lv.common.entity;

import java.io.Serializable;

/**
 * Created by Lv on 2016/4/28.
 */
public class HttpResult<T> implements Serializable {

    private boolean error;

    private String errorMessage;

    private T data;

    public HttpResult() {
    }

    public HttpResult(boolean error) {
        this.error = error;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
