package com.haozhiyan.zhijian.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 判断当前获取的时间与当前时间是否为三天内的
 *
 * @author lee
 */

public class TimeFormatUitls {

    private static int mYear;
    private static int mMonth;
    private static int mNowDay;
    private static int mNextDay;
    private static String m_month;
    private static String m_day;

    // 设置时间方法
    public static String LaterData(String laterNum) {

        try {
            if (TextUtils.isEmpty(laterNum)) {
                return "";
            }

            final Calendar c = Calendar.getInstance();
            c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

            // 这边定义的年月日变量都要int类型 ， 不要问我为什么
            mYear = c.get(Calendar.YEAR); // 获取当前年份
            mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
            mNowDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码
            LogUtils.e("今日时间===>", mYear + "年" + mMonth + "月" + mNowDay + "日");

            c.set(Calendar.DAY_OF_MONTH, mNowDay + Integer.parseInt(laterNum)); // 延后3天
            mNextDay = c.get(Calendar.DAY_OF_MONTH);
            // 判断延后的日期小于今天的日期，月份加一
            if (mNowDay > mNextDay) {
                mMonth += 1;
            }
            // 判断延后的月份大于本月的月份，月份设置为一月份，年份加一
            if (mMonth > 12) {
                mMonth = 1;
                mYear += 1;
            }
            // 测试今天的日期
            LogUtils.e(laterNum+"天后时间===>", mYear + "年" + mMonth + "月" + mNextDay + "日");
//        // 如果 月份为个位数则加个0在前面
//        if (mMonth < 10) {
//            m_month = "0" + mMonth;
//        } else {
//            m_month = "" + mMonth;
//        }
//        // 如果 天数为个位数则加个0在前面
//        if (mNextDay < 10) {
//            m_day = "0" + mNextDay;
//        } else {
//            m_day = "" + mNextDay;
//        }
//        return m_month + "月" + m_day + "日";
            return mYear + "-" + mMonth + "-" + mNowDay;
        } catch (Exception e) {
            e.printStackTrace();
            return laterNum;
        }
    }

    /**
     * @author lee
     * 获取时间字符串的月日
     */
    public static String format_date(String date) {
        int month = 0, day = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar ca = Calendar.getInstance();
        try {
            ca.setTime(sdf.parse(date));
            month = ca.get(Calendar.MONTH) + 1;
            day = ca.get(Calendar.DAY_OF_MONTH);
            int si = ca.get(Calendar.HOUR_OF_DAY);
        } catch (Exception e) {

            e.printStackTrace();
        }

        return month + "月" + day + "日";

    }

    /**
     * 将时间转化成毫秒
     * 时间格式: yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return
     */
    public static Long timeStrToSecond(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Long second = format.parse(time).getTime();
            return second;
        } catch (Exception e) {

        }

        return -1l;
    }

    /**
     * 秒转化成时间
     * 时间格式: yyyy-MM-dd
     *
     * @param millionSeconds
     * @return
     */
    public static String SecondTotimeStr(String millionSeconds) {
        try {
            Date date = new Date(Long.parseLong(millionSeconds) * 1000);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String tiem = format.format(date);

            return tiem;
        } catch (Exception e) {

        }

        return "";
    }

    /**
     * 秒转化成时间
     * 时间格式: yyyy-MM-dd
     *
     * @param millionS
     * @return
     */
    public static String SecondTotimeStr(long millionS) {
        try {
            Date date = new Date(millionS);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String tiem = format.format(date);

            return tiem;
        } catch (Exception e) {


        }

        return "";
    }

    /**
     * 将时间转化成毫秒
     * 时间格式: yyyy-MM-dd
     *
     * @param time
     * @return
     */
    public static Long dateStrToSecond(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Long second = format.parse(time).getTime();
            return second;
        } catch (Exception e) {

        }

        return -1l;
    }

    /**
     * @param
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     * @author fy.zhang
     */
    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return days + " days " + hours + " hours " + minutes + " minutes "
                + seconds + " seconds ";
    }


    /**
     * 某日起倒计时  返回格式为 x天 或  x时 或  x分 或 x秒
     *
     * @param
     * @return 该毫秒数
     * @author
     */
    public static String formatDuring(String data) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long mss = null;
        try {
            mss = format.parse(data).getTime() - System.currentTimeMillis();

            long days = mss / (1000 * 60 * 60 * 24);
            long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
            long seconds = (mss % (1000 * 60)) / 1000;

            if (days <= 0) {
                if (hours <= 0) {
                    if (minutes <= 0) {
                        if (seconds <= 0) {
                            return "";
                        } else {
                            return seconds + "秒";
                        }
                    } else {
                        return minutes + "分";
                    }
                } else {
                    return hours + "时";
                }

            } else {
                return days + "天";
            }


        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 某日起倒计时  返回格式为 x天 x时x分  x秒
     *
     * @param
     * @return 该毫
     * @author
     */
    public static String formatDuring2(String data) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long mss = null;
        try {
            mss = format.parse(data).getTime() - System.currentTimeMillis();

            long days = mss / (1000 * 60 * 60 * 24);
            long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
            long seconds = (mss % (1000 * 60)) / 1000;


            return days + "天" + hours + "时" + minutes + "分" + seconds + "秒";


        } catch (Exception e) {
            return "";
        }
    }


    /**
     * @param begin 时间段的开始
     * @param end   时间段的结束
     * @return 输入的两个Date类型数据之间的时间间格用* days * hours * minutes * seconds的格式展示
     * @author fy.zhang
     */
    public static String formatDuring(Date begin, Date end) {
        return formatDuring(end.getTime() - begin.getTime());

    }


    /**
     * 是否在 期间
     *
     * @param starttime 开始时间  格式  yyyy-MM-dd HH:mm:ss
     * @param endtime   结束时间  格式  yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static boolean isActivityShow(String starttime, String endtime) {
        long nowtime = System.currentTimeMillis();
        long start = timeStrToSecond(starttime);
        long end = timeStrToSecond(endtime);
        if (start <= nowtime && nowtime < end) {
            return true;
        }
        return false;
    }


}

