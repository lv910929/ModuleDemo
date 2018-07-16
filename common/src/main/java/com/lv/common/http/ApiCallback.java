package com.lv.common.http;


import rx.Subscriber;

public abstract class ApiCallback<M> extends Subscriber<M> {

    public abstract void onSuccess(M model);

    //失败的回调
    public abstract void onFailure(int code, String msg);

    public abstract void onFinish();

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e.toString().contains("Socket closed")) {
            onFailure(HttpConstant.FAIL_CODE_1003, "手动关闭请求");
        } else {
            if (e instanceof HttpException) {
                HttpException httpException = (HttpException) e;
                int code = httpException.code();
                String msg = httpException.getMessage();
                if (code == 504) {
                    msg = "网络不给力";
                } else if (code == 500 || code == 502 || code == 404) {
                    msg = "服务器异常，请稍后再试";
                }else {
                    msg = "服务器异常，请稍后再试";
                }
                onFailure(code, msg);
            } else {
                onFailure(HttpConstant.FAIL_CODE_1004, "服务器异常，请稍后再试");
            }
        }
        onFinish();
    }

    @Override
    public void onNext(M model) {
        if (model != null)
            onSuccess(model);
        else
            onFailure(HttpConstant.FAIL_CODE_1001, "服务器异常，请稍后再试");
    }

    @Override
    public void onCompleted() {
        onFinish();
    }

}
