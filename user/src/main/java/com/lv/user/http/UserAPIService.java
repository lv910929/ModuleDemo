package com.lv.user.http;


import com.lv.common.entity.HttpResult;
import com.lv.user.request.UserBody;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 接口调用的工具类
 */
public interface UserAPIService {

    //发送短信验证码
    @POST(UserConstant.EDIT_USER_URL)
    Observable<HttpResult> editUser(@Body UserBody userBody);

}
