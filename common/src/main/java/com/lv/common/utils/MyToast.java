package com.lv.common.utils;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lv.common.R;
import com.swifty.toptoastbar.BottomToast;
import com.swifty.toptoastbar.TopToast;

/**
 * ToastUtils
 */
public class MyToast {

    private static Toast mToast = null;

    public static void showShortToast(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void showBottomToast(Activity activity, int res, String text) {
        BottomToast.make((ViewGroup) activity.findViewById(res), text, 1000)
                .setBackground(activity.getResources().getColor(R.color.colorAccent))
                .show();
    }

    public static void showTopToast(Activity activity, int res, String text) {
        TopToast.make((ViewGroup) activity.findViewById(res), text, 1000)
                .setBackground(activity.getResources().getColor(R.color.colorAccent))
                .show();
    }

}
