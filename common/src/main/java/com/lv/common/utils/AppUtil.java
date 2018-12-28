package com.lv.common.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Lv on 2016/6/1.
 */
public class AppUtil {

    public static final String AMAP_PACKAGE_NAME = "com.autonavi.minimap";

    public static final String BAIDU_MAP_PACKAGE_NAME = "com.baidu.BaiduMap";

    public static final String TENCENT_MAP_PACKAGE_NAME = "com.tencent.map";

    public static final String GOOGLE_MAP_PACKAGE_NAME = "com.google.android.apps.maps";

    public static final String MEIZU_MAP_PACKAGE_NAME = "com.meizu.net.map";

    //比对版本号
    public static boolean hasNewVersion(Context context, int versionCode) {
        boolean result = false;
        if (versionCode > getVersionCode(context)) {//说明有新版本
            result = true;
        }
        return result;
    }

    //版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }

    // 取得联系人名字
    public static String getContactName(Cursor cursor) {
        String contactName = "";
        int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        contactName = cursor.getString(nameFieldColumnIndex);
        return contactName;
    }

    public static String getContactPhone(Context context, Cursor cursor) {
        int phoneColumn = cursor
                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        int phoneNum = cursor.getInt(phoneColumn);
        String result = "";
        if (phoneNum > 0) {
            // 获得联系人的ID号
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String contactId = cursor.getString(idColumn);
            // 获得联系人电话的cursor
            Cursor phone = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
                            + contactId, null, null);
            if (phone.moveToFirst()) {
                for (; !phone.isAfterLast(); phone.moveToNext()) {
                    int index = phone
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int typeindex = phone
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                    int phone_type = phone.getInt(typeindex);
                    String phoneNumber = phone.getString(index);
                    result = phoneNumber;
                    switch (phone_type) {//此处请看下方注释
                        case 2:
                            result = phoneNumber;
                            break;
                    }
                }
                if (!phone.isClosed()) {
                    phone.close();
                }
            }
        }
        return result;
    }

    //跳转到拨打电话
    public static void intentPhoneCall(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //跳转到发送邮件
    public static void intentEmail(Context context, String email) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //打开手机中的地图应用
    public static void openNavigation(Context context, String latitude, String longitude, String addr) {
        StringBuffer sb = new StringBuffer();
        sb.append("geo:").append(latitude).append(",").append(longitude)
                .append("?").append("z=").append("18").append("?").append("q=")
                .append(addr);
        if (isAppInstalled(context, AMAP_PACKAGE_NAME)
                || isAppInstalled(context, BAIDU_MAP_PACKAGE_NAME)
                || isAppInstalled(context, TENCENT_MAP_PACKAGE_NAME)
                || isAppInstalled(context, GOOGLE_MAP_PACKAGE_NAME)
                || isAppInstalled(context, MEIZU_MAP_PACKAGE_NAME)) {
            Uri mUri = Uri.parse(sb.toString());
            Intent mIntent = new Intent(Intent.ACTION_VIEW, mUri);
            ((Activity) context).startActivity(mIntent);
        } else {
            MyToast.showShortToast(context, "请先安装地图应用");
        }
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 检查摄像头是否可用
     *
     * @return
     */
    public static boolean isCameraUsable() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            // setParameters 是针对魅族MX5。MX5通过Camera.open()拿到的Camera对象不为null
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            canUse = false;
        }
        if (mCamera != null) {
            mCamera.release();
        }
        return canUse;
    }

    /**
     * 判断是否在栈顶
     *
     * @param mContext
     * @return
     */
    public static boolean getTopApp(Context mContext, String activityName) {
        String lockAppName = activityName;
        String topActivityName = "";
        ActivityManager am = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = am
                .getRunningTasks(1);
        if (runningTasks != null && !runningTasks.isEmpty()) {
            ActivityManager.RunningTaskInfo taskInfo = runningTasks.get(0);
            topActivityName = taskInfo.topActivity.getClassName();
        }
        if (lockAppName.equals(topActivityName)) {
            return true;
        }
        return false;
    }


}
