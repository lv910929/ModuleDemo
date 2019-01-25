package com.lv.login.http;


import com.lv.common.entity.HttpResult;
import com.lv.common.entity.UserEntity;
import com.lv.login.entity.LoginEntity;
import com.lv.login.request.LoginBody;
import com.lv.login.request.SmsCodeBody;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 接口调用的工具类
 */
public interface LoginAPIService {

    //发送短信验证码
    @POST(LoginConstant.SMS_CODE_URL)
    Observable<HttpResult> sendSmsCode(@Body SmsCodeBody smsCodeBody);

    //登录
    @POST(LoginConstant.LOGIN_URL)
    Observable<HttpResult<UserEntity>> loginReq(@Body LoginBody loginBody);

}
