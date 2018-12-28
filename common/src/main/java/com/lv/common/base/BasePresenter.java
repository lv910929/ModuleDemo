package com.lv.common.base;

import android.app.Activity;
import android.content.Context;

import com.lv.common.R;
import com.lv.common.entity.HttpResult;
import com.lv.common.entity.TokenBody;
import com.lv.common.entity.TokenEntity;
import com.lv.common.http.ApiCallback;
import com.lv.common.http.CommBuildApi;
import com.lv.common.http.HttpConstant;
import com.lv.common.utils.MyToast;
import com.tencent.bugly.crashreport.BuglyLog;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter<V> {

    public V mvpView;

    public Context context;

    private CompositeSubscription mCompositeSubscription;

    protected GetTokenCallBack getTokenCallBack;

    public void attachView(V mvpView) {
        this.mvpView = mvpView;
    }

    public void detachView() {
        this.mvpView = null;
        onUnSubscribe();
    }

    //RXjava取消注册，以避免内存泄露
    public void onUnSubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }


    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

    //返回退出动画
    public void onBackPressed() {
        ((Activity) context).overridePendingTransition(R.anim.slide_in_back, R.anim.slide_out);
        ((Activity) context).finish();
    }

    /**
     * 获取临时token
     *
     * @param tokenFixed 永久token
     */
    public void getTokenTempReq(String tokenFixed) {
        Observable<HttpResult<TokenEntity>> getTokenObservable = CommBuildApi.getAPIService(context).getTokenReq(new TokenBody(tokenFixed));
        addSubscription(getTokenObservable, new ApiCallback<HttpResult<TokenEntity>>() {

            @Override
            public void onSuccess(HttpResult<TokenEntity> model) {
                if (model.getStatus() == HttpConstant.SUCCESS_STATE_CODE) { //说明成功
                    BuglyLog.e("token：---------", "token获取成功！");
                    getTokenCallBack.getTokenSuccess();
                } else {
                    MyToast.showShortToast(context, model.getMessage());
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                BuglyLog.e("token：---------", msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    public interface GetTokenCallBack {
        void getTokenSuccess();
    }

}
