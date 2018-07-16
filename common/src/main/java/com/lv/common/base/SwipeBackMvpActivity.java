package com.lv.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;


/**
 * Created by Lv on 2016/10/28.
 * 带滑动返回的activity
 */
public abstract class SwipeBackMvpActivity<P extends BasePresenter> extends SwipeBackActivity {

    protected P mvpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter = createPresenter();
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }

}
