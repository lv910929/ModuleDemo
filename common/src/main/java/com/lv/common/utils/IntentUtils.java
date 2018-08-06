package com.lv.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.lv.common.R;
import com.lv.common.ui.WebViewActivity;

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

    //跳转到WebView
    public static void redirectWebView(Context context, String loadUrl, String title) {
        Intent intent = new Intent(context, WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("loadUrl", loadUrl);
        bundle.putString("title", title);
        intent.putExtras(bundle);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }


}
