package com.lv.main.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.lv.common.base.BaseMvpActivity;
import com.lv.main.R;
import com.lv.main.presenter.MainPresenter;
import com.lv.main.view.MainView;


public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainView, AHBottomNavigation.OnTabSelectedListener {

    private FrameLayout frameMain;
    private AHBottomNavigation bottomNavigation;

    private AHBottomNavigationItem itemHome;
    private AHBottomNavigationItem itemDiscover;
    private AHBottomNavigationItem itemMine;

    private int fragment_index = 0;
    private FragmentManager fragmentManager;

    private HomeFragment homeFragment;
    private DiscoverFragment discoverFragment;
    private MineFragment mineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        initUI();
    }

    @Override
    protected void initUI() {
        frameMain = (FrameLayout) findViewById(R.id.frame_main);
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        setBottomNavigation();
        setTabSelection(0);
    }

    private void setBottomNavigation() {
        // Create items
        itemHome = new AHBottomNavigationItem("首页", R.drawable.tab_home_icon);
        itemDiscover = new AHBottomNavigationItem("发现", R.drawable.tab_discover_icon);
        itemMine = new AHBottomNavigationItem("我的", R.drawable.tab_mine_icon);

        // Add items
        bottomNavigation.addItem(itemHome);
        bottomNavigation.addItem(itemDiscover);
        bottomNavigation.addItem(itemMine);

        // Set background color
        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.white));
        // Disable the translation inside the CoordinatorLayout
        bottomNavigation.setBehaviorTranslationEnabled(true);
        // Change colors
        bottomNavigation.setAccentColor(getResources().getColor(R.color.colorAccent));
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.grey500));
        // Set current item programmatically
        bottomNavigation.setCurrentItem(0);
        // Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(true);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        // Customize notification (title, background, typeface)
        bottomNavigation.setNotificationBackgroundColor(getResources().getColor(R.color.colorAccent));
        bottomNavigation.setOnTabSelectedListener(this);
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        setTabSelection(position);
        return true;
    }

    /**
     * 切换fragment
     */
    private void setTabSelection(int index) {
        fragment_index = index;
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.frame_main, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                break;
            case 1:
                if (discoverFragment == null) {
                    discoverFragment = new DiscoverFragment();
                    transaction.add(R.id.frame_main, discoverFragment);
                } else {
                    transaction.show(discoverFragment);
                }
                break;
            case 2:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.frame_main, mineFragment);
                } else {
                    transaction.show(mineFragment);
                }
                break;
        }
        invalidateOptionsMenu();
        transaction.commitAllowingStateLoss();
    }

    /**
     * 显示首页
     */
    private void showHomeFragment(FragmentTransaction transaction) {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            transaction.add(R.id.frame_main, homeFragment);
        } else {
            transaction.show(homeFragment);
        }
        bottomNavigation.setCurrentItem(0);
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (discoverFragment != null) {
            transaction.hide(discoverFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
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
