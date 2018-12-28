package com.lv.main.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.allen.library.SuperTextView;
import com.lv.common.base.BaseLoadMvpActivity;
import com.lv.common.base.BasePresenter;
import com.lv.common.data.CommonPath;
import com.lv.common.utils.DataCleanManager;
import com.lv.common.utils.IntentUtils;
import com.lv.main.R;

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

        } else if (id == R.id.super_text_cache) {

        } else if (id == R.id.super_text_share) {

        }
    }

}
