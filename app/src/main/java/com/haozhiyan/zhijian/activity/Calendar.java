package com.haozhiyan.zhijian.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.haibin.calendarview.CalendarView;
import com.haozhiyan.zhijian.R;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class Calendar extends BaseActivity2 implements
        CalendarView.OnCalendarInterceptListener,
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnMonthChangeListener,
        CalendarView.OnYearChangeListener {
    private int mode;

    TextView mTextMonthDay;

    TextView mTextYear;

    TextView mTextLunar;

    TextView mTextCurrentDay;

    private int mYear;

    CalendarView mCalendarView;
    com.haibin.calendarview.Calendar selectDate;
    public static final int SELECTDATE = 1152;

    public static final int NORMAL_MODE = 0;
    public static final int INTERCEPT_MODE = 1;

    private String[] mDate;
    private String[] limitDate;
    private int limitCode;
    public static final int LIMiT_BEFORE = 0; //在限制日期之前的能被选择
    public static final int LIMiT_AFTER = 1; //在限制日期之后的 能被选择


    /**
     * @param activity
     * @param dateTime    传入进来的时间格式:  yyyy-MM-dd
     * @param requestCode
     */
    public static void check(Activity activity, String dateTime, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity, Calendar.class);
        Bundle bundle = new Bundle();
        bundle.putInt("mode", NORMAL_MODE);
        bundle.putString("time", dateTime);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * @param fragment
     * @param dateTime    传入进来的时间格式:  yyyy-MM-dd
     * @param requestCode
     */
    public static void check(Fragment fragment, String dateTime, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(fragment.getContext(), Calendar.class);
        Bundle bundle = new Bundle();
        bundle.putInt("mode", NORMAL_MODE);
        bundle.putString("time", dateTime);
        intent.putExtras(bundle);
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 限时选择模式
     *
     * @param activity
     * @param dateTime    传入进来的时间格式:  yyyy-MM-dd
     * @param limitDate   限制的时间点 时间格式:  yyyy-MM-dd
     * @param requestCode
     */
    public static void timeModecheck(Activity activity, String dateTime, String limitDate, int limitCode, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity, Calendar.class);
        Bundle bundle = new Bundle();
        bundle.putInt("mode", INTERCEPT_MODE);
        bundle.putInt("limitCode", limitCode);
        bundle.putString("time", dateTime);
        bundle.putString("limitDate", limitDate);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }


    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_calendar;
    }

    @Override
    protected int getTitleBarType() {
        return TITLEBAR_DEFAULT;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        setTitleText("选择日期");
        setAndroidNativeLightStatusBar(true);


        mTextMonthDay = (TextView) findViewById(R.id.tv_month_day);
        mTextYear = (TextView) findViewById(R.id.tv_year);
        mTextLunar = (TextView) findViewById(R.id.tv_lunar);
        mTextCurrentDay = (TextView) findViewById(R.id.tv_current_day);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(mYear));
            }
        });
        findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectCalendar", selectDate);
                intent.putExtra("bundle", bundle);
                setResult(1001, intent);
                finish();
//                mCalendarView.scrollToCurrent();
            }
        });

        try {
            mode = getIntent().getExtras().getInt("mode", 0);
            String time = getIntent().getExtras().getString("time", "");
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = df.parse(time);
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(java.util.Calendar.YEAR);
                int month = calendar.get(java.util.Calendar.MONTH) + 1;
                int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
                mDate = new String[]{(year + ""), (month + ""), (day + "")};
            } catch (Exception e) {
                mDate = time.split("-");
            }

            if (mode == INTERCEPT_MODE) {
                mCalendarView.setSelectSingleMode();
                String limitDateST = getIntent().getExtras().getString("limitDate", "");
                try {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date date = df.parse(limitDateST);
                    java.util.Calendar calendar = java.util.Calendar.getInstance();
                    calendar.setTime(date);
                    int year = calendar.get(java.util.Calendar.YEAR);
                    int month = calendar.get(java.util.Calendar.MONTH) + 1;
                    int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
                    limitDate = new String[]{(year + ""), (month + ""), (day + "")};
                } catch (Exception e) {
                    limitDate = limitDateST.split("-");
                }
                limitCode = getIntent().getExtras().getInt("limitCode", 0);
            } else if (mode == NORMAL_MODE) {
                mCalendarView.setSelectSingleMode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mCalendarView.setOnMonthChangeListener(this);

        //设置日期拦截事件，仅适用单选模式，当前有效
        mCalendarView.setOnCalendarInterceptListener(this);

        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));

    }

    @Override
    protected void initData() {

//        int year = mCalendarView.getCurYear();
//        int month = mCalendarView.getCurMonth();

        try {


            mCalendarView.scrollToCalendar(Integer.parseInt(mDate[0]), Integer.parseInt(mDate[1]), Integer.parseInt(mDate[2]));

            mCalendarView.setSchemeColor(0xffff3333, 0xffff3333, 0xffff3333);

//
            Map<String, com.haibin.calendarview.Calendar> map = new HashMap<>();
            map.put(getSchemeCalendar(Integer.parseInt(mDate[0]), Integer.parseInt(mDate[1]), Integer.parseInt(mDate[2])).toString(),
                    getSchemeCalendar(Integer.parseInt(mDate[0]), Integer.parseInt(mDate[1]), Integer.parseInt(mDate[2])));
//        //此方法在巨大的数据量上不影响遍历性能，推荐使用
            mCalendarView.setSchemeDate(map);
        } catch (Exception e) {
            selectDate = getSchemeCalendar(mCalendarView.getCurYear(), mCalendarView.getCurMonth(), mCalendarView.getCurDay());
            e.printStackTrace();
        }
    }

    private com.haibin.calendarview.Calendar getSchemeCalendar(int year, int month, int day) {
        com.haibin.calendarview.Calendar calendar = new com.haibin.calendarview.Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        return calendar;
    }


    @Override
    public void onCalendarOutOfRange(com.haibin.calendarview.Calendar calendar) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCalendarSelect(com.haibin.calendarview.Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();

//        Log.e("onDateSelected", "  -- " + calendar.getYear() +
//                "  --  " + calendar.getMonth() +
//                "  -- " + calendar.getDay() +
//                "  --  " + isClick + "  --   " + calendar.getScheme());
        selectDate = calendar;
    }


    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }

    /**
     * 屏蔽某些不可点击的日期，可根据自己的业务自行修改
     * 如 calendar > 2018年1月1日 && calendar <= 2020年12月31日
     *
     * @param calendar calendar
     * @return 是否屏蔽某些不可点击的日期，MonthView和WeekView有类似的API可调用
     */
    @Override
    public boolean onCalendarIntercept(com.haibin.calendarview.Calendar calendar) {
        if (mode == INTERCEPT_MODE) {
            if (limitCode == LIMiT_AFTER) {
                try {
                    if (calendar.getYear() > Integer.parseInt(limitDate[0])) {
                        return false;
                    } else if (calendar.getYear() == Integer.parseInt(limitDate[0])) {
                        if (calendar.getMonth() > Integer.parseInt(limitDate[1])) {
                            return false;
                        } else if (calendar.getMonth() == Integer.parseInt(limitDate[1])) {
                            if (calendar.getDay() > Integer.parseInt(limitDate[2])) {
                                return false;
                            } else if (calendar.getDay() == Integer.parseInt(limitDate[2])) {
                                return false;
                            } else {
                                return true;
                            }
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                } catch (Exception e) {
                    return false;
                }

            } else if (limitCode == LIMiT_BEFORE) {
                try {
                    if (calendar.getYear() < Integer.parseInt(limitDate[0])) {
                        return false;
                    } else if (calendar.getYear() == Integer.parseInt(limitDate[0])) {
                        if (calendar.getMonth() < Integer.parseInt(limitDate[1])) {
                            return false;
                        } else if (calendar.getMonth() == Integer.parseInt(limitDate[1])) {
                            if (calendar.getDay() < Integer.parseInt(limitDate[2])) {
                                return false;
                            } else if (calendar.getDay() == Integer.parseInt(limitDate[2])) {
                                return false;
                            } else {
                                return true;
                            }
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                } catch (Exception e) {
                    return false;
                }
            } else {
                return false;
            }

        } else if (mode == NORMAL_MODE) {
            java.util.Calendar today = java.util.Calendar.getInstance();
            int year = today.get(java.util.Calendar.YEAR);
            int month = today.get(java.util.Calendar.MONTH) + 1;
            int day = today.get(java.util.Calendar.DAY_OF_MONTH);

            if (calendar.getYear() < year) {
                return true;
            } else {
                if (calendar.getMonth() < month) {
                    if (calendar.getYear() > year) {
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    if (calendar.getDay() < day) {
                        if (calendar.getYear() > year || calendar.getMonth() > month) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
    }

    /**
     * 当被拦截的日期被点击
     *
     * @param calendar
     * @param isClick
     */
    @Override
    public void onCalendarInterceptClick(com.haibin.calendarview.Calendar calendar, boolean isClick) {
//        Toast.makeText(this,
//                calendar.toString() + (isClick ? "拦截不可点击" : "拦截滚动到无效日期"),
//                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMonthChange(int year, int month) {

    }
}
