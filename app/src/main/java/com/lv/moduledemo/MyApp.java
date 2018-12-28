package com.lv.moduledemo;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lv.common.base.BaseApplication;
import com.lv.common.utils.DeviceUtils;
import com.lv.common.utils.Utils;
import com.lv.common.webview.SonicRuntimeImpl;
import com.tencent.bugly.crashreport.BuglyLog;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;

public class MyApp extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // dex突破65535的限制
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Utils.isAppDebug()) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
        // step 1: Initialize sonic engine if necessary, or maybe u can do this when application created
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new SonicRuntimeImpl(this), new SonicConfig.Builder().build());
        }
        //初始化bugly
        CrashReport.initCrashReport(getApplicationContext());
        BuglyLog.e("设备名称：-----------", DeviceUtils.getSystemModel());
        BuglyLog.e("设备品牌：-----------", DeviceUtils.getDeviceBrand());
        BuglyLog.e("系统版本：-----------", DeviceUtils.getSystemVersion());
        BuglyLog.e("设备UUID：-----------", DeviceUtils.getUniqueId(this));

    }

}
