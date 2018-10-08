package com.hjh.utils;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class TimeUtils {
    public static final String CM_To_LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String CM_LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String CM_SHORT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String CM_SHORT_MONTH_FORMAT = "yyyy-MM";
    public static final String CM_LONG_YEAR_FORMAT = "yyyy";
    public static final String CM_SHORT_YEAR_FORMAT = "yy";
    public static final String YEAR_MONTH = "yyyyMM";
    public static final String SHORT_MONTH_FORMAT = "MM";
    public static final String SHORT_DAY_FORMAT = "dd";
    private static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private static final String[] CHINESE_ZODIAC = new String[]{"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};
    private static final int[] ZODIAC_FLAGS = new int[]{20, 19, 21, 21, 21, 22, 23, 23, 23, 24, 23, 22};
    private static final String[] ZODIAC = new String[]{"水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"};

    private TimeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static String millis2String(long millis) {
        return millis2String(millis, DEFAULT_FORMAT);
    }

    public static String millis2String(long millis, DateFormat format) {
        return format.format(new Date(millis));
    }

    public static long string2Millis(String time) {
        return string2Millis(time, DEFAULT_FORMAT);
    }

    public static long string2Millis(String time, DateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException var3) {
            var3.printStackTrace();
            return -1L;
        }
    }

    public static Date string2Date(String time) {
        return string2Date(time, DEFAULT_FORMAT);
    }

    public static Date string2Date(String time, DateFormat format) {
        try {
            return format.parse(time);
        } catch (ParseException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static String date2String(Date date) {
        return date2String(date, DEFAULT_FORMAT);
    }

    public static String date2String(Date date, DateFormat format) {
        return format.format(date);
    }

    public static long date2Millis(Date date) {
        return date.getTime();
    }

    public static Date millis2Date(long millis) {
        return new Date(millis);
    }

    public static long getTimeSpan(String time1, String time2, int unit) {
        return getTimeSpan(time1, time2, DEFAULT_FORMAT, unit);
    }

    public static long getTimeSpan(String time1, String time2, DateFormat format, int unit) {
        return millis2TimeSpan(string2Millis(time1, format) - string2Millis(time2, format), unit);
    }

    public static long getTimeSpan(Date date1, Date date2, int unit) {
        return millis2TimeSpan(date2Millis(date1) - date2Millis(date2), unit);
    }

    public static long getTimeSpan(long millis1, long millis2, int unit) {
        return millis2TimeSpan(millis1 - millis2, unit);
    }

    public static String getFitTimeSpan(String time1, String time2, int precision) {
        long delta = string2Millis(time1, DEFAULT_FORMAT) - string2Millis(time2, DEFAULT_FORMAT);
        return millis2FitTimeSpan(delta, precision);
    }

    public static String getFitTimeSpan(String time1, String time2, DateFormat format, int precision) {
        long delta = string2Millis(time1, format) - string2Millis(time2, format);
        return millis2FitTimeSpan(delta, precision);
    }

    public static String getFitTimeSpan(Date date1, Date date2, int precision) {
        return millis2FitTimeSpan(date2Millis(date1) - date2Millis(date2), precision);
    }

    public static String getFitTimeSpan(long millis1, long millis2, int precision) {
        return millis2FitTimeSpan(millis1 - millis2, precision);
    }

    public static long getNowMills() {
        return System.currentTimeMillis();
    }

    public static String getNowString() {
        return millis2String(System.currentTimeMillis(), DEFAULT_FORMAT);
    }

    public static String getNowString(DateFormat format) {
        return millis2String(System.currentTimeMillis(), format);
    }

    public static Date getNowDate() {
        return new Date();
    }

    public static long getTimeSpanByNow(String time, int unit) {
        return getTimeSpan(time, getNowString(), DEFAULT_FORMAT, unit);
    }

    public static long getTimeSpanByNow(String time, DateFormat format, int unit) {
        return getTimeSpan(time, getNowString(format), format, unit);
    }

    public static long getTimeSpanByNow(Date date, int unit) {
        return getTimeSpan(date, new Date(), unit);
    }

    public static long getTimeSpanByNow(long millis, int unit) {
        return getTimeSpan(millis, System.currentTimeMillis(), unit);
    }

    public static String getFitTimeSpanByNow(String time, int precision) {
        return getFitTimeSpan(time, getNowString(), DEFAULT_FORMAT, precision);
    }

    public static String getFitTimeSpanByNow(String time, DateFormat format, int precision) {
        return getFitTimeSpan(time, getNowString(format), format, precision);
    }

    public static String getFitTimeSpanByNow(Date date, int precision) {
        return getFitTimeSpan(date, getNowDate(), precision);
    }

    public static String getFitTimeSpanByNow(long millis, int precision) {
        return getFitTimeSpan(millis, System.currentTimeMillis(), precision);
    }

    public static String getFriendlyTimeSpanByNow(String time) {
        return getFriendlyTimeSpanByNow(time, DEFAULT_FORMAT);
    }

    public static String getFriendlyTimeSpanByNow(String time, DateFormat format) {
        return getFriendlyTimeSpanByNow(string2Millis(time, format));
    }

    public static String getFriendlyTimeSpanByNow(Date date) {
        return getFriendlyTimeSpanByNow(date.getTime());
    }

    public static String getFriendlyTimeSpanByNow(long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 0L) {
            return String.format("%tc", millis);
        } else if (span < 1000L) {
            return "刚刚";
        } else if (span < 60000L) {
            return String.format(Locale.getDefault(), "%d秒前", span / 1000L);
        } else if (span < 3600000L) {
            return String.format(Locale.getDefault(), "%d分钟前", span / 60000L);
        } else {
            long wee = getWeeOfToday();
            if (millis >= wee) {
                return String.format("今天%tR", millis);
            } else {
                return millis >= wee - 86400000L ? String.format("昨天%tR", millis) : String.format("%tF", millis);
            }
        }
    }

    private static long getWeeOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(11, 0);
        cal.set(13, 0);
        cal.set(12, 0);
        cal.set(14, 0);
        return cal.getTimeInMillis();
    }

    public static long getMillis(long millis, long timeSpan, int unit) {
        return millis + timeSpan2Millis(timeSpan, unit);
    }

    public static long getMillis(String time, long timeSpan, int unit) {
        return getMillis(time, DEFAULT_FORMAT, timeSpan, unit);
    }

    public static long getMillis(String time, DateFormat format, long timeSpan, int unit) {
        return string2Millis(time, format) + timeSpan2Millis(timeSpan, unit);
    }

    public static long getMillis(Date date, long timeSpan, int unit) {
        return date2Millis(date) + timeSpan2Millis(timeSpan, unit);
    }

    public static String getString(long millis, long timeSpan, int unit) {
        return getString(millis, DEFAULT_FORMAT, timeSpan, unit);
    }

    public static String getString(long millis, DateFormat format, long timeSpan, int unit) {
        return millis2String(millis + timeSpan2Millis(timeSpan, unit), format);
    }

    public static String getString(String time, long timeSpan, int unit) {
        return getString(time, DEFAULT_FORMAT, timeSpan, unit);
    }

    public static String getString(String time, DateFormat format, long timeSpan, int unit) {
        return millis2String(string2Millis(time, format) + timeSpan2Millis(timeSpan, unit), format);
    }

    public static String getString(Date date, long timeSpan, int unit) {
        return getString(date, DEFAULT_FORMAT, timeSpan, unit);
    }

    public static String getString(Date date, DateFormat format, long timeSpan, int unit) {
        return millis2String(date2Millis(date) + timeSpan2Millis(timeSpan, unit), format);
    }

    public static Date getDate(long millis, long timeSpan, int unit) {
        return millis2Date(millis + timeSpan2Millis(timeSpan, unit));
    }

    public static Date getDate(String time, long timeSpan, int unit) {
        return getDate(time, DEFAULT_FORMAT, timeSpan, unit);
    }

    public static Date getDate(String time, DateFormat format, long timeSpan, int unit) {
        return millis2Date(string2Millis(time, format) + timeSpan2Millis(timeSpan, unit));
    }

    public static Date getDate(Date date, long timeSpan, int unit) {
        return millis2Date(date2Millis(date) + timeSpan2Millis(timeSpan, unit));
    }

    public static long getMillisByNow(long timeSpan, int unit) {
        return getMillis(getNowMills(), timeSpan, unit);
    }

    public static String getStringByNow(long timeSpan, int unit) {
        return getStringByNow(timeSpan, DEFAULT_FORMAT, unit);
    }

    public static String getStringByNow(long timeSpan, DateFormat format, int unit) {
        return getString(getNowMills(), format, timeSpan, unit);
    }

    public static Date getDateByNow(long timeSpan, int unit) {
        return getDate(getNowMills(), timeSpan, unit);
    }

    public static boolean isToday(String time) {
        return isToday(string2Millis(time, DEFAULT_FORMAT));
    }

    public static boolean isToday(String time, DateFormat format) {
        return isToday(string2Millis(time, format));
    }

    public static boolean isToday(Date date) {
        return isToday(date.getTime());
    }

    public static boolean isToday(long millis) {
        long wee = getWeeOfToday();
        return millis >= wee && millis < wee + 86400000L;
    }

    public static boolean isLeapYear(String time) {
        return isLeapYear(string2Date(time, DEFAULT_FORMAT));
    }

    public static boolean isLeapYear(String time, DateFormat format) {
        return isLeapYear(string2Date(time, format));
    }

    public static boolean isLeapYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(1);
        return isLeapYear(year);
    }

    public static boolean isLeapYear(long millis) {
        return isLeapYear(millis2Date(millis));
    }

    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    public static String getChineseWeek(String time) {
        return getChineseWeek(string2Date(time, DEFAULT_FORMAT));
    }

    public static String getChineseWeek(String time, DateFormat format) {
        return getChineseWeek(string2Date(time, format));
    }

    public static String getChineseWeek(Date date) {
        return (new SimpleDateFormat("E", Locale.CHINA)).format(date);
    }

    public static String getChineseWeek(long millis) {
        return getChineseWeek(new Date(millis));
    }

    public static String getUSWeek(String time) {
        return getUSWeek(string2Date(time, DEFAULT_FORMAT));
    }

    public static String getUSWeek(String time, DateFormat format) {
        return getUSWeek(string2Date(time, format));
    }

    public static String getUSWeek(Date date) {
        return (new SimpleDateFormat("EEEE", Locale.US)).format(date);
    }

    public static String getUSWeek(long millis) {
        return getUSWeek(new Date(millis));
    }

    public static int getValueByCalendarField(String time, int field) {
        return getValueByCalendarField(string2Date(time, DEFAULT_FORMAT), field);
    }

    public static int getValueByCalendarField(String time, DateFormat format, int field) {
        return getValueByCalendarField(string2Date(time, format), field);
    }

    public static int getValueByCalendarField(Date date, int field) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(field);
    }

    public static int getValueByCalendarField(long millis, int field) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        return cal.get(field);
    }

    public static String getChineseZodiac(String time) {
        return getChineseZodiac(string2Date(time, DEFAULT_FORMAT));
    }

    public static String getChineseZodiac(String time, DateFormat format) {
        return getChineseZodiac(string2Date(time, format));
    }

    public static String getChineseZodiac(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return CHINESE_ZODIAC[cal.get(1) % 12];
    }

    public static String getChineseZodiac(long millis) {
        return getChineseZodiac(millis2Date(millis));
    }

    public static String getChineseZodiac(int year) {
        return CHINESE_ZODIAC[year % 12];
    }

    public static String getZodiac(String time) {
        return getZodiac(string2Date(time, DEFAULT_FORMAT));
    }

    public static String getZodiac(String time, DateFormat format) {
        return getZodiac(string2Date(time, format));
    }

    public static String getZodiac(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(2) + 1;
        int day = cal.get(5);
        return getZodiac(month, day);
    }

    public static String getZodiac(long millis) {
        return getZodiac(millis2Date(millis));
    }

    public static String getZodiac(int month, int day) {
        return ZODIAC[day >= ZODIAC_FLAGS[month - 1] ? month - 1 : (month + 10) % 12];
    }

    private static long timeSpan2Millis(long timeSpan, int unit) {
        return timeSpan * (long)unit;
    }

    private static long millis2TimeSpan(long millis, int unit) {
        return millis / (long)unit;
    }

    private static String millis2FitTimeSpan(long millis, int precision) {
        if (precision <= 0) {
            return null;
        } else {
            precision = Math.min(precision, 5);
            String[] units = new String[]{"天", "小时", "分钟", "秒", "毫秒"};
            if (millis == 0L) {
                return 0 + units[precision - 1];
            } else {
                StringBuilder sb = new StringBuilder();
                if (millis < 0L) {
                    sb.append("-");
                    millis = -millis;
                }

                int[] unitLen = new int[]{86400000, 3600000, 60000, 1000, 1};

                for(int i = 0; i < precision; ++i) {
                    if (millis >= (long)unitLen[i]) {
                        long mode = millis / (long)unitLen[i];
                        millis -= mode * (long)unitLen[i];
                        sb.append(mode).append(units[i]);
                    }
                }

                return sb.toString();
            }
        }
    }
}
