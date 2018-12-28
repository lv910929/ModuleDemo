package com.lv.main.ui;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.lv.common.base.BasePresenter;
import com.lv.common.data.CommonPath;
import com.lv.common.http.CommConstant;
import com.lv.main.R;
import com.lv.main.ui.base.BaseHomeFragment;

/**
 * 首页
 */
public class HomeFragment extends BaseHomeFragment<BasePresenter> implements View.OnClickListener {

    private RelativeLayout layoutSearch;
    private ImageView btnToScan;
    private EasyRecyclerView recyclerHome;

    public HomeFragment() {
    }

    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }

    @Override
    protected void initUI(View view) {
        layoutSearch = (RelativeLayout) view.findViewById(R.id.layout_search);
        btnToScan = (ImageView) view.findViewById(R.id.btn_to_scan);
        recyclerHome = (EasyRecyclerView) view.findViewById(R.id.recycler_home);
        setRecyclerHome(view);
        layoutSearch.setOnClickListener(this);
        recyclerHome.setRefreshListener(this);
    }

    private void setRecyclerHome(View view) {
        setSwipeRefreshLayout(recyclerHome);
        recyclerHome.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.layout_search) {
            ARouter.getInstance()
                    .build(CommonPath.WEB_ACTIVITY_PATH)
                    .withString("title", "测试")
                    .withString("loadUrl", CommConstant.WEB_URL)
                    .withTransition(com.lv.common.R.anim.slide_in, com.lv.common.R.anim.slide_out_back)
                    .navigation();
        } else if (id == R.id.btn_to_scan) {

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

    }
}
