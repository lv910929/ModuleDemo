package com.lv.main.ui;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lv.common.base.BaseFragment;
import com.lv.common.base.BasePresenter;
import com.lv.common.base.BaseWebMvpFragment;
import com.lv.common.data.CommConstant;
import com.lv.common.webview.SonicSessionClientImpl;
import com.lv.main.R;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;

/**
 * 发现
 */
public class DiscoverFragment extends BaseWebMvpFragment<BasePresenter> {

    private TextView textTitle;
    private ProgressBar progressWebView;
    private WebView webViewDiscover;

    public DiscoverFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setSonicSession(CommConstant.WEB_URL);
        initUI(view);
        setWebViewDiscover();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initUI(View view) {
        textTitle = (TextView) view.findViewById(R.id.text_title);
        progressWebView = (ProgressBar) view.findViewById(R.id.progress_web_view);
        webViewDiscover = (WebView) view.findViewById(R.id.web_view_discover);
        textTitle.setText("发现");
    }

    private void setWebViewDiscover() {
        webViewDiscover.setWebViewClient(webViewClient);
        setWebView(webViewDiscover, CommConstant.WEB_URL);
    }


    @Override
    public void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType) {

    }

    @Override
    public void lollipopFileCallBack(ValueCallback<Uri[]> filePathCallback) {

    }

    @Override
    public void onProgressChange(WebView view, int newProgress) {

    }

}
