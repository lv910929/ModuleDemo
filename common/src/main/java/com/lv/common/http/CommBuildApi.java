package com.lv.common.http;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 获取网络框架类
 */
public class CommBuildApi {

    private static Retrofit retrofit;

    public static CommAPIService getAPIService(Context context) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(CommConstant.DEBUG_URL) //设置Base的访问路径
                    .addConverterFactory(GsonConverterFactory.create()) //设置默认的解析库：Gson
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(HttpConfig.initOkHttp(context.getApplicationContext()))
                    .build();
        }
        return retrofit.create(CommAPIService.class);
    }

}
