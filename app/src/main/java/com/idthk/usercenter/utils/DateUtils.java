package com.idthk.usercenter.utils;

import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DateUtils {
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_FORMAT1 = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT2 = "MM/dd/yyyy";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String TIME_NO_FORMAT = "HHmmss";
    public static final String TIME_FORMAT2 = "HH:mm";
    public static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmss";
    public static final String MILLI_TIMESTAMP_FORMAT = "yyMMddHHmmssSSS";//精确到毫秒时间戳
    public static final String ZEROTIME_FORMAT = "yyyy-MM-dd 00:00:00";    //当天零点
    public static final String ENDTIME_FORMAT = "yyyy-MM-dd 23:59:59";    //当天的最后一秒
    public static final String M2_D2_H2_M2_FORMAT = "MM-dd HH:mm";
    public static final String YYYY = "yyyy";
    public static final String MM = "MM";
    public static final String DD = "dd";
    public static final String MONTH_FORMAT = "yyyyMM";
    public static final String MONTH_FORMAT2 = "yyyy-MM";

    public static final String dateFormatSimpleReg = "[0-9]{4}-[0-9]{2}-[0-9]{2}";//匹配yyyy-MM-dd格式
    public static final String dateFormatReg = "[0-9]{4}-[0-9]{2}-[0-9]{2}\\s[0-9]{2}\\:[0-9]{2}\\:[0-9]{2}";//匹配yyyy-MM-dd HH:mm:ss格式


    /**
     * 把日期转换成时间戳
     * @param s
     * @return
     * @throws ParseException
     */
    public static String dateToStamp(String s) throws ParseException{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /**
     * 把时间戳转换成时间
     * @param s
     * @return
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }


    /**
     * 格式化日期
     * @param dateStr
     * @param sourceFmt 原格式
     * @param targetFmt 目标格式
     * @return
     */
    public static String formatDate(String dateStr,String sourceFmt,String targetFmt){
        try {
            SimpleDateFormat sourceSdf = new SimpleDateFormat(sourceFmt);
            SimpleDateFormat targetSdf = new SimpleDateFormat(targetFmt);
            return targetSdf.format(sourceSdf.parse(dateStr));
        }catch (Exception e) {
            return dateStr;
        }
    }
    /**
     * 功能描述：获取当前系统时间
     *
     * @param parse 时间格式
     * @return
     * @throws ParseException <p>
     *                        修改历史 ：(修改人，修改时间，修改原因/内容)
     *                        </p>
     * @author bingzhong.qin
     * <p>
     * 创建日期 ：2014-1-2 下午2:35:54
     * </p>
     */
    public static Date getCurrentDate(String parse) throws ParseException {

        if (StringUtils.isBlank(parse)) {
            parse = DATETIME_FORMAT;
        }
        SimpleDateFormat df = new SimpleDateFormat(parse);
        return parse(df.format(new Date()), parse);
    }

    /**
     * long转换成date
     *
     * @param currentTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static Date longToDate(long currentTime, String formatType) {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    public static String dateToString(Date data, String formatType) {
        if (formatType == null)
            formatType = DATETIME_FORMAT;
        return new SimpleDateFormat(formatType).format(data);
    }


    /**
     * 功能描述：string 转换成date，默认模式
     *
     * @param source
     * @return <p>
     * 修改历史 ：(修改人，修改时间，修改原因/内容)
     * </p>
     * @author bingzhong.qin
     * <p>
     * 创建日期 ：2013-12-31 下午2:15:31
     * </p>
     */
    public static Date parse(String source) {
        try {
            SimpleDateFormat format = new SimpleDateFormat();
            if (source.indexOf(":") > 0)
                format.applyPattern(DATETIME_FORMAT);
            else {
                format.applyPattern(DATE_FORMAT);
            }
            return format.parse(source.trim());
        } catch (ParseException e) {
        }
        return null;
    }


    /**
     * 功能描述：string 转换成date,自定义格式
     *
     * @param source
     * @param formatString
     * @return <p>
     * 修改历史 ：(修改人，修改时间，修改原因/内容)
     * </p>
     * @author bingzhong.qin
     * <p>
     * 创建日期 ：2013-12-31 下午2:14:30
     * </p>
     */
    public static Date parse(String source, String formatString) {
        try {
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern(formatString.trim());
            return format.parse(source.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 功能描述：date转换成string
     *
     * @param date
     * @param formatString
     * @return <p>
     * 修改历史 ：(修改人，修改时间，修改原因/内容)
     * </p>
     * @author bingzhong.qin
     * <p>
     * 创建日期 ：2013-12-31 下午2:11:42
     * </p>
     */
    public static String format(Date date, String formatString) {
        SimpleDateFormat format = null;
        if (date == null)
            return null;
        if (StringUtils.isBlank(formatString)) {
            formatString = DATETIME_FORMAT;
        }
        format = new SimpleDateFormat(formatString);
        return format.format(date);
    }

    public static String format(long date, String formatString) {
        SimpleDateFormat format;
        if (StringUtils.isBlank(formatString)) {
            formatString = DATETIME_FORMAT;
        }
        format = new SimpleDateFormat(formatString);
        return format.format(date);
    }

    public static String format(String date, String formatString) {

        return format(stringToDate(date), formatString);
    }


    // 获取上月月份
    public static int getPreMonth() {
        Calendar calendar = Calendar.getInstance();
        //calendar.add(calendar.MONTH,-1);
        return calendar.get(calendar.MONTH);
    }


    /***************************************************************************
     * dateStrToLong
     *
     * @param dateStr 日期字符串yyyy-mm-dd
     * @return long theDay
     * @see 2005-11-15
     **************************************************************************/
    public static long dateStrToLong(String dateStr) {
        long theDate = System.currentTimeMillis();
        Date thisDate = stringToDate(dateStr);
        if (thisDate != null) {
            theDate = thisDate.getTime();
        }
        return theDate;
    }

    /***************************************************************************
     * stringToDate 把字符型"yyyy-MM-dd hh:mm:ss"转换成日期型
     *
     * @param s String 需要转换的日期时间字符串
     * @return theDate Date
     **************************************************************************/
    public static Date stringToDate(String s) {
        Date theDate = null;
        try {
            if (s != null) {
                SimpleDateFormat dateFormatter = new SimpleDateFormat(DATETIME_FORMAT);
                theDate = dateFormatter.parse(s);
            } else {
                theDate = null;
            }
        } catch (ParseException pe) {
            // plogger.error(e); e.printStackTrace();
            theDate = null;
        }
        return theDate;
    }

    public static long dateToLong(Date date) {
        return date.getTime();
    }


    public static Date stringToDate(String s, String format) {
        Date theDate = null;
        if (format == null) {
            format = DATETIME_FORMAT;
        }
        try {
            if (s != null) {
                SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
                theDate = dateFormatter.parse(s);
            } else {
                theDate = null;
            }
        } catch (ParseException pe) {
            // plogger.error(e); e.printStackTrace();
            theDate = null;
        }
        return theDate;
    }

    public static Long stringToLong(String time, String format) {
        if (null == format) {
            format = DateUtils.DATETIME_FORMAT;
        }
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
            return dateFormatter.parse(time).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public static Long stringToLong(String time) {
        if (StringUtils.isEmpty(time)) {
            return 0L;
        }
        String format = null;
        Pattern pattern = Pattern.compile(dateFormatReg);
        Matcher matcher = pattern.matcher(time);
        if (matcher.find()) {
            format = DATETIME_FORMAT;
        }
        if (StringUtils.isEmpty(format)) {
            pattern = Pattern.compile(dateFormatSimpleReg);
            matcher = pattern.matcher(time);
            if (matcher.find()) {
                format = DATE_FORMAT;
            }
        }
        if (StringUtils.isEmpty(format)) {
            Log.e("stringToLong", "日期字符串格式错误");
            return 0L;
        }
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
            return dateFormatter.parse(time).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 把时间戳类型转换为Date
     *
     * @param time
     * @return
     */
    public static String longToString(long time, String format) {
        try {
            Date date = new Date();
            date.setTime(time);
            if (StringUtils.isEmpty(format)) {
                format = DATETIME_FORMAT;
            }
            SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
            return dateFormatter.format(date);
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * 获取指定日期是周几
     *
     * @param date
     * @return
     */
    public static String getWeekStrByDate(Date date) {
        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }

    public static int getWeekByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index <= 0) {
            week_index = 7;
        }
        return week_index;
    }

    public static String currPK() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String d1 = sdf.format(d);

        try {
            Thread.sleep(50);/* 沉睡50毫秒 */
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return d1;

    }

    /**
     * 获取系统当前秒级时间戳
     *
     * @return
     */
    public static long getSecondStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 传入时间跟当前时间比较
     *
     * @param dateStr
     * @return
     */
    public static int compareDate(String dateStr) {
        DateFormat df = new SimpleDateFormat(DATETIME_FORMAT);
        Date newDate = new Date();
        Date paramDate;
        try {
            paramDate = df.parse(dateStr);
            return newDate.compareTo(paramDate);
        } catch (ParseException e) {
            return -2;
        }
    }

    public static int compareDate(String dateStr,String dateStr1) {
        DateFormat df = new SimpleDateFormat(MONTH_FORMAT2);
        Date paramDate;
        Date paramDate1;
        try {
            paramDate = df.parse(dateStr);
            paramDate1 = df.parse(dateStr1);
            return paramDate.compareTo(paramDate1);
        } catch (ParseException e) {
            return -2;
        }
    }

    /**
     * 验证时间格式是否合格
     *
     * @param timeStr     时间字符串
     * @param timeFormart 需要验证的时间格式
     * @return
     */
    public static boolean isValidDate(String timeStr, String timeFormart) {
        boolean dateFlag = false;
        try {
            // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
            DateFormat dateFormat = new SimpleDateFormat(timeFormart);
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            dateFormat.setLenient(false);
            dateFormat.parse(timeStr);
            dateFlag = true;
        } catch (Exception e) {
            dateFlag = false;
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
        }
        return dateFlag;
    }

    /**
     * 获取当天零点
     *
     * @return
     * @throws Exception
     */
    public static Date getZeroTimeOfCurDate() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(ZEROTIME_FORMAT);
        String s = sdf.format(new Date());
        return sdf.parse(s);
    }

    public static Date getZeroTimeOfYesterDay() throws Exception {
        // 获取昨天这时候的日期时间
        Date nowOfYesterday = addSeconds(new Date(), -24 * 60 * 60);
        SimpleDateFormat sdf = new SimpleDateFormat(ZEROTIME_FORMAT);
        String s = sdf.format(nowOfYesterday);
        return sdf.parse(s);
    }

    /**
     * 获取当前时间
     *
     * @return
     * @throws Exception
     */
    public static Date getCurTimeOfCurDate() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
        String s = sdf.format(new Date());
        return sdf.parse(s);
    }

    public static Date getTime(String timeStr) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
        return sdf.parse(timeStr);
    }

    public static String getTimeStrByPattern(Date time, String dateFormat) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(time);
    }

    public static Date getTimeByPattern(String time, String dateFormat) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.parse(time);
    }

    public static Date getDateByPattern(Date time, String cutFormat) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(cutFormat);
        String newDateStr = sdf.format(time);
        sdf = new SimpleDateFormat(DATETIME_FORMAT);
        return sdf.parse(newDateStr);
    }


    public static String getCurDate() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT);
        String s = sdf.format(new Date());
        return s;
    }

    public static Date getEndTimeOfCurDate() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(ENDTIME_FORMAT);
        String s = sdf.format(new Date());
        sdf = new SimpleDateFormat(DATETIME_FORMAT);
        return sdf.parse(s);
    }

    /**
     * 获取当前日期为周几
     *
     * @return
     * @throws Exception
     */
    public static int getDayOfWeek() throws Exception {
        Calendar now = Calendar.getInstance();
        //一周第一天是否为星期天
        boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
        //获取周几
        int weekDay = now.get(Calendar.DAY_OF_WEEK);
        //若一周第一天为星期天，则-1
        if (isFirstSunday) {
            weekDay = weekDay - 1;
            if (weekDay == 0) {
                weekDay = 7;
            }
        }
        return weekDay;
    }

    /**
     * 判断当前日期是星期几<br>
     * <br>
     *
     * @param pTime 修要判断的时间<br>
     * @return dayForWeek 判断结果<br>
     * @Exception 发生异常<br>
     */
    public static int getDayOfWeek(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }


    /**
     * Adds a number of years to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date   the date, not null
     * @param amount the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addYears(Date date, int amount) {
        return add(date, Calendar.YEAR, amount);
    }

    //-----------------------------------------------------------------------

    /**
     * Adds a number of months to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date   the date, not null
     * @param amount the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addMonths(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    //-----------------------------------------------------------------------

    /**
     * Adds a number of weeks to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date   the date, not null
     * @param amount the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addWeeks(Date date, int amount) {
        return add(date, Calendar.WEEK_OF_YEAR, amount);
    }

    //-----------------------------------------------------------------------

    /**
     * Adds a number of days to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date   the date, not null
     * @param amount the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addDays(Date date, int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    //-----------------------------------------------------------------------

    /**
     * Adds a number of hours to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date   the date, not null
     * @param amount the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addHours(Date date, int amount) {
        return add(date, Calendar.HOUR_OF_DAY, amount);
    }

    //-----------------------------------------------------------------------

    /**
     * Adds a number of minutes to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date   the date, not null
     * @param amount the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addMinutes(Date date, int amount) {
        return add(date, Calendar.MINUTE, amount);
    }

    //-----------------------------------------------------------------------

    /**
     * Adds a number of seconds to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date   the date, not null
     * @param amount the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addSeconds(Date date, int amount) {
        return add(date, Calendar.SECOND, amount);
    }

    //-----------------------------------------------------------------------

    /**
     * Adds a number of milliseconds to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date   the date, not null
     * @param amount the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addMilliseconds(Date date, int amount) {
        return add(date, Calendar.MILLISECOND, amount);
    }


    /**
     * Adds to a date returning a new object.
     * The original date object is unchanged.
     *
     * @param date          the date, not null
     * @param calendarField the calendar field to add to
     * @param amount        the amount to add, may be negative
     * @return the new date object with the amount added
     * @throws IllegalArgumentException if the date is null
     * @deprecated Will become privately scoped in 3.0
     */
    public static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    /**
     * 获取当前时间延后day天后的时间
     *
     * @param datetime
     * @param day
     * @return
     */
    public static String getDateAfterDay(String datetime, int day) {
        DateFormat df = new SimpleDateFormat(DATETIME_FORMAT);
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(df.parse(datetime));
            c.add(Calendar.DATE, day);
            Date d = c.getTime();
            return df.format(d);
        } catch (ParseException e) {
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取当前时间延后day天后的时间
     *
     * @param datetime
     * @param day
     * @param fmt
     * @return
     */
    public static String getDateAfterDay(Date datetime, int day, String fmt) {
        if (StringUtils.isEmpty(fmt)) {
            fmt = DATETIME_FORMAT;
        }
        DateFormat df = new SimpleDateFormat(fmt);
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(datetime);
            c.add(Calendar.DATE, day);
            Date d = c.getTime();
            return df.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前时间延后day天后的时间
     *
     * @param datetime
     * @param day
     * @return
     */
    public static String getDateAfterDay(Date datetime, int day) {
        DateFormat df = new SimpleDateFormat(DATETIME_FORMAT);
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(datetime);
            c.add(Calendar.DATE, day);
            Date d = c.getTime();
            return df.format(d);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取当前时间延后day天后的时间
     *
     * @param datetime
     * @param day
     * @return
     */
    public static Date getDate(Date datetime, int day) {
        DateFormat df = new SimpleDateFormat(DATETIME_FORMAT);
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(datetime);
            c.add(Calendar.DATE, day);
            return c.getTime();
        } catch (Exception e) {
        }
        return null;
    }


    /**
     * 获取当前时间延后day天后的时间
     *
     * @param datetime
     * @param day
     * @return
     */
    public static Long getDateAfterDayReLong(Date datetime, int day) {
        DateFormat df = new SimpleDateFormat(DATETIME_FORMAT);
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(datetime);
            c.add(Calendar.DATE, day);
            Date d = c.getTime();
            return d.getTime();
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取当前时间延后hour小时后的时间
     *
     * @param datetime
     * @return
     */
    public static String getDateAfterHour(String datetime, int hour) {
        DateFormat df = new SimpleDateFormat(TIME_FORMAT);
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(df.parse(datetime));
            c.add(Calendar.HOUR, hour);
            Date d = c.getTime();
            return df.format(d);
        } catch (ParseException e) {
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 将日期字符串替换成新的格式
     *
     * @param dateTime
     * @param oldFmtStr
     * @return
     */
    public static String convertFmtDateTimeStr(String dateTime, String oldFmtStr, String newFmtStr) {
        if (StringUtils.isEmpty(oldFmtStr)) {
            oldFmtStr = DATETIME_FORMAT;
        }
        if (StringUtils.isEmpty(newFmtStr)) {
            newFmtStr = DATETIME_FORMAT1;
        }
        try {
            DateFormat df = new SimpleDateFormat(oldFmtStr);
            dateTime = new SimpleDateFormat(newFmtStr).format(df.parse(dateTime));
        } catch (ParseException e) {
        } catch (Exception e) {
        }
        return dateTime;
    }

    /**
     * 获取日期
     *
     * @param date
     * @return
     */
    public static String getDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(date);
    }

    public static Date getDateOfDateTime(Date date) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        String dateStr = format.format(date);
        return format.parse(dateStr);
    }

    public static Date getTimeOfDateTime(Date date) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT2);
        String dateStr = format.format(date);
        return format.parse(dateStr);
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT);
        return format.format(date);
    }

    /**
     * 计算传入日期时间跟当前日期时间比较
     * 如果传入日期>当前日期=false
     *
     * @param dtStr
     * @return
     */
    public static boolean validDateTime(String dtStr) {
        SimpleDateFormat format = new SimpleDateFormat(DATETIME_FORMAT);
        try {
            if (!StringUtils.isBlank(dtStr)) {
                long reqDateTime = format.parse(dtStr).getTime();
                long nowDateTime = new Date().getTime();
                if ((reqDateTime - nowDateTime) <= 0) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }


//	public static void main(String[] args) {
//		Calendar now = Calendar.getInstance();
//		//一周第一天是否为星期天
//		boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
//		//获取周几
//		int weekDay = now.get(Calendar.DAY_OF_WEEK);
//		System.out.println(weekDay);
//		//若一周第一天为星期天，则-1
//		if(isFirstSunday){
//		  weekDay = weekDay - 1;
//		  if(weekDay == 0){
//		    weekDay = 7;
//		  }
//		}
//		System.out.println(weekDay);
//		System.out.println(Calendar.THURSDAY);
//		System.out.println(Calendar.SATURDAY);
//	}

    /**
     * 验证时间格式是否合格
     *
     * @param timeStr     时间字符串
     * @param timeFormart 需要验证的时间格式
     * @return
     */
    public static Date validDateStr(String timeStr, String timeFormart) {
        Date date = new Date();
        try {
            // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
            DateFormat dateFormat = new SimpleDateFormat(timeFormart);
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            dateFormat.setLenient(false);
            date = dateFormat.parse(timeStr);
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
        }
        return date;
    }


    /**
     * 计算开始时间与结束时间的时间间隔的绝对值，单位为分
     *
     * @return
     * @Function: com.idcq.idianmgr.quartz.HandleOrderStatusJob.computeIntervalTime
     * @Description:
     * @version:v1.0
     * @author:shengzhipeng
     * @date:2015年8月1日 下午2:29:00
     * <p>
     * Modification History:
     * Date            Author       Version     Description
     * -----------------------------------------------------------------
     * 2015年8月1日    shengzhipeng       v1.0.0         create
     */
    public static int computeIntervalTime(Date startTime, Date endTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        long start = calendar.getTimeInMillis();
        calendar.setTime(endTime);
        long end = calendar.getTimeInMillis();
        return (int) Math.abs((end - start) / 60000);
    }

    /**
     * 某月第一天
     *
     * @return
     */
    public static String getFirstDayInMonth(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date theDate = calendar.getTime();

        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        return str.toString();

    }

    /**
     * 某月最后一天
     *
     * @return
     */
    public static String getLastDayInMonth(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date theDate = calendar.getTime();
        String s = df.format(theDate);
        StringBuffer str = new StringBuffer().append(s).append(" 23:59:59");
        return str.toString();

    }


    public static String getCurMonth() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        return df.format(date);
    }

    public static String getCurMonth(String format) {
        if (format == null) {
            return getCurMonth();
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    public static String getCurMonth(int month) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM--dd");
        Date date = new Date();
        date.setMonth(month);
        date.setDate(1);
        return df.format(date);
    }


    public static String getCurMonthLast(int month) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM--dd");
        Date date = new Date();
        date.setMonth(month);
        date.setDate(31);
        return df.format(date);
    }


    public static String diyConvert(String format, long time) {
        SimpleDateFormat sd = new SimpleDateFormat(format);
        return sd.format(new Date(time));
    }

    /**
     * 将日期时间字符串转换为Long
     *
     * @param format
     * @param time
     * @return
     */
    public static Long formatToLong(String format, String time) {
        try {
            SimpleDateFormat sd = new SimpleDateFormat(format);
            return sd.parse(time).getTime();
        } catch (Exception e) {
            Log.e("dd", "时间转换成long异常： " + e.getMessage());
        }
        return null;
    }


    /**
     * 获得指定日期的前N天
     *
     * @param beforeDay 前N天
     * @return
     * @throws Exception
     * @auhor dengjh
     */
    public static String getSpecifiedDayBefore(Integer beforeDay) {
        Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
        calendar.add(Calendar.DATE, -beforeDay);    //得到前一天
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    /**
     * 获取当前时间的年字符串
     *
     * @return
     */
    public static String getNowDateYear() {
        return new SimpleDateFormat(YYYY).format(Calendar.getInstance().getTime());
    }

    /**
     * 获取当前时间的月字符串
     *
     * @return
     */
    public static String getNowDateMonth() {
        return new SimpleDateFormat(MM).format(Calendar.getInstance().getTime());
    }

    /**
     * 获取当前时间的日字符串
     *
     * @return
     */
    public static String getNowDateDay() {
        return new SimpleDateFormat(DD).format(Calendar.getInstance().getTime());
    }

    public static void main(String[] args) {
//		long d1 = getDateAfterDayReLong(new Date(), 7);
//		long d2 = getDateAfterDayReLong(new Date(), -7);
//		System.out.println(d1);
//		System.out.println(d2);
//		System.out.println(longToString(d1,DATETIME_FORMAT));
//		System.out.println(longToString(d2,DATETIME_FORMAT));


        System.out.println(getCurMonth());
        System.out.println(getCurMonth(MONTH_FORMAT));
    }


    /**
     * 获取当前时间延后month月第一天的时间
     *
     * @param month
     * @return
     */
    public static String getMontAfterFirsetDay(int month) {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        try {
            Date datetime = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(datetime);
            c.add(Calendar.MONTH, month);
            c.add(Calendar.DAY_OF_MONTH, 1);
            Date d = c.getTime();
            return df.format(d);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取当前时间延后month月最后一天的时间
     *
     * @param month
     * @return
     */
    public static String getMontAfterLastDay(int month) {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        try {
            Date datetime = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(datetime);
            c.add(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, 31);
            Date d = c.getTime();
            return df.format(d);
        } catch (Exception e) {
        }
        return null;
    }


}