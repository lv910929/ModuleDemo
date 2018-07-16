package com.lv.common.widget.recyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lv.common.utils.CompatUtils;


/**
 * Created by Lv on 2016/5/11.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    private Context context;

    //单位dp
    public SpaceItemDecoration(Context context, int space) {
        this.context = context;
        this.space = CompatUtils.dip2px(context, space);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (parent.getChildPosition(view) != 0)
            outRect.top = space;
    }
}
