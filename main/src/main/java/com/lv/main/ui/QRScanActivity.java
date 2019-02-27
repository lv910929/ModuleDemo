package com.lv.main.ui;

import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lv.common.base.BasePresenter;
import com.lv.common.base.SwipeBackMvpActivity;
import com.lv.common.data.CommonPath;
import com.lv.common.utils.MyToast;
import com.lv.main.R;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

@Route(path = CommonPath.SCAN_ACTIVITY_PATH)
public class QRScanActivity extends SwipeBackMvpActivity<BasePresenter> implements QRCodeView.Delegate{

    private ZXingView viewQrScan;

    //用于判断闪关灯是否打开
    private boolean isFlashOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan);
        initUI();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initUI() {
        viewQrScan = (ZXingView) findViewById(R.id.view_qr_scan);
        initToolBar("扫一扫", true);
        viewQrScan.setDelegate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewQrScan.startCamera();
        viewQrScan.startSpotDelay(1000);
    }

    //震动
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_qrscan, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.nav_flash) {
            if (isFlashOpen) {//说明闪光灯开着
                viewQrScan.closeFlashlight();
                item.setIcon(R.drawable.flash_on_icon);
                isFlashOpen = false;
            } else {
                viewQrScan.openFlashlight();
                item.setIcon(R.drawable.flash_off_icon);
                isFlashOpen = true;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onScanQRCodeSuccess(final String result) {
        vibrate();
        viewQrScan.stopSpot();
        MyToast.showShortToast(QRScanActivity.this, result);
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e("QRScan","打开相机错误");
    }

    @Override
    protected void onStop() {
        viewQrScan.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        viewQrScan.onDestroy();
        super.onDestroy();
    }

}
