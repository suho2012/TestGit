package com.suho.passwordsave.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author suho
 * @date 2017/4/19
 * @desc 时间转换的工具类；
 */
public class TimeUtil {


    private static String mM;
    private static String mS;

    /**时间戳转换成字符窜*/
    public static String getDateAndTimeToString(long time) {
        Date d = new Date(time * 1000);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }
    /**时间戳转换成字符窜*/
    public static String getDateToString(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(d);
    }

    public static long getDataLong(String time) {
        //Date或者String转化为时间戳
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return date.getTime();
    }

    public static String friendlyTime(Date time) {
        //获取time距离当前的秒数
        int ct = (int)((System.currentTimeMillis() - time.getTime())/1000);

        if(ct == 0) {
            return "刚刚";
        }

        if(ct > 0 && ct < 60) {
            return ct + "秒前";
        }

        if(ct >= 60 && ct < 3600) {
            return Math.max(ct / 60,1) + "分钟前";
        }
        if(ct >= 3600 && ct < 86400){
            return ct / 3600 + "小时前";
        }
        if(ct >= 86400 && ct < 2592000){
            //86400 * 30
            int day = ct / 86400 ;
            return day + "天前";
        }
        if(ct >= 2592000 && ct < 31104000) {
            //86400 * 30
            return ct / 2592000 + "月前";
        }
        return ct / 31104000 + "年前";
    }

    public static String timeParse(int second) {
        int m = second / 60;
        int s1 = second % 60;
        mM = m < 10 ? "0" + m : String.valueOf(m);
        mS = s1 < 10 ? "0" + s1 : String.valueOf(s1);
        return mM + ":" + mS;
    }

    /**
     * 获取当前时间的秒数，long类型
     * @return
     */
    public static long getCurrentTime() {
        return System.currentTimeMillis()/1000;
    }

}
