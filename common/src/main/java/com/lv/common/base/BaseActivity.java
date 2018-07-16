package com.lv.common.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.lv.common.R;
import com.lv.common.utils.HideInputUtils;


/**
 * Created by Lv on 2016/10/27.
 */
public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
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
