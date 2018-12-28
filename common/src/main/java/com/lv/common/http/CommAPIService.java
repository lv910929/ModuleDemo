package com.lv.common.http;


import com.lv.common.entity.HttpResult;
import com.lv.common.entity.TokenBody;
import com.lv.common.entity.TokenEntity;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 接口调用的工具类
 */
public interface CommAPIService {

    //获取TOKEN
    @POST(CommConstant.GET_TOKEN_URL)
    Observable<HttpResult<TokenEntity>> getTokenReq(@Body TokenBody tokenBody);

}
