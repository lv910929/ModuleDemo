package com.lv.main.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.lv.common.base.BaseMvpActivity;
import com.lv.main.R;
import com.lv.main.presenter.MainPresenter;
import com.lv.main.view.MainView;


public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainView {

    private Toolbar toolbarComm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    @Override
    protected void initUI() {
        toolbarComm = (Toolbar) findViewById(R.id.toolbar_comm);
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this, this);
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void hideLoad() {

    }

}
