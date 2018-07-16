package com.lv.common.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lv.common.R;

/**
 * Created by Lv on 2016/11/29.
 * 带上拉下拉的activity
 */

public abstract class PullAndMoreActivity<P extends BasePresenter> extends BaseLoadingActivity implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    protected P mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    protected abstract P createPresenter();

    //设置下拉刷新样式
    protected void setSwipeToRefresh(EasyRecyclerView easyRecyclerView) {
        easyRecyclerView.setRefreshingColor(getResources().getColor(R.color.colorAccent));
        easyRecyclerView.getSwipeToRefresh().setDistanceToTriggerSync(500);
        easyRecyclerView.setRefreshListener(this);
    }

    //设置上拉刷新
    protected void initRecyclerArrayAdapter(final RecyclerArrayAdapter adapter) {
        adapter.setMore(R.layout.view_more, this);
        adapter.setNoMore(R.layout.view_nomore, null);
        adapter.setError(R.layout.view_error, new RecyclerArrayAdapter.OnErrorListener() {
            @Override
            public void onErrorShow() {
                adapter.resumeMore();
            }

            @Override
            public void onErrorClick() {
                adapter.resumeMore();
            }
        });
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {

    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }

}
