package com.lv.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.lv.common.utils.CompatUtils;

/**
 * Created by Lv on 2016/10/28.
 * 带滑动返回的activity
 */
public class SwipeBackActivity extends BaseActivity {

    protected boolean isCollapsingExpand;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(true)
                .setSwipeBackEnable(true)//on-off
                .setSwipeEdge(100)//set the touch area。200 mean only the left 200px of screen can touch to begin swipe.
                .setSwipeEdgePercent(0.1f)//0.2 mean left 20% of screen can touch to begin swipe.
                .setSwipeSensitivity(0.5f)//sensitiveness of the gesture。0:slow  1:sensitive
                .setClosePercent(0.8f);//close activity when swipe over this
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isCollapsingExpand", isCollapsingExpand);
    }

    //设置CollapsingToolbarLayout滑动效果
    protected void setCollapsingToolbarLayout(AppBarLayout appBar, final CollapsingToolbarLayout collapsingToolbarLayout) {
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //最大偏移量
                int maxVerticalOffset = appBarLayout.getTotalScrollRange() - CompatUtils.dip2px(SwipeBackActivity.this, 50);
                if (Math.abs(verticalOffset) >= maxVerticalOffset) {
                    isCollapsingExpand = false;//修改状态标记为折叠
                } else {
                    isCollapsingExpand = true;//修改状态标记为展开
                }
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }
}
