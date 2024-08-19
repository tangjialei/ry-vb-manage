package com.street.one.manage.common.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.utils
 * @ClassName: LocalDateTimeUtils
 * @Author: tjl
 * @Description: Java8 日期处理类
 * @Date: 2023/7/5 11:47
 * @modified modify person name
 * @Version: 1.0
 */
public class LocalDateTimeUtils {

    /**
     * 时间格式
     * DATE_FORMAT_NORMAL: yyyy-MM-dd HH:mm:ss
     * DATE_FORMAT_NUMBER: yyyyMMddHHmmss
     * DATE_FORMAT_T: yyyy-MM-ddTHH:mm:ss
     * TILME :HH:mm:ss
     */
    public static final String DATE_FORMAT_NORMAL = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_NUMBER = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_T = "yyyy-MM-ddTHH:mm:ss";
    public static final String TILME = "HH:mm:ss";

    /***
     * 获取当前日期
     * @return
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    /***
     * 获取当前时间
     * @return
     */
    public static LocalTime getCurrentTime() {
        return LocalTime.now();
    }

    /***
     * 获取当前日期和时间
     * @return
     */
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    /***
     * 将日期转换为字符串
     * @param date
     * @param pattern
     * @return
     */
    public static String formatLocalDate(LocalDate date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }

    /***
     * 将时间转换为字符串
     * @param time
     * @param pattern
     * @return
     */
    public static String formatLocalTime(LocalTime time, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return time.format(formatter);
    }

    /***
     * 将日期和时间转换为字符串
     * @param dateTime
     * @param pattern
     * @return
     */
    public static String formatLocalDateTime(LocalDateTime dateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    /***
     * 将字符串解析为日期
     * @param dateString
     * @param pattern
     * @return
     */
    public static LocalDate parseLocalDate(String dateString, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(dateString, formatter);
    }

    /***
     * 将字符串解析为时间
     * @param timeString
     * @param pattern
     * @return
     */
    public static LocalTime parseLocalTime(String timeString, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalTime.parse(timeString, formatter);
    }

    /***
     * 将字符串解析为日期和时间
     * @param dateTimeString
     * @param pattern
     * @return
     */
    public static LocalDateTime parseLocalDateTime(String dateTimeString, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    /***
     * 在日期上增加指定天数
     * @param date
     * @param days
     * @return
     */
    public static LocalDate addDays(LocalDate date, int days) {
        return date.plusDays(days);
    }

    /***
     * 在日期上减少指定天数
     * @param date
     * @param days
     * @return
     */
    public static LocalDate subtractDays(LocalDate date, int days) {
        return date.minusDays(days);
    }

    /****
     * 在时间上增加指定小时数
     * @param time
     * @param hours
     * @return
     */
    public static LocalTime addHours(LocalTime time, int hours) {
        return time.plusHours(hours);
    }

    /***
     * 在时间上减少指定小时数
     * @param time
     * @param hours
     * @return
     */
    public static LocalTime subtractHours(LocalTime time, int hours) {
        return time.minusHours(hours);
    }

    /***
     * 在日期和时间上增加指定分钟数
     * @param dateTime
     * @param minutes
     * @return
     */
    public static LocalDateTime addMinutes(LocalDateTime dateTime, int minutes) {
        return dateTime.plusMinutes(minutes);
    }

    /***
     * 在日期和时间上减少指定分钟数
     * @param dateTime
     * @param minutes
     * @return
     */
    public static LocalDateTime subtractMinutes(LocalDateTime dateTime, int minutes) {
        return dateTime.minusMinutes(minutes);
    }

    /***
     * 计算两个日期之间的天数差
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getDaysBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    /***
     * 计算两个日期之间的小时数差
     * @param startDateTime
     * @param endDateTime
     * @return
     */
    public static long getHoursBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return ChronoUnit.HOURS.between(startDateTime, endDateTime);
    }

    /***
     * 计算两个日期之间的分钟数差
     * @param startDateTime
     * @param endDateTime
     * @return
     */
    public static long getMinutesBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return ChronoUnit.MINUTES.between(startDateTime, endDateTime);
    }

    /***
     * 判断日期是否在指定日期范围内
     * @param date
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean isDateInRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
        return date.isAfter(startDate) && date.isBefore(endDate);
    }

    /***
     * 校验两个时间是否相等
     * @param startTime
     * @param endTime
     * @return
     */
    public static Boolean checkTimeEq(LocalTime startTime,LocalTime endTime) {
        String startTimeStr = LocalDateTimeUtils.formatLocalTime(startTime, TILME);
        String currentTimeStr = LocalDateTimeUtils.formatLocalTime(endTime, LocalDateTimeUtils.TILME);
        return startTimeStr.equals(currentTimeStr);
    }

    /****
     * 当前时间之前
     * @param time
     * @param startTime
     * @return
     */
    public static boolean isTimeBefore(LocalTime time, LocalTime startTime) {
        return time.isBefore(startTime);
    }

    /***
     * 时间戳转换时间
     * @param timestamp
     * @return
     */
    public static LocalDateTime timestampToDatetime(long timestamp){
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
