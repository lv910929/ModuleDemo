package com.lv.main.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.allen.library.SuperTextView;
import com.lv.common.base.BaseLoadMvpActivity;
import com.lv.common.base.BasePresenter;
import com.lv.common.data.CommConfig;
import com.lv.common.data.CommonPath;
import com.lv.common.permission.RuntimeRationale;
import com.lv.common.utils.DataCleanManager;
import com.lv.common.utils.DialogUtils;
import com.lv.common.utils.IntentUtils;
import com.lv.common.utils.MyToast;
import com.lv.main.R;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;

@Route(path = CommonPath.SET_ACTIVITY_PATH)
public class SettingActivity extends BaseLoadMvpActivity<BasePresenter> implements View.OnClickListener {

    private SuperTextView superTextUpdate;
    private SuperTextView superTextCache;
    private SuperTextView superTextShare;
    private Button btnExitLogin;

    private String cacheSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initData();
        initUI();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        try {
            cacheSize = DataCleanManager.getTotalCacheSize(SettingActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
            cacheSize = "0KB";
        }
    }

    @Override
    protected void initUI() {

        superTextUpdate = (SuperTextView) findViewById(R.id.super_text_update);
        superTextCache = (SuperTextView) findViewById(R.id.super_text_cache);
        superTextShare = (SuperTextView) findViewById(R.id.super_text_share);
        btnExitLogin = (Button) findViewById(R.id.btn_exit_login);

        initToolBar("设置", true);
        if (hasLogin()) {
            btnExitLogin.setVisibility(View.VISIBLE);
        } else {
            btnExitLogin.setVisibility(View.GONE);
        }
        superTextCache.setRightString(cacheSize);
        superTextUpdate.setOnClickListener(this);
        superTextCache.setOnClickListener(this);
        superTextShare.setOnClickListener(this);
        btnExitLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_exit_login) {
            setHasLogin(false);
            IntentUtils.notifyHasLogin();
            finish();
        } else if (id == R.id.super_text_update) {
            requestPermission(CommConfig.PHOTO_PERMISSIONS);
        } else if (id == R.id.super_text_cache) {

        } else if (id == R.id.super_text_share) {

        }
    }

    //---------------------- 权限 ------------------------//

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
                        MyToast.showShortToast(SettingActivity.this, "获取权限成功");
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        //获取权限失败
                        if (AndPermission.hasAlwaysDeniedPermission(SettingActivity.this, permissions)) {
                            DialogUtils.showSettingDialog(SettingActivity.this, SettingActivity.this.getResources().getString(R.string.storage_permission_string));
                        }
                    }
                })
                .start();
    }


}
