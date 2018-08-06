package com.lv.common.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.lv.common.R;
import com.lv.common.base.SwipeBackActivity;
import com.tencent.sonic.sdk.SonicSession;

public class WebViewActivity extends SwipeBackActivity {

    private ProgressBar progressBarCommon;
    private WebView webViewCommon;

    private String loadUrl;
    private String title;

    private SonicSession sonicSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initData();
        initUI();
    }

    @Override
    protected void initData() {
        Bundle bundle = this.getIntent().getExtras();
        loadUrl = bundle.getString("loadUrl", "");
        title = bundle.getString("title", "");
    }

    @Override
    protected void initUI() {
        progressBarCommon = (ProgressBar) findViewById(R.id.progress_bar_common);
        webViewCommon = (WebView) findViewById(R.id.web_view_common);
        initToolBar("", true);
    }

    private void setWebViewCommon(){
        
    }

    //----------------------- WebViewClient ----------------------//
    private WebViewClient webViewClient = new WebViewClient(){

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
            if (sonicSession != null) {
                sonicSession.getSessionClient().pageFinish(url);
            }
            if (!view.getSettings().getLoadsImagesAutomatically()) {
                view.getSettings().setLoadsImagesAutomatically(true);
            }
        }

        @Override
        public void onLoadResource(WebView view, String url) {

        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            // TODO Auto-generated method stub
            super.onReceivedError(view, errorCode, description, failingUrl);
            //view.loadUrl("file:///android_asset/day1.html");
        }

        @TargetApi(21)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return shouldInterceptRequest(view, request.getUrl().toString());
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            if (sonicSession != null) {
                //step 6: Call sessionClient.requestResource when host allow the application
                // to return the local data .
                return (WebResourceResponse) sonicSession.getSessionClient().requestResource(url);
            }
            return null;
        }

    };

}
