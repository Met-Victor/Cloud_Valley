/***********************************************************************
 * DateUtils.java,v1.0 2023/6/14 20:21 $
 *
 * @author: Victor
 *
 * (c)Copyright 2023 Hxsoft All rights reserved.
 ***********************************************************************/
package cn.com.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 日期模板
 *
 * @author 13016
 * @date 2023/08/30
 */
public class DateUtils {
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
    public static final DateFormat DATE_SHORT_FORMAT = new SimpleDateFormat("MM-dd");
    public static final DateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final DateFormat DATETIME_FORMAT2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final DateFormat DATETIME_SHORT_FORMAT = new SimpleDateFormat("MM-dd HH:mm");
    public static final DateFormat DATETIME_T_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    public static final DateFormat DATETIME_NAME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final DateFormat DATETIME_NAME_S_FORMAT = new SimpleDateFormat("yyyyMMddHHmmssS");
    public static final DateFormat DATE_NAME_FORMAT = new SimpleDateFormat("yyyyMMdd");
    public static final DateFormat YEAR_MONTH_FORMAT = new SimpleDateFormat("yyyyMM");
    public static final DateFormat MONTH_FORMAT = new SimpleDateFormat("MM");
    public static final DateFormat TIME_NAME_FORMAT = new SimpleDateFormat("HHmmss");
    public static final DateFormat HOUR_FORMAT = new SimpleDateFormat("HH:mm");
    public static final DateFormat HOURLY_FORMAT = new SimpleDateFormat("HH:00");
    public static final DateFormat WEEK_FORMAT = new SimpleDateFormat("E(yyyy-MM-dd)");
    public static final DateFormat DATE_WEEK_FORMAT = new SimpleDateFormat("MM-dd(E)");
}
