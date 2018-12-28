package com.lv.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;

import com.lv.common.event.MainEvent;

import org.greenrobot.eventbus.EventBus;


/**
 * 页面跳转
 */
public class IntentUtils {

    //获取通讯录回调
    public static final int GET_CONTACT_RESULT = 114;

    /**
     * 跳转到网络设置界面
     */
    public static void redirectToNETWORK(Context context) {
        Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        ((Activity) context).startActivityForResult(intent, 0);
        return;
    }

    /**
     * 跳转到系统联系人界面
     */
    public static void redirectContact(Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(ContactsContract.Contacts.CONTENT_URI);
        ((Activity) context).startActivityForResult(intent, GET_CONTACT_RESULT);
    }

    //通知登录成功
    public static void notifyHasLogin() {
        EventBus.getDefault().post(new MainEvent(0, null));
    }

}
