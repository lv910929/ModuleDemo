package com.lv.login.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lv.common.base.BasePresenter;
import com.lv.common.base.SwipeBackMvpActivity;
import com.lv.common.widget.button.TimeButton;
import com.lv.login.R;
import com.scwang.wave.MultiWaveHeader;
import com.xw.repo.XEditText;

@Route(path = "/login/RegisterActivity")
public class RegisterActivity extends SwipeBackMvpActivity<BasePresenter> implements View.OnClickListener {

    private MultiWaveHeader waveHeaderRegister;
    private XEditText editTextPhone;
    private XEditText editValidateCode;
    private TimeButton btnGetCode;
    private XEditText editTextPwd;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initUI() {
        super.initUI();

        waveHeaderRegister = (MultiWaveHeader) findViewById(R.id.wave_header_register);
        editTextPhone = (XEditText) findViewById(R.id.edit_text_phone);
        editValidateCode = (XEditText) findViewById(R.id.edit_validate_code);
        btnGetCode = (TimeButton) findViewById(R.id.btn_get_code);
        editTextPwd = (XEditText) findViewById(R.id.edit_text_pwd);
        btnRegister = (Button) findViewById(R.id.btn_register);

        initToolBar("注册账号", true);
        /**
         * 格式-format
         * offsetX offsetY scaleX scaleY velocity（dp/s）
         * 水平偏移量 竖直偏移量 水平拉伸比例 竖直拉伸比例 速度
         */
        waveHeaderRegister.setWaves("0,0,1,1,25");
        waveHeaderRegister.setScaleY(-1f);
        btnGetCode.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_get_code) {

        } else if (id == R.id.btn_register) {

        }
    }

}
