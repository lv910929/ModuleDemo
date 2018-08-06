package com.lv.common.webview;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;

import com.lv.common.R;
import com.lv.common.utils.IntentUtils;
import com.lv.common.utils.UrlParseUtil;
import java.util.Map;

/**
 * Created by Lv on 2016/4/3.
 */
public class JavaScriptObject {

    Context mContext;

    public JavaScriptObject(Context mContext) {
        this.mContext = mContext;
    }

    //跳转到下一个webview
    @JavascriptInterface
    public void pushcontroller(String urlString) {
        String title = "";
        String loadUrl = "";
        Map<String, String> urlMap = UrlParseUtil.parseUrl(urlString);
        title = urlMap.get("title");
        loadUrl = UrlParseUtil.spliceUrl(urlMap);
        IntentUtils.redirectWebView(mContext, title, loadUrl);
    }

    //退回我的界面
    @JavascriptInterface
    public void popcontroller() {
        ((Activity) mContext).finish();
        ((Activity) mContext).overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }

}
