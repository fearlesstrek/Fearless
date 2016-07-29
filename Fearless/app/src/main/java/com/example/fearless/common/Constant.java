package com.example.fearless.common;


import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class Constant {
//    public  static final  String host ="http://192.168.191.1:8080/invite";
//    public  static final  String host ="http://172.26.51.8:8080/invite";
    public  static final  String host ="http://10.0.2.2:8080/invite";
    //默认每页数量
    public static final  int pagenum = 10;

    public static final String KYE_TOKEN = "token";
    public static final String APP_ID = "com.example.fearless.common";

    public static final String KEY_COST_TOTAL = "cost_total";
    public static final String COST_ID = "com.example.fearless.common";


    public static final String KEY_COST_OR_NOT = "cost_or_not";
    public static final String COST_OR_NOT_ID = "com.example.fearless.common";

    public static final String KEY_Time_For_INVITE = "time_for_invite";
    public static final String Time_For_INVITE_ID = "com.example.fearless.common";


    public static String getCachedToken(Context context) {

        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KYE_TOKEN, null);
    }

    public static void cachedToken(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KYE_TOKEN, token);
        editor.commit();
    }
    public static String getCachedCostTotal(Context context) {

        return context.getSharedPreferences(COST_ID, Context.MODE_PRIVATE).getString(KEY_COST_TOTAL, null);
    }

    public static void cachedCostTotal(Context context, String costTotal) {
        SharedPreferences.Editor editor = context.getSharedPreferences(COST_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_COST_TOTAL, costTotal);
        editor.commit();
    }
    public static String getCachedCostOrNot(Context context) {

        return context.getSharedPreferences(COST_OR_NOT_ID, Context.MODE_PRIVATE).getString(KEY_COST_OR_NOT, null);
    }

    public static void cachedCostOrNot(Context context, String costTotal) {
        SharedPreferences.Editor editor = context.getSharedPreferences(COST_OR_NOT_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_COST_OR_NOT, costTotal);
        editor.commit();
    }
    public static String getCachedTimeForInvite(Context context) {

        return context.getSharedPreferences(Time_For_INVITE_ID, Context.MODE_PRIVATE).getString(KEY_Time_For_INVITE, null);
    }

    public static void cachedTimeForInvite(Context context, String costTotal) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Time_For_INVITE_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_Time_For_INVITE, costTotal);
        editor.commit();
    }
    //string类型时间转换
    public static Calendar getCalendarByInintData(String initDateTime) {
        Calendar calendar = Calendar.getInstance();

        String date = spliteString(initDateTime, "日", "index", "front");//日期
        String time = spliteString(initDateTime, "日", "index", "back"); //时间

        String yearStr = spliteString(date, "年", "index", "front"); // 年份
        String monthAndDay = spliteString(date, "年", "index", "back"); // 月日

        String monthStr = spliteString(monthAndDay, "月", "index", "front"); // 月
        String dayStr = spliteString(monthAndDay, "月", "index", "back"); // 日

        String hourStr = spliteString(time, "时", "index", "front"); //时
        String minuteStr = spliteString(time, "时", "index", "back");

        String minutes = spliteString(minuteStr, "分", "index", "front");// 分

        int currentYear = Integer.parseInt(yearStr.trim());
        int currentMonth = Integer.parseInt(monthStr.trim());
        int currentDay = Integer.parseInt(dayStr.trim());
        int currentHour = Integer.parseInt(hourStr.trim());
        int currentMinute = Integer.parseInt(minutes.trim());
        Log.i("wxy-----changeTime", currentYear + "-" +
                currentMonth + "-" + currentDay + " " + currentHour + ":" + currentMinute);

        calendar.set(currentYear, currentMonth-1, currentDay, currentHour,
                currentMinute);


        return calendar;
    }
    public static String spliteString(String srcStr, String pattern,
                                      String indexOrLast, String frontOrBack) {
        String result = "";
        int loc = -1;
        if (indexOrLast.equalsIgnoreCase("index")) {
            loc = srcStr.indexOf(pattern); //取得字符串第一次出现得位置
        } else {
            loc = srcStr.lastIndexOf(pattern); //最后一个匹配串得位置
        }
        if (frontOrBack.equalsIgnoreCase("front")) {
            if (loc != -1)
                result = srcStr.substring(0, loc); //截取子串
        } else {
            if (loc != -1)
                result = srcStr.substring(loc + 1, srcStr.length()); //截取子串
        }
        return result;
    }
    public static boolean isServiceRunning(Context mContext,String className) {

        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)
                mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList
                = activityManager.getRunningServices(30);
        if (!(serviceList.size()>0)) {
            return false;
        }
        for (int i=0; i<serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }


}
