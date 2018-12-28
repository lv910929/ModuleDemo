package com.lv.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.View;

import com.lv.common.R;
import com.lv.common.utils.PrefUtils;

import net.steamcrafted.loadtoast.LoadToast;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BaseFragment extends Fragment {

    protected LoadToast loadToast;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadToast = new LoadToast(getActivity());
    }

    protected void initUI(View view) {

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
     * 子类实现该方法用于替代 getResources().getString(id) 以及进行相应的类型检查
     *
     * @param id
     * @return
     */
    protected String getStringById(@StringRes int id) {
        return getResources().getString(id);
    }


    //判断是否第一次安装
    public boolean isFirstRun() {
        return PrefUtils.getBoolean(getActivity(), "isFirstRun", true);
    }

    //设置是否第一次安装
    public void setFirstRun(boolean isFirstRun) {
        PrefUtils.putBoolean(getActivity(), "isFirstRun", isFirstRun);
    }

    //判断登录状态
    public boolean hasLogin() {
        return PrefUtils.getBoolean(getActivity(), "hasLogin", false);
    }

    //设置登录状态
    public void setHasLogin(boolean hasLogin) {
        PrefUtils.putBoolean(getActivity(), "hasLogin", hasLogin);
    }

    //存放永久token
    public void setTokenFixed(String tokenFixed) {
        PrefUtils.putString(getActivity(), "tokenFixed", tokenFixed);
    }

    //获取永久Token
    public String getTokenFixed() {
        return PrefUtils.getString(getActivity(), "tokenFixed", "");
    }

    //获取临时token
    public String getTokenTemp() {
        return PrefUtils.getString(getActivity(), "tokenTemp", "");
    }

    //存放临时token
    public void setTokenTemp(String tokenTemp) {
        PrefUtils.putString(getActivity(), "tokenTemp", tokenTemp);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onUnsubscribe();
    }

    private CompositeSubscription mCompositeSubscription;

    public void onUnsubscribe() {
        //取消注册，以避免内存泄露
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    public void addSubscription(Subscription subscription) {
//        if (mCompositeSubscription == null) {
        mCompositeSubscription = new CompositeSubscription();
//        }
        mCompositeSubscription.add(subscription);
    }


}
