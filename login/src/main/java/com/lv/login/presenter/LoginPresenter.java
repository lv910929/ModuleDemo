package com.lv.login.presenter;

import android.content.Context;

import com.lv.common.base.BasePresenter;
import com.lv.common.base.BaseView;

public class LoginPresenter extends BasePresenter<BaseView> {

    public LoginPresenter(Context context, BaseView view) {
        this.context = context;
        attachView(view);
    }



}
