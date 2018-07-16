package com.lv.main.presenter;

import android.content.Context;

import com.lv.common.base.BasePresenter;
import com.lv.main.view.MainView;

/**
 * 首页
 */
public class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter(Context context, MainView view) {
        this.context = context;
        attachView(view);
    }

}
