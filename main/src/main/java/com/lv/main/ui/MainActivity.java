package com.lv.main.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.lv.common.base.BaseMvpActivity;
import com.lv.common.callback.HomeFragmentCallBack;
import com.lv.common.data.CommConfig;
import com.lv.common.data.CommonPath;
import com.lv.common.event.MainEvent;
import com.lv.common.permission.RuntimeRationale;
import com.lv.common.utils.DialogUtils;
import com.lv.main.R;
import com.lv.main.presenter.MainPresenter;
import com.lv.main.view.MainView;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

@Route(path = CommonPath.MAIN_ACTIVITY_PATH)
public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainView, AHBottomNavigation.OnTabSelectedListener,HomeFragmentCallBack {

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
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        initUI();
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this, this);
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

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEvent(MainEvent event) {
        switch (event.getWhat()) {
            case 0://登录回调
                if (homeFragment != null) {
                    homeFragment.checkLogin();
                }
                if (mineFragment != null) {
                    mineFragment.checkLogin();
                }
                break;
            case 1://跳转到扫一扫
                requestPermission(CommConfig.PHOTO_PERMISSIONS);
                break;
        }
    }

    /**
     * Request permissions.
     */
    private void requestPermission(String... permissions) {
        AndPermission.with(this)
                .runtime()
                .permission(permissions)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        //获取权限成功
                        ARouter.getInstance()
                                .build(CommonPath.SCAN_ACTIVITY_PATH)
                                .withTransition(com.lv.common.R.anim.slide_in, com.lv.common.R.anim.slide_out_back)
                                .navigation();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        //获取权限失败
                        if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, permissions)) {
                            DialogUtils.showSettingDialog(MainActivity.this, MainActivity.this.getResources().getString(R.string.camera_permission_string));
                        }
                    }
                })
                .start();
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void hideLoad() {

    }

    @Override
    public void transferMsg() {

    }

    @Override
    public void notificationUpdate(int notificationNum) {

    }

    @Override
    public void checkLogin() {

    }

    @Override
    public void changeMode(boolean editMode) {

    }

    @Override
    public void notifyCollectNum() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
