package com.lv.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lv.common.base.BaseMvpActivity;
import com.lv.common.base.BasePresenter;
import com.lv.common.utils.MyToast;
import com.lv.common.utils.NetUtils;
import com.lv.main.R;

public class SplashActivity extends BaseMvpActivity<BasePresenter> {

    private ImageView imageSplashLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initUI();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initUI() {
        imageSplashLogo = (ImageView) findViewById(R.id.image_splash_logo);

        final ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnim.setFillAfter(true);
        scaleAnim.setDuration(2000);
        scaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!NetUtils.checkNetWork(SplashActivity.this)) {
                    MyToast.showBottomToast(SplashActivity.this, R.id.layout_content, "无网络可用");
                }
                ARouter.getInstance()
                        .build("/login/LoginActivity")
                        .withTransition(com.lv.common.R.anim.slide_in, com.lv.common.R.anim.slide_out_back)
                        .navigation();
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageSplashLogo.startAnimation(scaleAnim);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initUI();
    }

}
