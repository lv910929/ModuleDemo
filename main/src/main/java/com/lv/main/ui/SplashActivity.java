package com.lv.main.ui;

import android.os.Bundle;

import com.lv.common.base.BaseMvpActivity;
import com.lv.common.base.BasePresenter;
import com.lv.main.R;

public class SplashActivity extends BaseMvpActivity<BasePresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

}
