package com.lv.common.data;

import android.os.Environment;

import com.yanzhenjie.permission.Permission;

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

    //相册权限
    public static final String[] PHOTO_PERMISSIONS = new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE};

}
