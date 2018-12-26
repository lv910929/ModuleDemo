package com.lv.common.base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lv.common.R;
import com.lv.common.http.HttpConstant;
import com.lv.common.utils.DoubleClickUtil;
import com.malinskiy.materialicons.IconDrawable;
import com.malinskiy.materialicons.Iconify;
import com.vlonjatg.progressactivity.ProgressRelativeLayout;


/**
 * MainActivity中的fragment继承这个
 */
public abstract class BaseLoadFragment<P extends BasePresenter> extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener {

    private ProgressRelativeLayout mProgressActivity;
    protected Drawable errorDrawable;
    protected Drawable emptyDrawable;

    protected FloatingActionButton btnToTop;
    protected int itemHeight;
    protected boolean isPassFirstItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mProgressActivity = (ProgressRelativeLayout) inflater.inflate(R.layout.fragment_base_loading, container, false);
        mProgressActivity.addView(onCreateContentView(inflater, mProgressActivity, savedInstanceState));
        errorDrawable = new IconDrawable(getContext(), Iconify.IconValue.zmdi_wifi_off).colorRes(R.color.grey300);
        emptyDrawable = new IconDrawable(getContext(), Iconify.IconValue.zmdi_coffee).colorRes(R.color.grey300);
        return mProgressActivity;
    }

    protected abstract View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected void showLoading() {
        if (!mProgressActivity.isLoadingCurrentState())
            mProgressActivity.showLoading();
    }

    protected void showContent() {
        if (!mProgressActivity.isContentCurrentState())
            mProgressActivity.showContent();
    }

    protected void showError(String errorTextTitle, String errorTextContent, String errorButtonText, View.OnClickListener onClickListener) {
        mProgressActivity.showError(errorDrawable, errorTextTitle, errorTextContent, errorButtonText, onClickListener);
    }

    protected void showEmpty(String emptyTextTitle, String emptyTextContent) {
        mProgressActivity.showEmpty(emptyDrawable, emptyTextTitle, emptyTextContent);
    }

    protected void showEmpty(String emptyTextTitle, String emptyTextContent, String emptyButtonText, View.OnClickListener onClickListener) {
        mProgressActivity.showError(emptyDrawable, emptyTextTitle, emptyTextContent, emptyButtonText, onClickListener);
    }

    protected void getData() {

    }

    //通用的出错处理
    protected void commFailResult(int code) {
        switch (code) {
            case HttpConstant.FAIL_CODE_404:
            case HttpConstant.FAIL_CODE_502:
            case HttpConstant.FAIL_CODE_1001:
            case HttpConstant.FAIL_CODE_1004:
                showError("服务器异常", "服务器出了点问题，请稍后再试", "重试", mErrorRetryListener);
                break;
            case HttpConstant.FAIL_CODE_504:
                showError("网络不给力", "好像您的网络出了点问题", "重试", mErrorRetryListener);
                break;
            case HttpConstant.FAIL_CODE_1002:
                showError("网络不给力", "连接超时，请稍后再试", "重试", mErrorRetryListener);
                break;
            case HttpConstant.FAIL_CODE_1005:
                showError("服务器异常", "验证失败，请稍后再试", "重试", mErrorRetryListener);
                break;
        }
    }

    protected View.OnClickListener mErrorRetryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (DoubleClickUtil.isFastDoubleClick()) return; //防止连续点击打开多个界面
            showLoading();
            getData();
        }
    };

    protected View.OnClickListener mLoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (DoubleClickUtil.isFastDoubleClick()) return; //防止连续点击打开多个界面
            //ActivityUtil.startActivity(getActivity(), LoginActivity.class, null);
        }
    };

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

    //设置下拉刷新样式
    protected void setSwipeRefreshLayout(EasyRecyclerView easyRecyclerView) {
        if (easyRecyclerView != null) {
            easyRecyclerView.getSwipeToRefresh().setColorSchemeResources(R.color.colorAccent);
            easyRecyclerView.getSwipeToRefresh().setDistanceToTriggerSync(400);
        }
    }

    //初始化返回顶部按钮
    protected void initBtnToTop(View view, final RecyclerView recyclerView) {
        btnToTop = (FloatingActionButton) view.findViewById(R.id.btn_to_top);
        btnToTop.setVisibility(View.GONE);
        btnToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(0);
            }
        });
    }

    //recyclerView滚动距离 向上
    protected int getScrolledDistance(RecyclerView swipeTarget, boolean isSingleList) {
        if (swipeTarget != null) {
            if (isSingleList) {//是瀑布流
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) swipeTarget.getLayoutManager();
                View firstVisibleItem = staggeredGridLayoutManager.getChildAt(0);
                if (firstVisibleItem != null) {
                    int[] firstItemPosition = staggeredGridLayoutManager.findFirstVisibleItemPositions(new int[]{0, 1});
                    itemHeight = firstVisibleItem.getHeight();
                    int firstItemBottom = staggeredGridLayoutManager.getDecoratedBottom(firstVisibleItem);
                    return (firstItemPosition[0]) * itemHeight - firstItemBottom;
                } else {
                    return 0;
                }
            } else {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) swipeTarget.getLayoutManager();
                View firstVisibleItem = linearLayoutManager.getChildAt(1);
                if (firstVisibleItem != null) {
                    int firstItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    itemHeight = firstVisibleItem.getHeight();
                    int firstItemBottom = linearLayoutManager.getDecoratedBottom(firstVisibleItem);
                    return (firstItemPosition + 1) * itemHeight - firstItemBottom;
                } else {
                    return 0;
                }
            }
        } else {
            return 0;
        }
    }

    /**
     * 设置recyclerView的滚动事件
     *
     * @param swipeTarget
     * @param isSingleList 是否是单品瀑布流
     */
    protected void setScrollEvent(final RecyclerView swipeTarget, final boolean isSingleList) {
        //监听滚动 滚动距离超过第一个item时显示返回按钮
        swipeTarget.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int scrollHeight = getScrolledDistance(swipeTarget, isSingleList);
                if (scrollHeight < itemHeight) {
                    isPassFirstItem = false;
                    btnToTop.setVisibility(View.GONE);
                } else {
                    btnToTop.setVisibility(View.VISIBLE);
                    isPassFirstItem = true;
                }
            }
        });
    }

    protected P mvpPresenter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mvpPresenter = createPresenter();
    }

    protected abstract P createPresenter();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }

}
