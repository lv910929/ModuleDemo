package com.lv.common.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Lv on 2016/4/7.
 */
public class StringUtils {

    //全案造价转换成带一个小数点的string
    public static String budgetToString(long price) {
        float budget = (float) (price / 10000.000);//以万为单位
        return floatToString(budget);
    }

    public static String floatToString(float rate) {
        //构造方法的字符格式这里如果小数不足1位,会以0补足.
        java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("#.0");
        String newRate = decimalFormat.format(rate);
        if (newRate.startsWith(".")) {
            newRate = "0" + newRate;
        }
        return newRate;
    }

    //含有"&"的字符串解析成map
    public static Map<String, String> stringToMap(String string) {
        Map<String, String> parsedMap = new HashMap<>();
        if (!TextUtils.isEmpty(string) && string.contains("&")) {
            String[] fStrings = string.split("&");
            for (int i = 0; i < fStrings.length; i++) {
                if (!TextUtils.isEmpty(fStrings[i]) && fStrings[i].contains("=")) {
                    String[] cStrings = fStrings[i].split("=");
                    parsedMap.put(cStrings[0], cStrings[1]);
                }
            }
        }
        return parsedMap;
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isSpace(String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 将List按照指定大小分段
     *
     * @param list
     * @param pageSize
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> list, int pageSize) {
        List<List<T>> listArray = new ArrayList<List<T>>();
        List<T> subList = null;
        for (int i = 0; i < list.size(); i++) {
            if (i % pageSize == 0) {//每次到达页大小的边界就重新申请一个subList
                subList = new ArrayList<T>();
                listArray.add(subList);
            }
            subList.add(list.get(i));
        }
        return listArray;
    }

    //-----------------过滤博客中 html标签 只取文字-----------------------------------

    /**
     * 定义script的正则表达式
     */
    private static final String REGEX_SCRIPT = "<script[^>]*?>[\\s\\S]*?<\\/script>";
    /**
     * 定义style的正则表达式
     */
    private static final String REGEX_STYLE = "<style[^>]*?>[\\s\\S]*?<\\/style>";
    /**
     * 定义HTML标签的正则表达式
     */
    private static final String REGEX_HTML = "<[^>]+>";
    /**
     * 定义空格回车换行符
     */
    private static final String REGEX_SPACE = "\\s*|\t|\r|\n";

    public static String delHTMLTag(String htmlStr) {
        // 过滤script标签
        Pattern p_script = Pattern.compile(REGEX_SCRIPT, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");
        // 过滤style标签
        Pattern p_style = Pattern.compile(REGEX_STYLE, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");
        // 过滤html标签
        Pattern p_html = Pattern.compile(REGEX_HTML, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");
        // 过滤空格回车标签
        Pattern p_space = Pattern.compile(REGEX_SPACE, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll("");
        return htmlStr.trim(); // 返回文本字符串
    }

    /**
     * 加密手机号码
     */
    public static String secretPhoneString(String phone) {
        String secretPhone = "";
        secretPhone = phone.substring(0, 3) + "****" + phone.substring(7, 11);
        return secretPhone;
    }

}

