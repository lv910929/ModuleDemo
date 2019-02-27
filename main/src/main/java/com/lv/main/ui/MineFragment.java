package com.lv.main.ui;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lv.common.base.BasePresenter;
import com.lv.common.data.CommonPath;
import com.lv.common.http.CommConstant;
import com.lv.common.widget.recyclerview.MyRecyclerView;
import com.lv.main.R;
import com.lv.main.ui.base.BaseHomeFragment;
import com.scwang.wave.MultiWaveHeader;

import cn.carbs.android.avatarimageview.library.AvatarImageView;

/**
 * 我的
 */
public class MineFragment extends BaseHomeFragment<BasePresenter> implements View.OnClickListener {

    private AvatarImageView avatarImageUser;
    private TextView textUserNickname;
    private LinearLayout layoutUserModule;

    private MyRecyclerView recyclerMine;
    private MultiWaveHeader waveHeaderLogin;
    private ImageView btnToSetting;

    public MineFragment() {
    }

    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initUI(View view) {

        layoutUserModule = (LinearLayout) view.findViewById(R.id.layout_user_module);
        waveHeaderLogin = (MultiWaveHeader) view.findViewById(R.id.wave_header_login);
        avatarImageUser = (AvatarImageView) view.findViewById(R.id.avatar_image_user);
        recyclerMine = (MyRecyclerView) view.findViewById(R.id.recycler_mine);
        btnToSetting = (ImageView) view.findViewById(R.id.btn_to_setting);
        textUserNickname = (TextView) view.findViewById(R.id.text_user_nickname);

        setAvatarImageUser();
        setRecyclerMine(view);
        layoutUserModule.setOnClickListener(this);
        btnToSetting.setOnClickListener(this);
    }

    private void setAvatarImageUser() {
        if (hasLogin()) {//已登录
            String firstChar = String.valueOf("我".charAt(0));
            avatarImageUser.setTextAndColor(firstChar, AvatarImageView.COLORS[0]);
            textUserNickname.setText("匿名");
        } else {//未登录
            String firstChar = String.valueOf("登录");
            avatarImageUser.setTextAndColor(firstChar, AvatarImageView.COLORS[0]);
            textUserNickname.setText("登录");
        }
    }

    private void setRecyclerMine(View view) {
        recyclerMine.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.layout_user_module) {
            if (hasLogin()) {
                ARouter.getInstance()
                        .build(CommonPath.USER_ACTIVITY_PATH)
                        .withTransition(com.lv.common.R.anim.slide_in, com.lv.common.R.anim.slide_out_back)
                        .navigation();
            } else {
                ARouter.getInstance()
                        .build(CommonPath.USER_ACTIVITY_PATH)
                        .withTransition(com.lv.common.R.anim.slide_in, com.lv.common.R.anim.slide_out_back)
                        .navigation();
            }
        } else if (id == R.id.btn_to_setting) {
            ARouter.getInstance()
                    .build(CommonPath.SET_ACTIVITY_PATH)
                    .withTransition(com.lv.common.R.anim.slide_in, com.lv.common.R.anim.slide_out_back)
                    .navigation();
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void checkLogin() {
        setAvatarImageUser();
    }

}
