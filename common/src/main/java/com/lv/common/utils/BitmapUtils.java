package com.lv.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.view.View;
import android.widget.RelativeLayout;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * Created by hasee on 2017/10/10 0010.
 */

public class BitmapUtils {

    public static Bitmap getNetImage(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream is = conn.getInputStream();
        return BitmapFactory.decodeStream(is);
    }

    public static Bitmap getDrawableImage(Context context, int drawableId) throws Exception {
        return BitmapFactory.decodeResource(context.getResources(), drawableId);
    }


    private Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap, int marginLeft, int marginTop) {
        Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth(), firstBitmap.getHeight(), firstBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(firstBitmap, new Matrix(), null);
        canvas.drawBitmap(secondBitmap, 0, 0, null);
        return bitmap;
    }

    public static byte[] getBitmapByte(Bitmap bitmap) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    public static Bitmap mergebitmap(Context context, Bitmap backBitmap, Bitmap frontBitmap, double virtalBorderWidth) {
        //设置想要的大小
        double newWidth = frontBitmap.getWidth() + virtalBorderWidth  * 2;
        double newHeight = frontBitmap.getHeight() + virtalBorderWidth  * 2;

        //计算缩放比例
        float scaleWidth = ((float) newWidth) / backBitmap.getWidth();
        float scaleHeight = ((float) newHeight) / backBitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap bitmap3 = Bitmap.createBitmap(backBitmap, 0, 0, backBitmap.getWidth(), backBitmap.getHeight(), matrix, true);
        Bitmap scaleFrontBitmap = Bitmap.createBitmap(frontBitmap, 0, 0, frontBitmap.getWidth(), frontBitmap.getHeight(), new Matrix(), true);
        Canvas canvas = new Canvas(bitmap3);
        canvas.drawBitmap(bitmap3, new Matrix(), null);
        canvas.drawBitmap(scaleFrontBitmap, (float) virtalBorderWidth, (float)virtalBorderWidth, null);
        return bitmap3;
    }

    /**
     * 设置不验证主机
     */
    private static final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    /**
     * Get image from newwork 网络获取图片 用于转换bitmap对象
     *
     * @param path The path of image
     * @return InputStream
     * @throws Exception
     */
    public static InputStream getImageStream(String path) throws Exception {
        boolean useHttps = path.startsWith("https");
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if (useHttps) {
            HttpsURLConnection https = (HttpsURLConnection) conn;
            https.setHostnameVerifier(DO_NOT_VERIFY);
        }
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return conn.getInputStream();
        } else {
        }
        return null;
    }

    // 获取指定Activity的截屏，保存到png文件
    public static Bitmap getScreenShot(RelativeLayout waterPhoto, Context context) {
        // View是你需要截图的View
        View view = waterPhoto;
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        // 获取长和高
        int width = view.getWidth();
        int height = view.getHeight();
        // 去掉标题栏
        // Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
        Bitmap b = Bitmap.createBitmap(b1, 0, CompatUtils.dip2px(context, 73), width, height - CompatUtils.dip2px(context, 73));
        view.destroyDrawingCache();
        return b;
    }

    /**
     * 将bitmap写到指定路径
     *
     * @param filePath
     * @param bitmap
     * @throws IOException
     */
    public static void writeBitmap(String filePath, Bitmap bitmap)
            throws IOException {
        File file = new File(filePath);
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(file));
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);// 将图片压缩到流中
        bos.flush();// 输出
        bos.close();// 关闭
        bitmap.recycle();
        bitmap = null;
    }


    public static Bitmap mergebitmaptest(Context context, Bitmap backBitmap, Bitmap frontBitmap) {
        //设置想要的大小
        int newWidth = frontBitmap.getWidth() + 15 * 2;
        int newHeight = frontBitmap.getHeight() + 15 * 2;

        //计算缩放比例
        float scaleWidth = ((float) newWidth) / backBitmap.getWidth();
        float scaleHeight = ((float) newHeight) / backBitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap bitmap3 = Bitmap.createBitmap(backBitmap, 0, 0, backBitmap.getWidth(), backBitmap.getHeight(), matrix, true);
        Bitmap scaleFrontBitmap = Bitmap.createBitmap(frontBitmap, 0, 0, frontBitmap.getWidth(), frontBitmap.getHeight(), new Matrix(), true);
        Canvas canvas = new Canvas(bitmap3);
        canvas.drawBitmap(bitmap3, new Matrix(), null);
        canvas.drawBitmap(scaleFrontBitmap, 15, 15, null);
        return bitmap3;
    }

    /**
     * 旋转图片90度
     *
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, String path, int degree) {
        Bitmap returnBm = null;

        // 得到图片的旋转角度
//        int degree = getBitmapDegree(path);
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        // LogUtil.log("iamgeStr1", Log.ERROR, imageStr1);
        return returnBm;
    }

    /**
     * 获取原始图片的角度（解决三星手机拍照后图片是横着的问题）
     * @param path 图片的绝对路径
     * @return 原始图片的角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

}
