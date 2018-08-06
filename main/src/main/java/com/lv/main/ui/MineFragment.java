package com.lv.main.ui;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lv.common.base.BaseFragment;
import com.lv.common.widget.recyclerview.MyRecyclerView;
import com.lv.main.R;

import cn.carbs.android.avatarimageview.library.AvatarImageView;

/**
 * 我的
 */
public class MineFragment extends BaseFragment {

    private CollapsingToolbarLayout collapsingToolbar;
    private AvatarImageView avatarImageUser;
    private Toolbar toolbar;
    private MyRecyclerView recyclerMine;

    public MineFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }

    @Override
    protected void initUI(View view) {
        collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        avatarImageUser = (AvatarImageView) view.findViewById(R.id.avatar_image_user);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        recyclerMine = (MyRecyclerView) view.findViewById(R.id.recycler_mine);
        setCollapsingToolbar();
        setRecyclerMine(view);
    }

    private void setCollapsingToolbar() {
        String firstChar = String.valueOf("我".charAt(0));
        avatarImageUser.setTextAndColor("我", AvatarImageView.COLORS[0]);
    }

    private void setRecyclerMine(View view){
        recyclerMine.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

}