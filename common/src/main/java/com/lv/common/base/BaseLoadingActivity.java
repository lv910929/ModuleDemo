package com.lv.common.base;

import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lv.common.R;
import com.lv.common.http.HttpConstant;
import com.lv.common.utils.MyToast;
import com.malinskiy.materialicons.IconDrawable;
import com.malinskiy.materialicons.Iconify;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Method;

/**
 * Created by Lv on 2016/11/4.
 * 带下拉上拉列表的界面
 */
public class BaseLoadingActivity extends SwipeBackActivity {

    protected FloatingActionButton btnToTop;
    protected int itemHeight;
    protected boolean isPassFirstItem;

    //内容布局
    protected RelativeLayout layoutContent;
    //加载布局
    protected RelativeLayout layoutLoading;
    //空数据布局
    protected LinearLayout layoutEmpty;
    protected ImageView imageEmptyItem;
    protected TextView textEmptyItem;
    //出错布局
    protected LinearLayout layoutError;
    protected ImageView imageErrorItem;
    protected TextView textErrorItem;
    protected Button buttonRetry;

    protected void initStateLayout() {
        //内容布局
        layoutContent = (RelativeLayout) findViewById(R.id.layout_content);
        //加载布局
        layoutLoading = (RelativeLayout) findViewById(R.id.layout_loading);
        //空数据布局
        layoutEmpty = (LinearLayout) findViewById(R.id.layout_empty);
        imageEmptyItem = (ImageView) findViewById(R.id.image_empty_item);
        textEmptyItem = (TextView) findViewById(R.id.text_empty_item);
        setEmptyLayout(getString(R.string.no_data_hint));
        //出错布局
        layoutError = (LinearLayout) findViewById(R.id.layout_error);
        imageErrorItem = (ImageView) findViewById(R.id.image_error_item);
        textErrorItem = (TextView) findViewById(R.id.text_error_item);
        buttonRetry = (Button) findViewById(R.id.button_retry);
        setErrorLayout(getString(R.string.error_data_hint));
    }

    /**
     * 通过反射，设置menu显示icon
     *
     * @param view
     * @param menu
     * @return
     */
    @SuppressWarnings("RestrictedApi")
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    /**
     * 设置空数据布局
     */
    protected void setEmptyLayout(String emptyMessage) {
        Drawable emptyDrawable = new IconDrawable(BaseLoadingActivity.this, Iconify.IconValue.zmdi_coffee).colorRes(R.color.grey300);
        imageEmptyItem.setImageDrawable(emptyDrawable);
        textEmptyItem.setText(emptyMessage);
    }

    /**
     * 设置出错布局
     */
    protected void setErrorLayout(String message) {
        Drawable errorDrawable = new IconDrawable(BaseLoadingActivity.this, Iconify.IconValue.zmdi_wifi_off).colorRes(R.color.grey300);
        imageErrorItem.setImageDrawable(errorDrawable);
        textErrorItem.setText(message);
        buttonRetry.setOnClickListener(mRetryListener);
    }

    private View.OnClickListener mRetryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showLoadLayout();
            getFirstData();
        }
    };

    //显示内容布局
    public void showContentLayout() {
        layoutContent.setVisibility(View.VISIBLE);
        layoutLoading.setVisibility(View.GONE);
        layoutEmpty.setVisibility(View.GONE);
        layoutError.setVisibility(View.GONE);
    }

    //显示加载布局
    public void showLoadLayout() {
        layoutLoading.setVisibility(View.VISIBLE);
        layoutContent.setVisibility(View.GONE);
        layoutEmpty.setVisibility(View.GONE);
        layoutError.setVisibility(View.GONE);
    }

    //显示空数据布局
    public void showEmptyLayout() {
        layoutContent.setVisibility(View.VISIBLE);
        layoutEmpty.setVisibility(View.VISIBLE);
        layoutLoading.setVisibility(View.GONE);
        layoutError.setVisibility(View.GONE);
    }

    //显示出错布局
    public void showErrorLayout(String message) {
        layoutError.setVisibility(View.VISIBLE);
        layoutLoading.setVisibility(View.GONE);
        layoutContent.setVisibility(View.GONE);
        layoutEmpty.setVisibility(View.GONE);
        setErrorLayout(message);
    }

    //通用的第一次获取数据的方法
    protected void getFirstData() {

    }

    //通用的出错处理
    protected void commFailResult(int code) {
        switch (code) {
            case HttpConstant.FAIL_CODE_404:
            case HttpConstant.FAIL_CODE_502:
            case HttpConstant.FAIL_CODE_1001:
            case HttpConstant.FAIL_CODE_1004:
                showErrorLayout("服务器出了点问题，请稍后再试");
                break;
            case HttpConstant.FAIL_CODE_504:
                showErrorLayout("好像您的网络出了点问题");
                break;
            case HttpConstant.FAIL_CODE_1002:
                showErrorLayout("连接超时，请稍后再试");
                break;
            case HttpConstant.FAIL_CODE_1005:
                showErrorLayout("验证失败，请稍后再试");
                break;
        }
    }

    //初始化返回顶部按钮
    protected void initBtnToTop(final RecyclerView recyclerView) {
        btnToTop = (FloatingActionButton) findViewById(R.id.btn_to_top);
        btnToTop.setAlpha(0.6f);
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
            if (swipeTarget.getLayoutManager() instanceof StaggeredGridLayoutManager) {//是瀑布流
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

    //加载失败提示
    protected void showErrorToast() {
        MyToast.showBottomToast(BaseLoadingActivity.this, R.id.layout_content, getString(R.string.load_data_fail));
    }

}
