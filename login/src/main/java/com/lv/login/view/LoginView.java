package com.lv.login.view;


import com.lv.common.base.BaseView;

/**
 * Created by Lv on 2017/6/15.
 */

public interface LoginView extends BaseView {

    void getCodeSuccess();

    void loginSuccess(String tokenTemp, String tokenFixed);
}
