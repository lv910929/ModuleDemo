package com.lv.main.ui;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lv.common.base.SwipeBackActivity;
import com.lv.common.utils.FileUtils;
import com.lv.common.webview.MyWebChromeClient;
import com.lv.main.R;
import com.malinskiy.materialicons.IconDrawable;
import com.malinskiy.materialicons.Iconify;
import com.tencent.bugly.crashreport.BuglyLog;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;

@Route(path = "/main/WebViewActivity")
public class WebViewActivity extends SwipeBackActivity implements MyWebChromeClient.OpenFileChooserCallBack, MyWebChromeClient.LollipopFileCallBack, MyWebChromeClient.ProgressChangeCallBack {

    private ProgressBar progressBarCommon;
    private WebView webViewCommon;

    @Autowired(name = "loadUrl")
    String loadUrl;
    @Autowired(name = "title")
    String title;

    private MyWebChromeClient myWebChromeClient;

    public static final int REQUEST_CAMERA = 1;
    public static final int REQUEST_CHOOSE = 2;

    ValueCallback<Uri> mUploadMessage;
    ValueCallback<Uri[]> mUploadMessagesAboveL;
    private Uri cameraUri;

    //内容布局
    protected RelativeLayout layoutContent;
    //出错布局
    protected LinearLayout layoutError;
    protected ImageView imageErrorItem;
    protected TextView textErrorItem;
    protected Button buttonRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);//添加在onCreate（）
        setContentView(R.layout.activity_web_view);
        initUI();
    }

    @Override
    protected void initUI() {
        progressBarCommon = (ProgressBar) findViewById(R.id.progress_bar_common);
        webViewCommon = (WebView) findViewById(R.id.web_view_common);

        initStateLayout();
        initToolBar(title, true);
        setWebViewCommon();
        showContentLayout();
        webViewCommon.loadUrl(loadUrl);
        // 增加Javascript异常监控
        CrashReport.setJavascriptMonitor(webViewCommon, false);
    }

    protected void initStateLayout() {
        //内容布局
        layoutContent = (RelativeLayout) findViewById(com.lv.common.R.id.layout_content);
        //出错布局
        layoutError = (LinearLayout) findViewById(com.lv.common.R.id.layout_error);
        imageErrorItem = (ImageView) findViewById(com.lv.common.R.id.image_error_item);
        textErrorItem = (TextView) findViewById(com.lv.common.R.id.text_error_item);
        buttonRetry = (Button) findViewById(com.lv.common.R.id.button_retry);
        setErrorLayout(getString(com.lv.common.R.string.error_data_hint));
    }

    /**
     * 设置出错布局
     */
    protected void setErrorLayout(String message) {
        Drawable errorDrawable = new IconDrawable(WebViewActivity.this, Iconify.IconValue.zmdi_wifi_off).colorRes(com.lv.common.R.color.grey300);
        imageErrorItem.setImageDrawable(errorDrawable);
        textErrorItem.setText(message);
        buttonRetry.setOnClickListener(mRetryListener);
    }

    private View.OnClickListener mRetryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showContentLayout();
            webViewCommon.loadUrl(loadUrl);
            // 增加Javascript异常监控
            CrashReport.setJavascriptMonitor(webViewCommon, false);
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

    private void setWebViewCommon() {
        myWebChromeClient = new MyWebChromeClient(WebViewActivity.this, WebViewActivity.this, WebViewActivity.this);
        WebSettings webSettings = webViewCommon.getSettings();

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        //设置加载进来的页面自适应手机屏幕
//        webSettings.setUseWideViewPort(true);
//        webSettings.setLoadWithOverviewMode(true);
        // 设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置可以访问文件
        webSettings.setAllowFileAccess(true);
        // 设置支持缩放
        webSettings.setBuiltInZoomControls(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 启用数据库
        webSettings.setDatabaseEnabled(true);
        String dir = this.getApplicationContext().getDir("database", MODE_PRIVATE).getPath();
        // 启用地理定位
        webSettings.setGeolocationEnabled(true);
        // 设置定位的数据库路径
        webSettings.setGeolocationDatabasePath(dir);
        webSettings.setDomStorageEnabled(true);
        webViewCommon.setWebViewClient(webViewClient);
        webViewCommon.setWebChromeClient(myWebChromeClient);
        webViewCommon.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        if (Build.VERSION.SDK_INT >= 19) {
            webViewCommon.getSettings().setLoadsImagesAutomatically(true);
        } else {
            webViewCommon.getSettings().setLoadsImagesAutomatically(false);
        }
        webViewCommon.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }


    @Override
    public void lollipopFileCallBack(ValueCallback<Uri[]> filePathCallback) {
        if (mUploadMessagesAboveL != null) {
            mUploadMessagesAboveL.onReceiveValue(null);
        } else {
            mUploadMessagesAboveL = filePathCallback;
            selectImage();
        }
    }

    @Override
    public void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType) {
        if (mUploadMessage != null) return;
        mUploadMessage = uploadMsg;
        selectImage();
    }

    private void selectImage() {
        if (!FileUtils.checkSDCard(this)) {
            return;
        }
        String[] selectPicTypeStr = {"拍照", "图库"};
        new AlertDialog.Builder(this)
                .setOnCancelListener(new ReOnCancelListener())
                .setItems(selectPicTypeStr,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0: // 相机拍摄
                                        openCamera();
                                        break;
                                    case 1:// 手机相册
                                        chosePicture();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }).show();
    }

    /**
     * 本地相册选择图片
     */
    private void chosePicture() {
        Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        innerIntent.setType("image/*");
        Intent wrapperIntent = Intent.createChooser(innerIntent, null);
        startActivityForResult(wrapperIntent, REQUEST_CHOOSE);
    }

    /**
     * 选择照片后结束
     */
    private Uri afterChosePic(Intent data) {
        if (data != null) {
            final String path = data.getData().getPath();
            if (path != null && (path.endsWith(".png") || path.endsWith(".PNG") || path.endsWith(".jpg") || path.endsWith(".JPG"))) {
                return data.getData();
            } else {
                Toast.makeText(this, "上传的图片仅支持png或jpg格式", Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }

    /**
     * 打开照相机
     */
    private void openCamera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String imagePaths = Environment.getExternalStorageDirectory().getPath() + "/Module/Images/" + (System.currentTimeMillis() + ".jpg");
        // 必须确保文件夹路径存在，否则拍照后无法完成回调
        File vFile = new File(imagePaths);
        if (!vFile.exists()) {
            File vDirPath = vFile.getParentFile();
            vDirPath.mkdirs();
        } else {
            if (vFile.exists()) {
                vFile.delete();
            }
        }
        cameraUri = Uri.fromFile(vFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) {
            return;
        }
        if (mUploadMessagesAboveL != null) {
            onActivityResultAboveL(requestCode, resultCode, data);
        }
        if (mUploadMessage == null) return;
        Uri uri = null;
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            cameraUri = data.getData();
            uri = cameraUri;
        }
        if (requestCode == REQUEST_CHOOSE && resultCode == RESULT_OK) {
            uri = afterChosePic(data);
        }
        mUploadMessage.onReceiveValue(uri);
        mUploadMessage = null;
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 5.0以后机型 返回文件选择
     */
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {

        Uri[] results = null;
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            results = new Uri[]{cameraUri};
        }
        if (requestCode == REQUEST_CHOOSE && resultCode == RESULT_OK) {
            if (data != null) {
                String dataString = data.getDataString();
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        mUploadMessagesAboveL.onReceiveValue(results);
        mUploadMessagesAboveL = null;
        return;
    }

    @Override
    public void onProgressChange(WebView view, int newProgress) {
        if (newProgress == 100) {
            progressBarCommon.setVisibility(View.GONE);
        } else {
            progressBarCommon.setVisibility(View.VISIBLE);
            progressBarCommon.setProgress(newProgress);
        }
    }

    /**
     * dialog监听类
     */
    private class ReOnCancelListener implements DialogInterface.OnCancelListener {
        @Override
        public void onCancel(DialogInterface dialogInterface) {
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(null);
                mUploadMessage = null;
            }

            if (mUploadMessagesAboveL != null) {
                mUploadMessagesAboveL.onReceiveValue(null);
                mUploadMessagesAboveL = null;
            }
        }
    }

    /**
     * 自定义WebViewClient
     */
    private WebViewClient webViewClient = new WebViewClient() {

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
            showErrorLayout(getString(R.string.error_data_hint));
        }

        //处理网页加载失败时
        //6.0以上执行
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);

            BuglyLog.i("WebView", "onReceivedError: ");
            showErrorLayout(getString(R.string.error_data_hint));
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webViewCommon.canGoBack()) {
            webViewCommon.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (webViewCommon != null) {
            webViewCommon.removeAllViews();
            webViewCommon.destroy();
        }
        super.onDestroy();
    }

}
