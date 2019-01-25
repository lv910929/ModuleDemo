package com.lv.user.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.allen.library.SuperTextView;
import com.lv.common.base.BasePresenter;
import com.lv.common.base.SwipeBackMvpActivity;
import com.lv.common.data.CommonPath;
import com.lv.common.event.MainEvent;
import com.lv.common.event.UserEvent;
import com.lv.common.permission.RuntimeRationale;
import com.lv.common.utils.BitmapHelper;
import com.lv.common.utils.BitmapUtils;
import com.lv.common.utils.DialogUtils;
import com.lv.common.utils.MyToast;
import com.lv.common.utils.PhotoSelectUtils;
import com.lv.user.R;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

@Route(path = CommonPath.USER_ACTIVITY_PATH)
public class UserActivity extends SwipeBackMvpActivity<BasePresenter> implements View.OnClickListener {

    private SuperTextView superChangeAvatar;
    private SuperTextView superChangeNickname;
    private SuperTextView superChangeGender;
    private SuperTextView superChangeBirthday;
    private SuperTextView superChangeSignature;
    private SuperTextView superChangePhone;
    private SuperTextView superChangeEmail;

    private PhotoSelectUtils photoSelectUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_user);
        initUI();
        initPhotoSelectUtils();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void initPhotoSelectUtils() {
        // PhotoSelectUtils（一个Activity对应一个PhotoSelectUtils）
        photoSelectUtils = new PhotoSelectUtils(this, new PhotoSelectUtils.PhotoSelectListener() {

            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                // 当拍照或从图库选取图片成功后回调
                String fileName = outputFile.getName();
                String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
                //压缩图片
                Bitmap imageBitmap = BitmapHelper.revisionImageSize(outputFile);
                byte[] imageBytes = BitmapUtils.bmpToByteArray(imageBitmap, true);
            }
        }, true);//true裁剪，false不裁剪
    }

    @Override
    protected void initUI() {
        super.initUI();

        superChangeAvatar = (SuperTextView) findViewById(R.id.super_change_avatar);
        superChangeNickname = (SuperTextView) findViewById(R.id.super_change_nickname);
        superChangeGender = (SuperTextView) findViewById(R.id.super_change_gender);
        superChangeBirthday = (SuperTextView) findViewById(R.id.super_change_birthday);
        superChangeSignature = (SuperTextView) findViewById(R.id.super_change_signature);
        superChangePhone = (SuperTextView) findViewById(R.id.super_change_phone);
        superChangeEmail = (SuperTextView) findViewById(R.id.super_change_email);

        initToolBar("个人资料", true);

        superChangeAvatar.setOnClickListener(this);
        superChangeNickname.setOnClickListener(this);
        superChangeGender.setOnClickListener(this);
        superChangeBirthday.setOnClickListener(this);
        superChangeSignature.setOnClickListener(this);
        superChangePhone.setOnClickListener(this);
        superChangeEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.super_change_avatar) {
            requestPermission(Permission.Group.CAMERA);
        } else if (id == R.id.super_change_nickname) {
            showNickNameDialog();
        } else if (id == R.id.super_change_gender) {

        } else if (id == R.id.super_change_birthday) {

        } else if (id == R.id.super_change_signature) {

        } else if (id == R.id.super_change_phone) {

        } else if (id == R.id.super_change_email) {

        }
    }

    /**
     * 更改昵称对话框
     */
    private void showNickNameDialog(){

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(UserEvent event) {
        switch (event.getWhat()) {
            case 1://拍照
                photoSelectUtils.takePhoto();
                break;
            case 2://相册
                photoSelectUtils.selectPhoto();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 在Activity中的onActivityResult()方法里与LQRPhotoSelectUtils关联
        photoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
    }

    /**
     * Request permissions.
     */
    private void requestPermission(String... permissions) {
        AndPermission.with(this)
                .runtime()
                .permission(permissions)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        //获取权限成功
                        DialogUtils.showPhotoDialog(UserActivity.this);
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        //获取权限失败
                        if (AndPermission.hasAlwaysDeniedPermission(UserActivity.this, permissions)) {
                            DialogUtils.showSettingDialog(UserActivity.this, UserActivity.this.getResources().getString(R.string.camera_permission_string));
                        }
                    }
                })
                .start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
