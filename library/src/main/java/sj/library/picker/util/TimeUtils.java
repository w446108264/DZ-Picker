package sj.library.picker.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by sj on 2016/11/27.
 */

public class TimeUtils {

    private final static String DATE_FORMAT = "yyyy-MM-dd";
    private final static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取两个Date之间的天数的列表
     * @param first
     * @param second
     * @return
     * @author lenghao
     * @createTime 2008-8-5 下午01:57:09
     */
    public static List<Date> getDaysListBetweenDates(java.util.Date first, java.util.Date second) {
        List<Date> dateList = new ArrayList<>();
        Date d1 = getFormatDateTime(getFormatDate(first), DATE_FORMAT);
        Date d2 = getFormatDateTime(getFormatDate(second), DATE_FORMAT);
        if (d1.compareTo(d2) > 0) {
            return dateList;
        }
        do {
            dateList.add(d1);
            d1 = getDateBeforeOrAfter(d1, 1);
        } while (d1.compareTo(d2) <= 0);
        return dateList;
    }

    /**
     * 得到格式化后的日期，格式为yyyy-MM-dd，如2006-02-15
     *
     * @param currDate 要格式化的日期
     * @return String 返回格式化后的日期，默认格式为为yyyy-MM-dd，如2006-02-15
     * @see #getFormatDate(java.util.Date, String)
     */
    public static String getFormatDate(java.util.Date currDate) {
        return getFormatDate(currDate, DATE_FORMAT);
    }

    /**
     * 根据格式得到格式化后的日期
     *
     * @param currDate 要格式化的日期
     * @param format   日期格式，如yyyy-MM-dd
     * @return String 返回格式化后的日期，格式由参数<code>format</code>
     * 定义，如yyyy-MM-dd，如2006-02-15
     * @see java.text.SimpleDateFormat#format(java.util.Date)
     */
    public static String getFormatDate(java.util.Date currDate, String format) {
        if (currDate == null) {
            return "";
        }
        SimpleDateFormat dtFormatdB;
        try {
            dtFormatdB = new SimpleDateFormat(format);
            return dtFormatdB.format(currDate);
        } catch (Exception e) {
            dtFormatdB = new SimpleDateFormat(DATE_FORMAT);
            try {
                return dtFormatdB.format(currDate);
            } catch (Exception ex) {
            }
        }
        return null;
    }

    /**
     * 得到格式化后的时间
     * @param currDate 要格式化的时间
     * @param format   时间格式，如yyyy-MM-dd HH:mm:ss
     * @return Date 返回格式化后的时间，格式由参数<code>format</code>定义，如yyyy-MM-dd
     * HH:mm:ss
     * @see java.text.SimpleDateFormat#parse(java.lang.String)
     */
    public static Date getFormatDateTime(String currDate, String format) {
        if (currDate == null) {
            return null;
        }
        SimpleDateFormat dtFormatdB ;
        try {
            dtFormatdB = new SimpleDateFormat(format);
            return dtFormatdB.parse(currDate);
        } catch (Exception e) {
            dtFormatdB = new SimpleDateFormat(TIME_FORMAT);
            try {
                return dtFormatdB.parse(currDate);
            } catch (Exception ex) {
            }
        }
        return null;
    }

    /**
     * 得到日期的前或者后几天
     *
     * @param iDate 如果要获得前几天日期，该参数为负数； 如果要获得后几天日期，该参数为正数
     * @return Date 返回参数<code>curDate</code>定义日期的前或者后几天
     * @see java.util.Calendar#add(int, int)
     */
    public static Date getDateBeforeOrAfter(Date curDate, int iDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        cal.add(Calendar.DAY_OF_MONTH, iDate);
        return cal.getTime();
    }

    /**
     * 第二个日期距离第一个日期的天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int diffDateWithDay(Date startDate, Date endDate) {
        Date d1 = getFormatDateTime(getFormatDate(startDate), DATE_FORMAT);
        Date d2 = getFormatDateTime(getFormatDate(endDate), DATE_FORMAT);
        Long mils = (d2.getTime() - d1.getTime()) / (86400000);
        return mils.intValue();
    }

    /**
     * 判断是否是同一天
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean isSameDay(Date startDate, Date endDate) {
        return diffDateWithDay(startDate, endDate) == 0;
    }

    /**
     * 获得给定时间若干秒以前或以后的日期的标准格式。
     *
     * @param curDate
     * @param seconds
     * @return curDate
     */
    public static Date getSpecifiedDateTimeBySeconds(Date curDate, int seconds) {
        long time = (curDate.getTime() / 1000) + seconds;
        curDate.setTime(time * 1000);
        return curDate;
    }

    /**
     * 寻找当前时间的下一个整点 进制为 10 分钟
     *
     * @return
     */
    public static Date getNextNowTime(Date startDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        // 分 进制 + 1
        int minute = calendar.get(Calendar.MINUTE);
        int minuteNext = (int) Math.ceil(minute / (float) 10);
        if (minuteNext < 6) {
            calendar.set(Calendar.MINUTE, minuteNext * 10);
            return calendar.getTime();
        }
        calendar.set(Calendar.MINUTE, 0);

        // 小时 进制 + 1
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour < 23) {
            calendar.set(Calendar.HOUR_OF_DAY, hour + 1);
            return calendar.getTime();
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        // 天 进制 + 1
        // 获取24小时之后的Date
        Date nextDay = calendar.getTime();
        long time = (nextDay.getTime() / 1000) + 86400;
        nextDay.setTime(time * 1000);
        return nextDay;
    }
}
