package com.lv.main.http;

import com.lv.common.base.BaseApplication;
import com.lv.common.http.CommConstant;
import com.lv.common.utils.MD5;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 获取网络框架类
 */
public class MainBuildApi {

    //一个API_KEY对应一个SECRET 由接口开发提供
    private final static String API_KEY = "API_KEY_ANDROID_SHOUQU";
    private final static String SECRET = "API_SECRETKEY_ANDROID_SHOUQU";
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

    private static Retrofit retrofit;

    public static MainAPIService getAPIService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(CommConstant.BASE_URL) //设置Base的访问路径
                    .addConverterFactory(GsonConverterFactory.create()) //设置默认的解析库：Gson
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(initOkHttp())
                    .build();
        }
        return retrofit.create(MainAPIService.class);
    }

    //设置OkHttp超时时间
    private static OkHttpClient initOkHttp() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //生成timeStamp和signature
                        Date currentTime = new Date();
                        String timeStamp = formatter.format(currentTime);
                        String signature = MD5Signature(timeStamp);
                        //建立拦截器
                        Request original = chain.request();
                        HttpUrl originalHttpUrl = original.url();
                        HttpUrl url = originalHttpUrl.newBuilder()
                                .addQueryParameter("apiKey", API_KEY)
                                .addQueryParameter("signature", signature)
                                .addQueryParameter("timeStamp", timeStamp)
                                .build();
                        Request.Builder requestBuilder = original.newBuilder().url(url);
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .sslSocketFactory(BaseApplication.sslParams.sSLSocketFactory, BaseApplication.sslParams.trustManager)
                .build();
        return client;
    }

    //加密生成signature
    private static String MD5Signature(String timeStamp) {
        String signature = MD5.encodeWithMD5(API_KEY + SECRET + timeStamp);
        return signature;
    }

}
