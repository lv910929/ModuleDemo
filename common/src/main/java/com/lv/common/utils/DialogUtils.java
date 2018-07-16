package com.lv.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;


/**
 * Created by Lv on 2016/4/14.
 */
public class DialogUtils {

    public static final int REQUEST_CAMERA = 110;
    public static final int REQUEST_CHOOSE = 120;

    public static void showInfoDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setTitle(title)
                .setCancelable(true)
                .setPositiveButton("确定", null)
                .show();
    }

    public static void showActionDialog(Context context, String title, String message, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("确定", onClickListener)
                .setNegativeButton("取消", null)
                .show();
    }

    //没有网络对话框
    public static void showNoNetDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("没有可用的网络").setMessage("是否对网络进行设置?");
        builder.setPositiveButton("是",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        IntentUtils.redirectToNETWORK(context);
                    }
                })
                .setNegativeButton("否",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                                ((Activity) context).finish();
                            }
                        }).show();
    }

    /**
     * 跳转到设置对话框
     */
    public static void showSettingDialog(final Activity activity) {
        //创建对话框创建器
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //设置标题
        builder.setTitle("权限申请");
        //设置正文
        builder.setMessage("在设置-应用-品在家-权限 中开启相机、存储权限，才能正常使用");

        //添加确定按钮点击事件
        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {//点击完确定后，触发这个事件

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //这里用来跳到手机设置页，方便用户开启权限
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            }
        });
        //添加取消按钮点击事件
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        //使用构建器创建出对话框对象
        AlertDialog dialog = builder.create();
        dialog.show();//显示对话框
    }

}
