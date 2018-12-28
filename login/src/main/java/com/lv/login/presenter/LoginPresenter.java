package com.lv.login.presenter;

import android.content.Context;

import com.lv.common.base.BasePresenter;
import com.lv.common.entity.HttpResult;
import com.lv.common.http.ApiCallback;
import com.lv.common.http.HttpConstant;
import com.lv.common.utils.MyToast;
import com.lv.login.entity.LoginEntity;
import com.lv.login.http.LoginBuildApi;
import com.lv.login.request.LoginBody;
import com.lv.login.request.SmsCodeBody;
import com.lv.login.view.LoginView;

import rx.Observable;

/**
 * 登录Presenter
 */
public class LoginPresenter extends BasePresenter<LoginView> implements BasePresenter.GetTokenCallBack {

    public LoginPresenter(Context context, LoginView view) {
        this.context = context;
        attachView(view);
    }

    /**
     * 发送短信验证码请求
     *
     * @param phone
     */
    public void getSmsCodeReq(String phone) {
        Observable<HttpResult> smsCodeObservable = LoginBuildApi.getAPIService(context).sendSmsCode(new SmsCodeBody(phone));
        addSubscription(smsCodeObservable, new ApiCallback<HttpResult>() {

            @Override
            public void onSuccess(HttpResult model) {
                mvpView.hideLoad();
                if (model.getStatus() == HttpConstant.SUCCESS_STATE_CODE) { //说明成功
                    MyToast.showShortToast(context, "短信已发送");
                    mvpView.getCodeSuccess();
                } else {
                    MyToast.showShortToast(context, model.getMessage());
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mvpView.hideLoad();
                MyToast.showShortToast(context, msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * 登录请求
     *
     * @param phone
     * @param smsCode
     */
    public void loginReq(String phone, String smsCode) {
        Observable<HttpResult<LoginEntity>> loginObservable = LoginBuildApi.getAPIService(context).loginReq(new LoginBody(phone, smsCode));
        addSubscription(loginObservable, new ApiCallback<HttpResult<LoginEntity>>() {

            @Override
            public void onSuccess(HttpResult<LoginEntity> model) {
                mvpView.hideLoad();
                if (model.getStatus() == HttpConstant.SUCCESS_STATE_CODE) { //说明成功
                    MyToast.showShortToast(context, "登录成功");
                    mvpView.loginSuccess(model.getData().getTokenTemp(), model.getData().getTokenFixed());
                } else {
                    MyToast.showShortToast(context, model.getMessage());
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mvpView.hideLoad();
                MyToast.showShortToast(context, msg);
            }

            @Override
            public void onFinish() {

            }
        });

    }

    @Override
    public void getTokenSuccess() {

    }

}
