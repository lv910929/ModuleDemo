package com.lv.common.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.lv.common.R;
import com.lv.common.utils.HideInputUtils;
import com.lv.common.utils.PrefUtils;

import net.steamcrafted.loadtoast.LoadToast;


/**
 * Created by Lv on 2016/10/27.
 */
public class BaseActivity extends AppCompatActivity {

    protected LoadToast loadToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        loadToast = new LoadToast(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        //setStatusBar();
    }

    /**
     * 显示加载动画
     *
     * @param message
     */
    protected void showLoadToast(String message) {
        loadToast.setText(message);
        loadToast.setTextColor(this.getResources().getColor(R.color.colorPrimary));
        loadToast.setTranslationY(300);
        loadToast.show();
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    //初始化控件
    protected void initUI() {
    }

    protected Toolbar toolbar;
    protected TextView titleText;

    //设置通用的toolbar
    protected void initToolBar(String title, boolean needBack) {
        toolbar = (Toolbar) findViewById(R.id.toolbar_comm);
        titleText = (TextView) findViewById(R.id.text_title);
        setTitle("");
        titleText.setText(title);
        if (needBack) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
        StatusBarUtil.setTranslucent(this, 60);
    }

    //设置状态栏透明
    protected void setStatusTransparent() {
        StatusBarUtil.setTransparent(this);
    }

    //隐藏软键盘
    protected void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 点击屏幕空白处，隐藏输入法
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (HideInputUtils.isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    protected void showView(View showView, View hideView) {
        showView.setVisibility(View.VISIBLE);
        hideView.setVisibility(View.GONE);
    }

    protected void hideView(View showView, View hideView) {
        showView.setVisibility(View.GONE);
        hideView.setVisibility(View.VISIBLE);
    }

    //判断是否第一次安装
    public boolean isFirstRun() {
        return PrefUtils.getBoolean(this, "isFirstRun", true);
    }

    //设置是否第一次安装
    public void setFirstRun(boolean isFirstRun) {
        PrefUtils.putBoolean(this, "isFirstRun", isFirstRun);
    }

    //判断登录状态
    public boolean hasLogin() {
        return PrefUtils.getBoolean(this, "hasLogin", false);
    }

    //设置登录状态
    public void setHasLogin(boolean hasLogin) {
        PrefUtils.putBoolean(this, "hasLogin", hasLogin);
    }

    //存放永久token
    public void setTokenFixed(String tokenFixed) {
        PrefUtils.putString(this, "tokenFixed", tokenFixed);
    }

    //获取永久Token
    public String getTokenFixed() {
        return PrefUtils.getString(this, "tokenFixed", "");
    }

    //获取临时token
    public String getTokenTemp() {
        return PrefUtils.getString(this, "tokenTemp", "");
    }

    //存放临时token
    public void setTokenTemp(String tokenTemp) {
        PrefUtils.putString(this, "tokenTemp", tokenTemp);
    }

    //返回退出动画
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_back, R.anim.slide_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Glide.with(this.getApplicationContext()).onDestroy();
        super.onDestroy();
    }

}
