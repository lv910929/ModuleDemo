package com.lv.common.http;

import android.content.Context;

import com.lv.common.base.BaseApplication;
import com.lv.common.utils.AppUtil;
import com.lv.common.utils.DeviceUtils;
import com.lv.common.utils.NetUtils;
import com.lv.common.utils.PrefUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;

public class HttpConfig {

    private final static String DEVICE_PLATFORM = "2";

    //设置OkHttp超时时间
    public static OkHttpClient initOkHttp(Context context) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        String tokenNo = PrefUtils.getString(context, "tokenNo", "");
                        //建立拦截器
                        Request original = chain.request();
                        original.newBuilder()
                                .addHeader("Content-Type", "application/json;charset=UTF-8")
                                .addHeader("DevicePlatform", DEVICE_PLATFORM)
                                .addHeader("DeviceToken", DeviceUtils.getUniqueId(context))
                                .addHeader("DeviceSystem", DeviceUtils.getDeviceBrand())
                                .addHeader("DeviceSystemver", DeviceUtils.getSystemVersion())
                                .addHeader("DeviceModel", DeviceUtils.getSystemModel())
                                .addHeader("DeviceNetwork", NetUtils.getNetworkType(context))
                                .addHeader("AppVersion", AppUtil.getVersionName(context))
                                .addHeader("Authorization", tokenNo)
                                .post(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), bodyToString(original.body())))//关键部分，设置requestBody的编码格式为json
                                .build();
                        return chain.proceed(original);
                    }
                })
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .sslSocketFactory(BaseApplication.sslParams.sSLSocketFactory, BaseApplication.sslParams.trustManager)
                .build();
        return client;
    }

    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

}
