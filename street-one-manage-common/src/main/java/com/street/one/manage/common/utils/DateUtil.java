package com.street.one.manage.common.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.utils
 * @ClassName: DateUtil
 * @Author: tjl
 * @Description: 日期工具类
 * @Date: 2024/5/13 16:50
 * @modified modify person name
 * @Version: 1.0
 */
public class DateUtil {

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static String DEF_DATETIME_FMT = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy-MM-dd HH:mm:ss SSS
     */
    public static String DEF_DATETIME_FMT_3 = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * yyyy-MM-dd
     */
    public static String DEF_DATE_FMT = "yyyy-MM-dd";

    /**
     * yyyyMMdd
     */
    public static String DEF_DATE_FMT1 = "yyyyMMdd";

    /**
     * yyyyMMddHHmmssms
     */
    public static String DEF_DATE_FMT2 = "yyyyMMddHHmmssms";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    public static Date convertToDate(Object val, String format) throws ParseException {
        Date dt = null;

        if (val instanceof java.sql.Date) {
            dt = new Date(((java.sql.Date) val).getTime());
        } else if (val instanceof Date) {
            dt = (Date) val;
        } else if (val instanceof Integer || val instanceof Long) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            dt = sdf.parse(sdf.format(val));
        } else if (val != null) {
            if (val.toString().length() <= 10) {
                SimpleDateFormat sdf = new SimpleDateFormat(DEF_DATE_FMT);
                dt = sdf.parse(val.toString());
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                dt = sdf.parse(val.toString());
            }
        }

        return dt;
    }

    /**
     * 按默认时间字符串转换日期
     *
     * @param val
     * @return
     * @throws ParseException
     * @see DateUtil::format
     */
    public static Date convertToDate(Object val) {
        try {
            return convertToDate(val, DEF_DATETIME_FMT);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 按默认时间格式[yyyy-MM-dd HH:mm:ss]格式化时间
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        if (date == null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat(DEF_DATETIME_FMT);
        return sdf.format(date);
    }

    public static String format(Date date, String format) {
        if (date == null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 获取当天日期与时间
     *
     * @return
     */
    public static Date getNow() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    /**
     * 获取当天日期，不包括时间部分
     *
     * @return
     */
    public static Date getToday() {
        Calendar cal = Calendar.getInstance();
        clearTimeField(cal);
        return cal.getTime();
    }

    private static void clearTimeField(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    /**
     * 获取当前日期的年份
     */
    public static int getYearValue(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前日期的月份
     */
    public static int getMonthValue(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前日期的日份
     */
    public static int getDayValue(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前日期的时间部分的小时
     */
    public static int getHourValue(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取当前日期的时间部分的分钟
     */
    public static int getMinuteValue(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 获取当前日期的时间部分的秒
     */
    public static int getSecondValue(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 获取两个日期之间的相隔天数
     *
     * @param date1
     * @param date2
     * @return days date2 -date1的天数
     */
    public static int getDifferDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        clearTimeField(cal1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        clearTimeField(cal2);

        long between_days = (cal2.getTimeInMillis() - cal1.getTimeInMillis()) / (1000 * 3600 * 24);
        return (int) between_days;
    }

    public static int getDeffMinute(Date date1, Date date2) {
        long diff = date1.getTime() - date2.getTime();
        Long minute = Math.abs(diff / 60 / 1000);
        return minute.intValue();
    }

    /**
     * 获取两个日期之间的相隔月数
     *
     * @param d1
     * @param d2
     * @return days date2 -date1的月数
     */
    public static int getDiffMonth(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值 
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if (month1 < month2 || month1 == month2 && day1 < day2) {
            yearInterval--;
        }
        // 获取月数差值
        int monthInterval = (month1 + 12) - month2;
        if (day1 < day2) {
            monthInterval--;
        }
        monthInterval %= 12;
        int monthsDiff = Math.abs(yearInterval * 12 + monthInterval);
        return monthsDiff;
    }

    /**
     * 获取调整日期
     *
     * @param originalDate 被调整的日期，null表示当前时间
     * @param type         {@link Calendar}类定义的各种常量，年、月、周、日、时、分、秒、毫秒
     * @param value        调整值 正数表示日期之后，负数表示之前
     * @return
     */
    public static Date adjust(Date originalDate, int type, int value) {
        Calendar cal = Calendar.getInstance();

        if (originalDate != null) {
            cal.setTime(originalDate);
        }

        if (value != 0) {
            cal.add(type, value);
        }

        return cal.getTime();
    }

    /**
     * 是否是当月日期
     *
     * @param date
     * @return
     */
    public static boolean isCurrentMonth(Date date) {
        int dateMonth = DateUtil.getMonthValue(date);
        int currentMonth = DateUtil.getMonthValue(DateUtil.getToday());
        return dateMonth == currentMonth;
    }

    /**
     * 是否是当天日期
     *
     * @param date
     * @return
     */
    public static boolean isCurrentDay(Date date) {
        int dateDay = DateUtil.getDayValue(date);
        int currentDay = DateUtil.getDayValue(DateUtil.getToday());
        return dateDay == currentDay;
    }

    // 获得某天最大时间 2017-10-15 23:59:59
    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()),
                ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 获得某天最小时间 2017-10-15 00:00:00
    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()),
                ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /*
     * 获取本周的开始时间
     */
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    /*
     * 获取本周的结束时间
     */
    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    // 获取上周的开始时间
    public static Date getBeginDayOfLastWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return getDayStartTime(cal.getTime());
    }

    // 获取指定周上周的结束时间
    public static Date getEndDayOfLastWeek(Date date) {

        //指定日期周的第一天就是上一周的周日
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return getDayEndTime(cal.getTime());
    }

    // 获取本月的开始时间
    public static Date getBeginDayOfMonth() {
        return getBeginDayOfMonth(null);
    }

    // 获取本年的开始时间
    public static Date getBeginDayOfYear() {
        return getBeginDayOfYear(null);
    }

    // 获取传入月份的开始时间
    public static Date getBeginDayOfMonth(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(getYearValue(calendar.getTime()), getMonthValue(calendar.getTime()) - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    // 获取传入月份的开始时间
    public static Date getBeginDayOfYear(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(getYearValue(calendar.getTime()), 0, 1);
        return getDayStartTime(calendar.getTime());
    }

    // 获取本月的结束时间
    public static Date getEndDayOfMonth() {
        return getEndDayOfMonth(null);
    }

    // 获取本年的结束时间
    public static Date getEndDayOfYear() {
        return getEndDayOfYear(null);
    }

    // 获取本月的结束时间
    public static Date getEndDayOfMonth(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(getYearValue(calendar.getTime()), getMonthValue(calendar.getTime()) - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getYearValue(calendar.getTime()), getMonthValue(calendar.getTime()) - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    // 获取本年的结束时间
    public static Date getEndDayOfYear(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        int year = calendar.get(Calendar.YEAR);
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return getDayEndTime(calendar.getTime());
    }

    // 获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0,
                0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    // 获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23,
                59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 判断当前日期是星期几
     *
     * @param date
     * @return
     * @throws Throwable
     */
    public static String dayForWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        String[] weekDays = {"7", "1", "2", "3", "4", "5", "6"};// 周日到周六
        try {
            cal.setTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 仿微信聊天时间格式化
     *
     * @param date
     * @return
     */
    public static String wechatFormatTime(Date date) {
        if (isSameYear(date)) { // 同一年 显示MM-dd HH:mm
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
            if (isSameDay(date)) { // 同一天 显示HH:mm
                int minute = minutesAgo(date.getTime());
                if (minute < 60) {// 1小时之内 显示n分钟前
                    if (minute <= 1) {// 一分钟之内，显示刚刚
                        return "刚刚";
                    } else {
                        return minute + "分钟前";
                    }
                } else {
                    return simpleDateFormat.format(date);
                }
            } else {
                if (isYesterday(date)) {// 昨天，显示昨天+HH:mm
                    return "昨天 ";
                } else if (isSameWeek(date)) {// 本周,显示周几+HH:mm
                    String weekday = null;
                    if (date.getDay() == 1) {
                        weekday = "周一";
                    }
                    if (date.getDay() == 2) {
                        weekday = "周二";
                    }
                    if (date.getDay() == 3) {
                        weekday = "周三";
                    }
                    if (date.getDay() == 4) {
                        weekday = "周四";
                    }
                    if (date.getDay() == 5) {
                        weekday = "周五";
                    }
                    if (date.getDay() == 6) {
                        weekday = "周六";
                    }
                    if (date.getDay() == 0) {
                        weekday = "周日";
                    }
                    return weekday;
                } else {// 同一年 显示MM-dd HH:mm
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd", Locale.CHINA);
                    return sdf.format(date);
                }
            }
        } else {// 不是同一年 显示完整日期yyyy-MM-dd HH:mm
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            return sdf.format(date);
        }
    }

    /**
     * 几分钟前
     *
     * @param time
     * @return
     */
    public static int minutesAgo(long time) {
        return (int) ((System.currentTimeMillis() - time) / (60000));
    }

    /**
     * 与当前时间是否在同一周 先判断是否在同一年，然后根据Calendar.DAY_OF_YEAR判断所得的周数是否一致
     *
     * @param date
     * @return
     */
    public static boolean isSameWeek(Date date) {
        if (isSameYear(date)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int a = calendar.get(Calendar.DAY_OF_YEAR);

            Date now = new Date();
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(now);
            int b = calendar1.get(Calendar.DAY_OF_WEEK);
            return a == b;
        } else {
            return false;
        }
    }

    /**
     * 是否是当前时间的昨天 获取指定时间的后一天的日期，判断与当前日期是否是同一天
     *
     * @param date
     * @return
     */
    public static boolean isYesterday(Date date) {
        Date yesterday = getNextDay(date, 1);
        return isSameDay(yesterday);
    }

    /**
     * 判断与当前日期是否是同一天
     *
     * @param date
     * @return
     */
    public static boolean isSameDay(Date date) {
        return isEquals(date, "yyyy-MM-dd");
    }

    /**
     * 判断与当前日期是否是同一月
     *
     * @param date
     * @return
     */
    public static boolean isSameMonth(Date date) {
        return isEquals(date, "yyyy-MM");
    }

    /**
     * 判断与当前日期是否是同一年
     *
     * @param date
     * @return
     */
    public static boolean isSameYear(Date date) {
        return isEquals(date, "yyyy");
    }

    /**
     * 格式化Date，判断是否相等
     *
     * @param date
     * @return 是返回true，不是返回false
     */
    private static boolean isEquals(Date date, String format) {
        // 当前时间
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat(format);
        // 获取今天的日期
        String nowDay = sf.format(now);
        // 对比的时间
        String day = sf.format(date);
        return day.equals(nowDay);
    }

    /**
     * 获取某个date第n天的日期 n<0 表示前n天 n=0 表示当天 n>1 表示后n天
     *
     * @param date
     * @param n
     * @return
     */
    public static Date getNextDay(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, n);
        date = calendar.getTime();
        return date;
    }

    /**
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @author sunran 判断当前时间在时间区间内
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime() || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /***
     * 根据当前时间往后推一年
     *
     * @return
     */
    public static Date getCurrentToEndDate(Date currentDate, Integer year, Integer month, Integer day)
            throws ParseException {
        Calendar calendar = new GregorianCalendar();
        Date date = currentDate;
        if (null != date) {
            calendar.setTime(date);
        } else {
            calendar.setTime(new Date());
        }

        calendar.add(Calendar.YEAR, year);// 把日期往后增加一年.整数往后推,负数往前移动
        calendar.add(Calendar.MONTH, month);// 把日期往后增加一个月.整数往后推,负数往前移动
        calendar.add(Calendar.DATE, day);// 把日期往后增加一天.整数往后推,负数往前移动
        // calendar.add(calendar.WEEK_OF_MONTH, 1);//把日期往后增加一个月.整数往后推,负数往前移动
        date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String d_now = sDateFormat.format(date) + " 23:59:59";
        return toDate(d_now, DEF_DATETIME_FMT);
    }

    /**
     * 转换日期(pattern)
     */
    public static Date toDate(String date, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(date);
    }


    /**
     * 获取指定日期上一年相同周的开始日期
     *
     * @return
     */
    public static Date getBeginDayOfYearWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, -1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return getDayStartTime(cal.getTime());
    }

    /**
     * 获取指定日期上一年相同周的开始日期
     *
     * @return
     */
    public static Date getEndDayOfYearWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, -1);
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return getDayStartTime(cal.getTime());
    }

    /**
     * 最近一年第一月
     * @return Date
     */
    public static String getYearFirst(){
        Calendar cale = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(DateUtil.DEF_DATE_FMT);
        cale.add(Calendar.YEAR, 0);
        cale.set(Calendar.DAY_OF_YEAR, 1);
        return format.format(cale.getTime());
    }

    /***
     * 最近一月开始时间
     * @return
     */
    public static String getRecentlyMonth() {
        Calendar cale = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        return  format.format(cale.getTime());
    }

    /***
     * 开始时间转换
     * @param startDateTime
     * @return
     */
    public static Date startDateConvertToDateTime(Date startDateTime){
        return  DateUtil.convertToDate(DateUtil.format(startDateTime,
                DateUtil.DEF_DATE_FMT) + " 00:00:00");
    }

    /***
     * 结束日期转换
     * @param endDateTime
     * @return
     */
    public static Date endDateConvertToDateTime(Date endDateTime){
        //结束日期大于当前日期，那么结束日期就等于当前日期
//        if (endDateTime.after(DateUtil.getNow())) {
//            endDateTime = DateUtil.getNow();
//        }
        return DateUtil.convertToDate(DateUtil.format(endDateTime,
                DateUtil.DEF_DATE_FMT) + " 23:59:59");
    }

    /**
     * 获取指定周的开始时间
     * @return
     */
    public static Date getStartTimeOfCurrentWeek(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        setMinTimeOfDay(calendar);
        return calendar.getTime();
    }

    /**
     *  获取指定周的结束时间
     * @return
     */
    public static Date getEndTimeOfCurrentWeek(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        setMaxTimeOfDay(calendar);
        return calendar.getTime();
    }

    /**
     * 设置当天的开始时间
     * @param calendar
     */
    private static void setMinTimeOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    /**
     * 设置当天的结束时间
     * @param calendar
     */
    private static void setMaxTimeOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return DateUtils.parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }




    public static void main(String[] args) throws ParseException {
        System.out.println(getYearFirst());
        System.out.println(getRecentlyMonth());
        System.out.println(DateUtil.format(getStartTimeOfCurrentWeek(DateUtil.convertToDate("2023-01-29 11:37:23"))));
        System.out.println(DateUtil.format(getEndTimeOfCurrentWeek(DateUtil.convertToDate("2023-01-29 11:37:23"))));
    }

}
