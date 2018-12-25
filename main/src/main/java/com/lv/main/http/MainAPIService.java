package com.lv.main.http;


import com.lv.common.entity.HttpResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 接口调用的工具类
 */
public interface MainAPIService {

    //检测版本
    @GET(MainConstant.CHECK_VERSION_URL)
    Observable<HttpResult> checkVersion(@Query("token") String token, @Query("apkType") int apkType , @Query("appType") int appType);


}
