package com.lv.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetUtils {

    /**
     * 判断是否具有网络连接
     */
    public static final boolean hasNetWorkConnection(Context ctx) {
        // 获取连接活动管理器
        final ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取连接的网络信息
        @SuppressLint("MissingPermission")
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isAvailable());
    }

    /**
     * 对网络连接状态进行判断
     */
    @SuppressWarnings("deprecation")
    public static boolean checkNetWork(Context context) {

        boolean result = false;
        ConnectivityManager mConnectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 检查网络连接，如果无网络可用，就不需要进行连网操作等
        @SuppressLint("MissingPermission")
        NetworkInfo info = mConnectivity.getActiveNetworkInfo();
        if (info == null || !mConnectivity.getBackgroundDataSetting()) {
            result = false;
        } else {
            @SuppressLint("MissingPermission")
            NetworkInfo[] infos = mConnectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < infos.length; i++) {
                    if (infos[i].getState() == NetworkInfo.State.CONNECTED) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 获取网络类型
     *
     * @param context
     * @return
     */
    public static String getNetTypeName(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return "无网络";
            }
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return activeNetworkInfo.getTypeName();
            } else {
                String typeName = activeNetworkInfo.getSubtypeName();
                Log.i("网络名称:", typeName);
                if (typeName == null || typeName.length() == 0) {
                    return "未知网络";
                } else if (typeName.length() > 3) {
                    return activeNetworkInfo.getSubtypeName().substring(0, 4);
                } else {
                    return activeNetworkInfo.getSubtypeName().substring(0, typeName.length());
                }
            }
        } else {
            return "无网络";
        }
    }

    /**
     * 获取ip地址
     *
     * @param context
     * @return
     */
    public static String getIp(Context context) {
        String ip = "";
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return "";
            }
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                ip = getWIFIIp(context);
            } else {
                ip = getMobileIP();
            }
        }
        return ip;
    }

    /**
     * 获取WIFI网络的IP地址
     */
    public static String getWIFIIp(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        // 格式化IP address，例如：格式化前：1828825280，格式化后：192.168.1.109
        String ip = String.format("%d.%d.%d.%d", (ipAddress & 0xff),
                (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff),
                (ipAddress >> 24 & 0xff));
        return ip;
    }

    /**
     * 获取GSM网络的IP地址
     */
    public static String getMobileIP() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = (NetworkInterface) en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ipaddress = inetAddress.getHostAddress().toString();
                        return ipaddress;
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("getMobileIP", "Exception in Get IP Address: " + ex.toString());
        }
        return "";
    }


}
