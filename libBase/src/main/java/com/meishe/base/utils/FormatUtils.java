package com.meishe.base.utils;

import java.util.Locale;

/**
 * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : LiHangZhou
 * @CreateDate : 2020/11/20
 * @Description :关于格式化的工具类。 A tool class about formatting
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
public class FormatUtils {
    private static final int HOUR = 3600;
    private static final int MINUTE = 60;
    private static final int HUNDRED = 100;
    private static final int MILLISECOND = 1000;
    private static final int MICROSECOND = 1000000;

    /**
     * 将时间转化成00:00:00的格式
     * Convert the time to a 00:00:00 format
     *
     * @param time long 需要转化的微秒数 The microsecond need to convert
     * @return the string
     */
    public static String microsecond2Time(long time) {
        return sec2Time((int) (time / MICROSECOND));
    }

    /**
     * 将时间转化成00:00:00的格式
     * Convert the time to a 00:00:00 format
     *
     * @param time long 需要转化的毫秒数 The millisecond need to convert
     * @return the string
     */
    public static String millisecond2Time(long time) {
        return sec2Time((int) (time / MILLISECOND));
    }

    /**
     * 将时间转化成00:00:00的格式
     * Convert the time to a 00:00:00 format
     *
     * @param time int 需要转化的秒数 The second need to convert
     * @return the string
     */
    public static String sec2Time(int time) {
        String timeStr = "00:00";
        if (time > 0) {
            int hour = time / HOUR;
            int minute = time / MINUTE;
            int second = time % MINUTE;

            timeStr = getTime(minute) + ":" + getTime(second);

            if (hour < HUNDRED && hour > 0) {
                timeStr = getTime(hour) + ":" + timeStr;
            } else if (hour >= HUNDRED) {
                timeStr = "99:59:59";
            }
        }
        return timeStr;
    }

    private static String getTime(int i) {
        return i >= 0 && i < 10 ? "0" + i : Integer.toString(i);
    }

    /**
     * 将时间转化成xx:xx:x.xs的格式
     * Convert the time to a xx:xx:x.xs format
     *
     * @param duration long 需要转化的微秒时长 The microsecond need to convert
     * @return the string
     */
    public static String duration2Text(long duration) {
        float time = duration * 1f / MICROSECOND;
        String timeStr;
        if (time > MINUTE) {
            int hour = (int) (time / HOUR);
            int minute = (int) (time / MINUTE);
            float second = time % MINUTE;
            timeStr = getTime(minute) + ":" + (second < 10 ? "0" + objectFormat2String(second) + "s" : objectFormat2String(second) + "s");
            if (hour < HUNDRED && hour > 0) {
                timeStr = getTime(hour) + ":" + timeStr;
            } else if (hour >= HUNDRED) {
                timeStr = "99:59:59s";
            }
        } else {
            timeStr = objectFormat2String(time) + "s";
        }
        return timeStr;
    }

    /**
     * 将时间转化成xx:xx:xx.xs的格式，大于1分钟，不再精确到.xs
     * Convert the time to a xx:xx:x.xs format
     *
     * @param duration long 需要转化的微秒时长 The microsecond need to convert
     * @return the string
     */
    public static String duration2Text2(long duration) {
        float time = duration * 1f / MICROSECOND;
        String timeStr;
        if (time > MINUTE) {
            int hour = (int) (time / HOUR);
            int minute = (int) (time / MINUTE);
            float second = time % MINUTE;
            timeStr = getTime(minute) + ":" + (second < 10 ? "0" + (int) second : (int) second);
            if (hour < HUNDRED && hour > 0) {
                timeStr = getTime(hour) + ":" + timeStr;
            } else if (hour >= HUNDRED) {
                timeStr = "99:59:59s";
            }
        } else {
            timeStr = objectFormat2String(time) + "s";
        }
        return timeStr;
    }


    /**
     * 格式化float保持小数点后一位
     * Float format float.
     *
     * @param time the time
     * @return the float
     */
    public static float floatFormat(Object time) {
        String formatString = objectFormat2String(time);
        return Float.parseFloat(formatString);
    }

    /**
     * float格式化
     * Float format float.
     *
     * @param time   the time
     * @param format the format
     * @return the float
     */
    public static float floatFormat(Object time, String format) {
        String formatString = objectFormat2String(time, format);
        return Float.parseFloat(formatString);
    }

    /**
     * 格式化Object保持小数点后一位
     * object format float to String.
     *
     * @param time the time
     * @return the string
     */
    public static String objectFormat2String(Object time) {
        return objectFormat2String(time, "%.1f");
    }

    /**
     * String 格式化
     * object format 2 string string.
     *
     * @param time   the time
     * @param format the format
     * @return the string
     */
    public static String objectFormat2String(Object time, String format) {
        return String.format(Locale.ENGLISH, format, time);
    }
}
