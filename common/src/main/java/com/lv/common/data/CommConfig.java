package com.lv.common.data;

import android.os.Environment;

import java.util.Map;

/**
 * 通用配置类
 */
public class CommConfig {

    // 用于存放倒计时时间
    public static Map<String, Long> validateTimeMap;

    //数据库名称
    public static final String DB_NAME = "Module.db";

    public static final String SDCARD_ROOT_PATH = Environment.getExternalStorageDirectory().getPath();
    //相册名
    public final static String ALBUM_PATH = SDCARD_ROOT_PATH + "/Module_Pic/";

    public static final String SAVE_PATH_IN_SDCARD = "/Module/download/";

    public static final String SAVE_IMAGE_PATH = "/Module/Images/";

    public static final String DOWNLOAD_APK_NAME = "Module.apk";

    //用于权限设置回调
    public static final int REQUEST_CODE_SETTING = 300;
    //写文件权限
    public static final int REQUEST_WRITE_PERMISSIONS = 110;
    //用于摄像头权限
    public static final int REQUEST_QR_CODE_PERMISSIONS = 111;
    //用于定位权限
    public static final int REQUEST_LOCATION_PERMISSIONS = 112;
    //用于获取通讯录权限
    public static final int REQUEST_CONTACT_PERMISSIONS = 113;
    //获取通讯录回调
    public static final int GET_CONTACT_RESULT = 114;

}
