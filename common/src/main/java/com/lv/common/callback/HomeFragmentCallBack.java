package com.lv.common.callback;

/**
 * Created by Lv on 2016/4/5.
 */
public interface HomeFragmentCallBack {

    // 这里响应在FragmentActivity中的控件交互
    public void transferMsg();

    // 通知activity更新小标数字
    public void notificationUpdate(int notificationNum);

    //通知fragment登录成功
    public void checkLogin();

    //通知购物车切换模式
    public void changeMode(boolean editMode);

    //通知更新收藏角标
    public void notifyCollectNum();
    
}
