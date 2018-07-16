package com.lv.common.base;

/**
 * Created by Lv on 2017/3/14.
 */

public interface PullAndMoreView extends BaseLoadView {

    //停止刷新
    public void stopRefresh();

    //没有更多了
    public void loadNoMore();

    //加载更多失败
    public void loadMoreFail();
}
