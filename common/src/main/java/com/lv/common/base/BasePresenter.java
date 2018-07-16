package com.lv.common.base;

import android.app.Activity;
import android.content.Context;
import com.lv.common.R;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter<V> {

    public V mvpView;

    public Context context;

    private CompositeSubscription mCompositeSubscription;

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

}
