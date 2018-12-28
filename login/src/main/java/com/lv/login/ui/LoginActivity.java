package com.lv.login.ui;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lv.common.base.SwipeBackMvpActivity;
import com.lv.common.data.CommonPath;
import com.lv.common.utils.IntentUtils;
import com.lv.common.utils.MyToast;
import com.lv.common.utils.ValidateUtil;
import com.lv.common.widget.button.TimeButton;
import com.lv.login.R;
import com.lv.login.presenter.LoginPresenter;
import com.lv.login.view.LoginView;
import com.scwang.wave.MultiWaveHeader;
import com.xw.repo.XEditText;

@Route(path = CommonPath.LOGIN_ACTIVITY_PATH)
public class LoginActivity extends SwipeBackMvpActivity<LoginPresenter> implements View.OnClickListener, LoginView {

    private MultiWaveHeader waveHeaderLogin;
    private XEditText editTextPhone;
    private XEditText editValidateCode;
    private TimeButton btnGetCode;
    private Button btnLogin;
    private Button btnToRegister;

    private String phone;
    private String validateCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this, this);
    }

    @Override
    protected void initUI() {

        waveHeaderLogin = (MultiWaveHeader) findViewById(R.id.wave_header_login);
        editTextPhone = (XEditText) findViewById(R.id.edit_text_phone);
        editValidateCode = (XEditText) findViewById(R.id.edit_validate_code);
        btnGetCode = (TimeButton) findViewById(R.id.btn_get_code);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnToRegister = (Button) findViewById(R.id.btn_to_register);

        initToolBar("登录账号", true);
        /**
         * 格式-format
         * offsetX offsetY scaleX scaleY velocity（dp/s）
         * 水平偏移量 竖直偏移量 水平拉伸比例 竖直拉伸比例 速度
         */
        waveHeaderLogin.setWaves("0,0,1,1,25");
        waveHeaderLogin.setScaleY(-1f);
        btnLogin.setOnClickListener(this);
        btnGetCode.setOnClickListener(this);
        btnToRegister.setOnClickListener(this);
    }

    /**
     * 登录校验
     *
     * @return
     */
    private boolean validateLogin() {
        boolean result = true;
        phone = editTextPhone.getText().toString().trim();
        validateCode = editValidateCode.getText().toString().trim();
        StringBuilder stringBuilder = new StringBuilder();

        if (TextUtils.isEmpty(phone)) {
            stringBuilder.append("手机号不能为空\n");
        } else if (!ValidateUtil.validatePhone(phone)) {
            stringBuilder.append("请输入正确的手机号\n");
        } else {
            if (TextUtils.isEmpty(validateCode)) {
                stringBuilder.append("验证码不能为空\n");
            } else if (!ValidateUtil.validateSmsCode(validateCode)) {
                stringBuilder.append("请输入有效的验证码\n");
            }
        }
        if (!TextUtils.isEmpty(stringBuilder)) {
            result = false;
            MyToast.showShortToast(LoginActivity.this, stringBuilder.substring(0, stringBuilder.length() - 1));
        }
        return result;
    }

    /**
     * 获取验证码校验
     *
     * @return
     */
    private boolean validateCode() {
        boolean result = true;
        phone = editTextPhone.getText().toString().trim();
        StringBuilder stringBuilder = new StringBuilder();

        if (TextUtils.isEmpty(phone)) {
            stringBuilder.append("手机号不能为空\n");
        } else if (!ValidateUtil.validatePhone(phone)) {
            stringBuilder.append("请输入正确的手机号\n");
        }
        if (!TextUtils.isEmpty(stringBuilder)) {
            result = false;
            MyToast.showShortToast(LoginActivity.this, stringBuilder.substring(0, stringBuilder.length() - 1));
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_login) {
            if (validateLogin()) {
                showLoadToast("登录中");
                //延迟请求数据
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mvpPresenter.loginReq(phone, validateCode);
                    }
                }, 1000);
            }
        } else if (id == R.id.btn_get_code) {
            if (validateCode()) {
                showLoadToast("发送短信中");
                //延迟请求数据
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mvpPresenter.getSmsCodeReq(phone);
                    }
                }, 1000);
            }
        } else if (id == R.id.btn_to_register) {
            ARouter.getInstance()
                    .build(CommonPath.REGISTER_ACTIVITY_PATH)
                    .withTransition(com.lv.common.R.anim.slide_in, com.lv.common.R.anim.slide_out_back)
                    .navigation();
        }
    }


    @Override
    public void getCodeSuccess() {
        btnGetCode.start();
    }

    @Override
    public void loginSuccess(String tokenTemp, String tokenFixed) {
        //保存临时token
        setTokenTemp(tokenTemp);
        //保存永久token
        setTokenFixed(tokenFixed);
        //保存已登录状态
        setHasLogin(true);
        //通知首页已经登录
        IntentUtils.notifyHasLogin();
        onBackPressed();
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void hideLoad() {
        loadToast.hide();
    }

}
