package com.lv.common.utils;

import java.security.MessageDigest;

public class MD5 {

    private MD5() {
    }

    public final static String getMessageDigest(byte[] buffer) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    // 通用方法 计算MD5
    public static String encodeWithMD5(final String input) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            result = byte2hex(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * byte to string
     *
     * @param digest
     * @return
     */
    private static String byte2hex(final byte[] digest) {
        String des = "";
        String tmp = null;
        for (final byte element : digest) {
            tmp = (Integer.toHexString(element & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

}
