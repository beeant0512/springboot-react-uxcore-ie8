package com.xstudio.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author xiaobiao
 * @version 1
 * @date 2017/10/2
 */
public class DateUtil {
    /**
     * 日期格式化字符串：yyyy-MM-dd
     */
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_DATE_NODASH = "yyyyMMdd";
    /**
     * 时间格式化字符串：yyyy-MM-dd HH:mm:ss
     */
    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss.S";
    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    private DateUtil() {
        return;
    }

    public static void sleep(Integer seconds) {
        try {
            Thread.sleep((seconds * 1000));
        } catch (Exception e) {
            logger.error("休眠 {}s 错误{}", seconds, e);
        }
    }

    public static String yesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return new SimpleDateFormat(FORMAT_DATE).format(cal.getTime());
    }

    public static String nowTimeString() {
        return format(new Date(), "yyyy-MM-dd hh:mm:ss.SSS");
    }

    /**
     * 返回 2017_07_12 12_23_40格式的日期字符串
     *
     * @return String
     */
    public static String nowTimeStringForFileName() {
        return format(new Date(), "yyyy_MM_dd hh_mm_ss");
    }

    public static long nowTimestamp() {
        Date date = new Date();
        return date.getTime();
    }

    public static String nowTimestampString() {
        Date date = new Date();
        return String.valueOf(date.getTime());
    }

    public static String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date.getTime());
    }

    public static String format(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").format(date.getTime());
    }

    public static String formatDate(Date date) {
        return new SimpleDateFormat(FORMAT_DATE).format(date.getTime());
    }

    public static String plusDays(String date, int days) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(FORMAT_DATE);
        LocalDate parseTime = LocalDate.parse(date, format);
        return parseTime.plusDays(days).format(format);
    }

    public static boolean isBefore(String date, String dateEnd) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(FORMAT_DATE);
        LocalDate dateBefore = LocalDate.parse(date, format);
        LocalDate dateAfter = LocalDate.parse(dateEnd, format);
        return dateBefore.isBefore(dateAfter);
    }

    public static Date paraseDate(String date) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        DateFormat format = new SimpleDateFormat(FORMAT_DATE);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            logger.error("日期字符串转换Date类型错误 {}", date);
        }

        return null;
    }

    public static String[] daysBetween(Date dateEnd, Date dateBegin) {
        Integer integer = diffDays(dateEnd, dateBegin);
        if (null == integer) {
            return new String[]{};
        }
        if (integer == 0) {
            return new String[]{format(dateEnd, FORMAT_DATE)};
        }

        String[] days = new String[integer + 1];
        String dateBeginString = format(dateBegin, FORMAT_DATE);
        days[0] = dateBeginString;
        Integer index = 1;
        while (index <= integer) {
            dateBeginString = plusDays(dateBeginString, 1);
            days[index] = dateBeginString;
            index++;
        }

        return days;
    }

    public static Integer diffDays(Date dateEnd, Date dateBegin) {
        Long diff = diff(dateEnd, dateBegin);
        if (null == diff) {
            return null;
        }
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return Math.toIntExact(diffDays);
    }

    public static Long diff(Date dateEnd, Date dateBegin) {
        if (null == dateEnd || dateBegin == null) {
            return null;
        }
        long endTime = dateEnd.getTime();
        long beginTime = dateBegin.getTime();
        return endTime - beginTime;
    }

    /**
     * 获得本月第一天
     *
     * @param date
     * @return
     */
    public static String firstDateOfMonth(Date date, String pattern) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return new SimpleDateFormat(pattern).format(cal.getTime());
    }

    /**
     * 获得本月最后一天
     *
     * @param date
     * @return
     */
    public static String lastDateOfMonth(Date date, String pattern) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return new SimpleDateFormat(pattern).format(cal.getTime());
    }

    /**
     * 某天开始时间
     *
     * @param date 例如： 2017-08-01
     * @return 2017-08-01 00:00:00
     * @throws ParseException
     */
    public static Date dayBegin(String date) throws ParseException {
        return new SimpleDateFormat(FORMAT_DATE_TIME).parse(date + " 00:00:00.0");
    }

    /**
     * 某天结束时间
     *
     * @param date 例如： 2017-08-01
     * @return 2017-08-01 23:59:59
     * @throws ParseException
     */
    public static Date dayEnd(String date) throws ParseException {
        return new SimpleDateFormat(FORMAT_DATE_TIME).parse(date + " 23:59:59.999");
    }

    public static Integer idcardToAge(String idcard) {
        int leh = idcard.length();
        String dates;
        if (leh == 18) {
            dates = idcard.substring(6, 10);
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            String year = df.format(new Date());
            return Integer.parseInt(year) - Integer.parseInt(dates);
        } else {
            dates = idcard.substring(6, 8);
            return Integer.parseInt(dates);
        }
    }

    public static Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(5, num);
        return startDT.getTime();
    }
}
