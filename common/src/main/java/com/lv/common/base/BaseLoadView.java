package com.lv.common.base;

/**
 * Created by Lv on 2017/2/9.
 */
public interface BaseLoadView extends BaseView {

    //显示加载界面
    public void showLayoutLoad();

    //显示内容界面
    public void showLayoutContent();

    //显示空数据界面
    public void showLayoutEmpty();

    //显示错误界面
    public void showLayoutError(int code);
}
