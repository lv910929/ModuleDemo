package com.lv.common.base;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lv.common.R;
import com.lv.common.http.HttpConstant;
import com.lv.common.webview.MyWebChromeClient;
import com.lv.common.webview.SonicSessionClientImpl;
import com.malinskiy.materialicons.IconDrawable;
import com.malinskiy.materialicons.Iconify;
import com.tencent.bugly.crashreport.BuglyLog;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;


public abstract class BaseWebMvpFragment<P extends BasePresenter> extends BaseFragment implements MyWebChromeClient.LollipopFileCallBack, MyWebChromeClient.OpenFileChooserCallBack, MyWebChromeClient.ProgressChangeCallBack {

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

    //内容布局
    protected RelativeLayout layoutContent;
    //出错布局
    protected LinearLayout layoutError;
    protected ImageView imageErrorItem;
    protected TextView textErrorItem;
    protected Button buttonRetry;

    protected void initStateLayout(View view) {
        //内容布局
        layoutContent = (RelativeLayout) view.findViewById(R.id.layout_content);
        //出错布局
        layoutError = (LinearLayout) view.findViewById(R.id.layout_error);
        imageErrorItem = (ImageView) view.findViewById(R.id.image_error_item);
        textErrorItem = (TextView) view.findViewById(R.id.text_error_item);
        buttonRetry = (Button) view.findViewById(R.id.button_retry);
        setErrorLayout(getString(R.string.error_data_hint));
    }

    /**
     * 设置出错布局
     */
    protected void setErrorLayout(String message) {
        Drawable errorDrawable = new IconDrawable(getActivity(), Iconify.IconValue.zmdi_wifi_off).colorRes(R.color.grey300);
        imageErrorItem.setImageDrawable(errorDrawable);
        textErrorItem.setText(message);
        buttonRetry.setOnClickListener(mRetryListener);
    }

    private View.OnClickListener mRetryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showContentLayout();
            reload();
        }
    };

    //显示内容布局
    public void showContentLayout() {
        layoutContent.setVisibility(View.VISIBLE);
        layoutError.setVisibility(View.GONE);
    }

    //显示出错布局
    public void showErrorLayout(String message) {
        layoutError.setVisibility(View.VISIBLE);
        layoutContent.setVisibility(View.GONE);
        setErrorLayout(message);
    }

    //重新加载
    protected abstract void reload();

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


    //------------------ WebView ---------------//

    public static final int REQUEST_CAMERA = 1;
    public static final int REQUEST_CHOOSE = 2;

    protected ValueCallback<Uri> mUploadMessage;
    protected ValueCallback<Uri[]> mUploadMessagesAboveL;
    protected Uri cameraUri;

    protected MyWebChromeClient myWebChromeClient = new MyWebChromeClient(this, this, this);

    protected WebViewClient webViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null && url.startsWith("mailto:")
                    || url.startsWith("geo:") || url.startsWith("tel:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            Log.d("webview", "正在加载......");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            if (!view.getSettings().getLoadsImagesAutomatically()) {
                view.getSettings().setLoadsImagesAutomatically(true);
            }
        }

        @Override
        public void onLoadResource(WebView view, String url) {

        }

        //6.0以下执行
        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            // TODO Auto-generated method stub
            super.onReceivedError(view, errorCode, description, failingUrl);
            showErrorLayout(getActivity().getString(R.string.error_data_hint));
        }

        //处理网页加载失败时
        //6.0以上执行
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);

            BuglyLog.i("WebView", "onReceivedError: ");
            showErrorLayout(getActivity().getString(R.string.error_data_hint));
        }


        @TargetApi(21)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return shouldInterceptRequest(view, request.getUrl().toString());
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            return null;
        }

    };

    protected void setWebView(WebView webView, String loadUrl) {
        WebSettings webSettings = webView.getSettings();

        // step 4: bind javascript
        // note:if api level lower than 17(android 4.2), addJavascriptInterface has security
        // issue, please use x5 or see https://developer.android.com/reference/android/webkit/
        // WebView.html#addJavascriptInterface(java.lang.Object, java.lang.String)
        webSettings.setJavaScriptEnabled(true);
        webView.removeJavascriptInterface("searchBoxJavaBridge_");

        // init webview settings
        webSettings.setAllowContentAccess(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        if (Build.VERSION.SDK_INT >= 19) {
            webView.getSettings().setLoadsImagesAutomatically(true);
        } else {
            webView.getSettings().setLoadsImagesAutomatically(false);
        }
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        webView.loadUrl(loadUrl);
        // 增加Javascript异常监控
        CrashReport.setJavascriptMonitor(webView, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
