package com.lv.common.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtils {
    /**
     * 通过年份和月份 得到当月的日子
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthDays(int year, int month) {
        month++;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return -1;
        }
    }

    /**
     * 返回当前月份1号位于周几
     *
     * @param year  年份
     * @param month 月份，传入系统获取的，不需要正常的
     * @return 日：1		一：2		二：3		三：4		四：5		五：6		六：7
     */
    public static int getFirstDayWeek(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 根据列明获取周
     *
     * @param column
     * @return
     */
    public static String getWeekName(int column) {
        switch (column) {
            case 0:
                return "周日";
            case 1:
                return "周一";
            case 2:
                return "周二";
            case 3:
                return "周三";
            case 4:
                return "周四";
            case 5:
                return "周五";
            case 6:
                return "周六";
            default:
                return "";
        }
    }

    //把日期转为字符串
    public static String ConverToString(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        return df.format(date);
    }

    //把日期转为字符串
    public static String ConverToString2(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    //带时 分；
    public static String ConverToStringtoss(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return df.format(date);
    }

    //把字符串转为日期
    public static Date ConverToDate(String strDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date StringToDate(String strDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String DateToString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

    //字符串转为Timestamp
    public static long StringToLong(String strDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeLong = 0;
        try {
            Date date = df.parse(strDate);
            timeLong = date.getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeLong;
    }

    public static Long dayStringToTimeStamp(String strDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Long timeLong = null;
        try {
            Date date = df.parse(strDate);
            timeLong = date.getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeLong;
    }

    public static long minuteToTimeStamp(String strDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long timeLong = 0;
        try {
            Date date = df.parse(strDate);
            timeLong = date.getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeLong;
    }

    //字符串转为Timestamp
    public static long StringToTimeStamp(String strDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeLong = 0;
        try {
            Date date = df.parse(strDate);
            String time = df.format(date);
            Timestamp timestamp = Timestamp.valueOf(strDate);
            timeLong = timestamp.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeLong;
    }

    public static String timestampToString2(long timestamp) {
        Timestamp ts = new Timestamp(timestamp * 1000);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//定义格式，不显示毫秒
        String str = df.format(ts);
        return str;
    }

    public static String timestampToString3(long timestamp) {
        Timestamp ts = new Timestamp(timestamp);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//定义格式，不显示毫秒
        String str = df.format(ts);
        return str;
    }

    public static String timestampToStringSecond(long timestamp) {
        Timestamp ts = new Timestamp(timestamp);
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");//定义格式，不显示毫秒
        String str = df.format(ts);
        return str;
    }

    public static String timestampToStringSecondNoYear(long timestamp) {
        Timestamp ts = new Timestamp(timestamp);
        SimpleDateFormat df = new SimpleDateFormat("MM月dd日HH时mm分ss秒");//定义格式，不显示毫秒
        String str = df.format(ts);
        return str;
    }

    /**
     * 精确到天
     *
     * @param timestamp
     * @return
     */
    public static String longTimeToDay(long timestamp) {
        Timestamp ts = new Timestamp(timestamp * 1000);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//定义格式，不显示毫秒
        String str = df.format(ts);
        return str;
    }

    /**
     * 精确到分
     *
     * @param timestamp
     * @return
     */
    public static String timeStampToMinute(long timestamp) {
        Timestamp ts = new Timestamp(timestamp);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//定义格式，不显示毫秒
        String str = df.format(ts);
        return str;
    }

    /**
     * 精确到秒
     *
     * @param timestamp
     * @return
     */
    public static String timeStampToSecond(long timestamp) {
        Timestamp ts = new Timestamp(timestamp * 1000);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
        String str = df.format(ts);
        return str;
    }

    //Timestamp转date
    public static Map<Integer, Integer> timestampToDate(long timestamp) {
        Map<Integer, Integer> dateMap = new HashMap<>();
        Timestamp ts = new Timestamp(timestamp * 1000);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//定义格式，不显示毫秒
        String str = df.format(ts);
        String[] dateStrings = str.split("-");
        dateMap.put(0, Integer.parseInt(dateStrings[0]));
        dateMap.put(1, Integer.parseInt(dateStrings[1]));
        dateMap.put(2, Integer.parseInt(dateStrings[2]));
        return dateMap;
    }

    public static String getInstanceDate() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return df.format(date);
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     */
    public static int differentDaysByMillisecond(long startTimestamp, long endTimestamp) {
        int days = (int) ((endTimestamp - startTimestamp) / (1000 * 3600 * 24));
        return days;
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     */
    public static int differentHoursByMillisecond(long startTimestamp, long endTimestamp) {
        int days = (int) ((endTimestamp - startTimestamp) / (1000 * 3600));
        return days;
    }

    /**
     * 当天时间
     */
    public static Date getTodayDate() {
        Date date = new Date();
        String dateString = ConverToString2(date);
        Date nowDate = StringToDate(dateString);
        return nowDate;
    }

    /**
     * 当天时间
     *
     * @return
     */
    public static long getInstanceTime() {
        Date date = new Date();
        String nowTime = DateToString(date);
        return StringToLong(nowTime);
    }

    /**
     * 一周前
     */
    public static long getWeekTime() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -7);
        date = calendar.getTime();
        String weekDate = DateToString(date);
        return StringToLong(weekDate);
    }

    /**
     * 一月前
     */
    public static long getMonthTime() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -30);
        date = calendar.getTime();
        String monthTime = DateToString(date);
        return StringToLong(monthTime);
    }

    /**
     * 三月前
     */
    public static long getThreeMonthTime() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -90);
        date = calendar.getTime();
        String monthTime = DateToString(date);
        return StringToLong(monthTime);
    }

    public static long getLongTimeSecond(String time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date = null;
        Date sDate = null;
        try {
            date = format.parse(time);
            sDate = format.parse("00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (date.getTime() - sDate.getTime()) / 1000;
    }

    public static long getLongTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date = null;
        Date sDate = null;
        try {
            date = format.parse(time);
            sDate = format.parse("00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (date.getTime() - sDate.getTime());
    }

    /**
     * 获取时间戳
     *
     * @param dateType 1:本周 2:本月 3:本季度 4:本年度 5:今天 6:昨天 7:上周 8:下周 9:上月 10:前天 11：明天 12:下月
     * @return 返回一个数组 0号位为开始时间  1号位为结束时间
     */
    public static ArrayList<Long> obtainTimeStamp(Integer dateType) {
        Long estimateStartTime = null;
        Long estimateEndTime = null;
        ArrayList<Long> timeArray = new ArrayList<Long>();
        if (dateType != null) {
            if (1 == dateType) {//本周
                Calendar date1 = Calendar.getInstance();
                date1.set(Calendar.HOUR_OF_DAY, 00);    //当天的时,为0时
                date1.set(Calendar.MINUTE, 0);            //当天的分,为0分
                date1.set(Calendar.SECOND, 0);            //当天的秒,为0秒
                int[] weekDays = {6, 0, 1, 2, 3, 4, 5};
                int num = weekDays[date1.get(Calendar.DAY_OF_WEEK) - 1];
                date1.add(Calendar.DAY_OF_MONTH, -num);    //本周周一日期
                estimateStartTime = date1.getTime().getTime() / 1000;
                date1.add(Calendar.DAY_OF_MONTH, 6);    //本周周日日期
                date1.set(Calendar.HOUR_OF_DAY, 23);    //周日的23：59：59
                date1.set(Calendar.MINUTE, 59);
                date1.set(Calendar.SECOND, 59);
                estimateEndTime = date1.getTime().getTime() / 1000;
            } else if (2 == dateType) {//本月
                Calendar date1 = Calendar.getInstance();
                date1.set(Calendar.DATE, 1);                //设为当前月的1号
                date1.set(Calendar.HOUR_OF_DAY, 00);        //当天的时,为0时
                date1.set(Calendar.MINUTE, 0);            //当天的分,为0分
                date1.set(Calendar.SECOND, 0);            //当天的秒,为0秒
                estimateStartTime = date1.getTime().getTime() / 1000;
                Calendar date2 = Calendar.getInstance();
                date2.set(Calendar.DATE, 1);                //设为当前月的1号
                date2.add(Calendar.MONTH, 1);            //加一个月，变为下月的1号
                date2.add(Calendar.DATE, -1);            //减去一天，变为当月最后一天
                date2.set(Calendar.HOUR_OF_DAY, 23);     //当天的时,为23时
                date2.set(Calendar.MINUTE, 59);          //当天的分,为59分
                date2.set(Calendar.SECOND, 59);            //当天的秒,为59秒
                estimateEndTime = date2.getTime().getTime() / 1000;
            } else if (3 == dateType) {//本季
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                int month = cal1.get(Calendar.MONTH) + 1;
                if (month >= 1 && month <= 3) {
                    cal1.set(Calendar.MONTH, 0);
                    cal2.set(Calendar.MONTH, 2);
                    cal2.set(Calendar.DATE, 31);
                } else if (month >= 4 && month <= 6) {
                    cal1.set(Calendar.MONTH, 3);
                    cal2.set(Calendar.MONTH, 5);
                    cal2.set(Calendar.DATE, 30);
                } else if (month >= 7 && month <= 9) {
                    cal1.set(Calendar.MONTH, 6);
                    cal2.set(Calendar.MONTH, 8);
                    cal2.set(Calendar.DATE, 30);
                } else if (month >= 10 && month <= 12) {
                    cal1.set(Calendar.MONTH, 9);
                    cal2.set(Calendar.MONTH, 11);
                    cal2.set(Calendar.DATE, 31);
                }
                cal1.set(Calendar.HOUR_OF_DAY, 00);    //当天的时,为0时
                cal1.set(Calendar.MINUTE, 0);            //当天的分,为0分
                cal1.set(Calendar.SECOND, 0);            //当天的秒,为0秒
                cal1.set(Calendar.DATE, 1);
                cal2.set(Calendar.HOUR_OF_DAY, 23);     //当天的时,为23时
                cal2.set(Calendar.MINUTE, 59);          //当天的分,为59分
                cal2.set(Calendar.SECOND, 59);            //当天的秒,为59秒
                estimateStartTime = cal1.getTime().getTime() / 1000;
                estimateEndTime = cal2.getTime().getTime() / 1000;
            } else if (4 == dateType) {//本年
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.MONTH, 0);
                cal.set(Calendar.DATE, 1);
                cal.set(Calendar.HOUR_OF_DAY, 00);        //当天的时,为0时
                cal.set(Calendar.MINUTE, 0);            //当天的分,为0分
                cal.set(Calendar.SECOND, 0);            //当天的秒,为0秒
                estimateStartTime = cal.getTime().getTime() / 1000;
                cal.set(Calendar.MONTH, 11);
                cal.set(Calendar.DATE, 31);
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
                estimateEndTime = cal.getTime().getTime() / 1000;
            } else if (5 == dateType) {//今日
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, 00);        //当天的时,为0时
                cal.set(Calendar.MINUTE, 0);            //当天的分,为0分
                cal.set(Calendar.SECOND, 0);            //当天的秒,为0秒
                estimateStartTime = cal.getTime().getTime() / 1000;
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
                estimateEndTime = cal.getTime().getTime() / 1000;
            } else if (6 == dateType) {//昨日
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -1);
                cal.set(Calendar.HOUR_OF_DAY, 00);        //当天的时,为0时
                cal.set(Calendar.MINUTE, 0);            //当天的分,为0分
                cal.set(Calendar.SECOND, 0);            //当天的秒,为0秒
                estimateStartTime = cal.getTime().getTime() / 1000;
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
                estimateEndTime = cal.getTime().getTime() / 1000;
            } else if (7 == dateType) {//上周
                Calendar date1 = Calendar.getInstance();
                date1.set(Calendar.HOUR_OF_DAY, 00);    //当天的时,为0时
                date1.set(Calendar.MINUTE, 0);            //当天的分,为0分
                date1.set(Calendar.SECOND, 0);            //当天的秒,为0秒
                int[] weekDays = {6, 0, 1, 2, 3, 4, 5};
                int num = weekDays[date1.get(Calendar.DAY_OF_WEEK) - 1];
                date1.add(Calendar.DAY_OF_MONTH, -num - 7);    //上周周一日期
                estimateEndTime = date1.getTime().getTime() / 1000;
                date1.add(Calendar.DAY_OF_MONTH, 6);    //上周周日日期
                date1.set(Calendar.HOUR_OF_DAY, 23);    //周日的23：59：59
                date1.set(Calendar.MINUTE, 59);
                date1.set(Calendar.SECOND, 59);
                estimateStartTime = date1.getTime().getTime() / 1000;
            } else if (8 == dateType) {//下周
                Calendar date1 = Calendar.getInstance();
                date1.set(Calendar.HOUR_OF_DAY, 00);    //当天的时,为0时
                date1.set(Calendar.MINUTE, 0);            //当天的分,为0分
                date1.set(Calendar.SECOND, 0);            //当天的秒,为0秒
                int[] weekDays = {6, 0, 1, 2, 3, 4, 5};
                int num = weekDays[date1.get(Calendar.DAY_OF_WEEK) - 1];
                date1.add(Calendar.DAY_OF_MONTH, -num + 7);    //下周周一日期
                estimateEndTime = date1.getTime().getTime() / 1000;
                date1.add(Calendar.DAY_OF_MONTH, 6);    //下周周日日期
                date1.set(Calendar.HOUR_OF_DAY, 23);    //周日的23：59：59
                date1.set(Calendar.MINUTE, 59);
                date1.set(Calendar.SECOND, 59);
                estimateStartTime = date1.getTime().getTime() / 1000;
            } else if (9 == dateType) {//上个月
                Calendar date1 = Calendar.getInstance();
                date1.set(Calendar.DATE, 1);                //设为当前月的1号
                date1.add(Calendar.MONTH, -1);            //减一个月，变为上月的1号
                date1.set(Calendar.HOUR_OF_DAY, 00);        //当天的时,为0时
                date1.set(Calendar.MINUTE, 0);            //当天的分,为0分
                date1.set(Calendar.SECOND, 0);            //当天的秒,为0秒
                estimateStartTime = date1.getTime().getTime() / 1000;
                Calendar date2 = Calendar.getInstance();
                date2.set(Calendar.DATE, 1);                //设为当前月的1号
                date2.add(Calendar.DATE, -1);            //减去一天，变为上月最后一天
                date2.set(Calendar.HOUR_OF_DAY, 23);     //当天的时,为23时
                date2.set(Calendar.MINUTE, 59);          //当天的分,为59分
                date2.set(Calendar.SECOND, 59);            //当天的秒,为59秒
                estimateEndTime = date2.getTime().getTime() / 1000;
            } else if (10 == dateType) {//前天
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -2);
                cal.set(Calendar.HOUR_OF_DAY, 00);        //当天的时,为0时
                cal.set(Calendar.MINUTE, 0);            //当天的分,为0分
                cal.set(Calendar.SECOND, 0);            //当天的秒,为0秒
                estimateStartTime = cal.getTime().getTime() / 1000;
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
                estimateEndTime = cal.getTime().getTime() / 1000;
            } else if (11 == dateType) {//明天
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, +1);
                cal.set(Calendar.HOUR_OF_DAY, 00);        //当天的时,为0时
                cal.set(Calendar.MINUTE, 0);            //当天的分,为0分
                cal.set(Calendar.SECOND, 0);            //当天的秒,为0秒
                estimateStartTime = cal.getTime().getTime() / 1000;
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
                estimateEndTime = cal.getTime().getTime() / 1000;
            } else if (12 == dateType) {//下个月
                Calendar date1 = Calendar.getInstance();
                date1.set(Calendar.DATE, 1);                //设为当前月的1号
                date1.add(Calendar.MONTH, +1);            //加一个月，变为下月的1号
                date1.set(Calendar.HOUR_OF_DAY, 00);        //当天的时,为0时
                date1.set(Calendar.MINUTE, 0);            //当天的分,为0分
                date1.set(Calendar.SECOND, 0);            //当天的秒,为0秒
                estimateStartTime = date1.getTime().getTime() / 1000;
                Calendar date2 = Calendar.getInstance();
                date2.set(Calendar.DATE, 1);                //设为当前月的1号
                date2.add(Calendar.MONTH, +2);           //加两个月,变为下下个月的1号
                date2.add(Calendar.DATE, -1);            //减一天，变为下个月最后一天
                date2.set(Calendar.HOUR_OF_DAY, 23);     //当天的时,为23时
                date2.set(Calendar.MINUTE, 59);          //当天的分,为59分
                date2.set(Calendar.SECOND, 59);            //当天的秒,为59秒
                estimateEndTime = date2.getTime().getTime() / 1000;
            }
            timeArray.add(estimateStartTime);
            timeArray.add(estimateEndTime);
        } else {
            timeArray = null;
        }
        return timeArray;
    }
}
