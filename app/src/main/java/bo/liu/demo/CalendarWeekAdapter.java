package bo.liu.demo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by ${wangyu} on 2017/6/5.
 */

public class CalendarWeekAdapter extends BaseAdapter {
    private boolean isLeapyear = false; // 是否为闰年
    private int daysOfMonth = 0; // 某月的天数
    private int dayOfWeek = 0; // 具体某一天是星期几
    private int lastDaysOfMonth = 0; // 上一个月的总天数
    private Context context;
    private String[] dayNumber = new String[42]; // 一个gridview中的日期存入此数组中

    private SpecialCalendar sc = null;
    private LunarCalendar lc = null;
    private Resources res = null;
    private Drawable drawable = null;


    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");


    private String showYear = ""; // 用于在头部显示的年份
    private String showMonth = ""; // 用于在头部显示的月份
    private String animalsYear = "";
    private String leapMonth = ""; // 闰哪一个月
    private String cyclical = ""; // 天干地支
    // 系统当前时间
    private String sysDate = "";
    private String sys_year = "";
    private String sys_month = "";
    private String sys_day = "";

    private TextView gridViewtextView;
    private int getCurrentYear;
    private int stepMonth;
    private int newstepMonth;
    private String otherallTime;
    private String allTime;
    private Date data;
    private List<Date> days;
    public static ArrayList<String> dateList;



    public CalendarWeekAdapter() {
        Date date = new Date();
        sysDate = sdf.format(date); // 当期日期
        sys_year = sysDate.split("-")[0];
        sys_month = sysDate.split("-")[1];
        sys_day = sysDate.split("-")[2];

    }

    public CalendarWeekAdapter(Context context, Resources rs, int jumpMonth, int jumpYear, int year_c, int month_c, int day_c) {
        this();
        this.context = context;
        sc = new SpecialCalendar();
        lc = new LunarCalendar();
        this.res = rs;

        int stepYear = year_c + jumpYear;


        //跳转的月份

        stepMonth = month_c + jumpMonth;
        if (stepMonth > 0) {
            // 往下一个月滑动
            if (stepMonth % 12 == 0) {
                stepYear = year_c + stepMonth / 12 - 1;
                stepMonth = 12;
            } else {
                stepYear = year_c + stepMonth / 12;
                stepMonth = stepMonth % 12;
            }
        } else {
            // 往上一个月滑动
            stepYear = year_c - 1 + stepMonth / 12;
            stepMonth = stepMonth % 12 + 12;

        }


        getCurrentYear = stepYear + jumpYear;

        getCalendar(stepYear + jumpYear, stepMonth);

    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dayNumber.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.calendar_item, null);
        }
        gridViewtextView = (TextView) convertView.findViewById(R.id.tvtext);
        String d = dayNumber[position].split("\\.")[0];
        String dv = dayNumber[position].split("\\.")[1];

        SpannableString sp = new SpannableString(d);

//        if (dv != null || dv != "") {
//            sp.setSpan(new RelativeSizeSpan(0.75f), d.length() + 1, dayNumber[position].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        }

        gridViewtextView.setText(sp);
        gridViewtextView.setTextColor(Color.GRAY);

        if (position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {

            // 当前月信息显示
            gridViewtextView.setTextColor(Color.BLACK);// 当月字体设黑

            if (position % 7 == 0 || position % 7 == 6) {
                // 当前月信息显示
                gridViewtextView.setTextColor(Color.rgb(23, 126, 214));// 当月字体设黑


            }
        }

        //设置不是本月其他月份日历颜色
        if (dayOfWeek + 7 <= position + 7 && position <= (dayOfWeek + daysOfMonth + 7) - 1 - 7) {

            if ((d.length()) == 1) {
                otherallTime = getOtherYearMonth(position) + "-" + d;
            } else {
                otherallTime = getOtherYearMonth(position) + "-" + d;
            }
            if ((d.length()) == 1) {
                allTime = getOtherYearMonth(position) + "-" + d;
            } else {
                allTime = getOtherYearMonth(position) + "-" + d;
            }

            //清空日历
            if (Constants.WEEK_CLEARTYPE == 3) {
                if (position == 5 || position == 6 || (position - 5) % 7 == 0 || (position - 6) % 7 == 0) {
                    gridViewtextView.setBackgroundColor(context.getResources().getColor(R.color.white));
                    gridViewtextView.setTextColor(context.getResources().getColor(R.color.calendar_yellow));
                } else {
                    gridViewtextView.setBackgroundColor(Color.WHITE);
                    gridViewtextView.setTextColor(Color.BLACK);
                }

                return convertView;
            }


            days = dateToWeek(Constants.WEEK_SELECT_WEEK);
            dateList = new ArrayList<>();

            for (Date date : days) {
                dateList.add(sdf.format(date));
            }


            if (dateList.contains(otherallTime)) {

                gridViewtextView.setTextColor(Color.WHITE);
                //gridViewtextView.setBackground(res.getDrawable(R.drawable.calendar_background));
                if (position == 0 || position % 7 == 0) {
                    gridViewtextView.setText(d + "\r\n" + "开始");
                }
                if (position == 6 || (position - 6) % 7 == 0) {
                    gridViewtextView.setText(d + "\r\n" + "结束");
                }
            } else if (position == 5 || position == 6 || (position - 5) % 7 == 0 || (position - 6) % 7 == 0) {
                gridViewtextView.setBackgroundColor(context.getResources().getColor(R.color.white));
                gridViewtextView.setTextColor(context.getResources().getColor(R.color.calendar_yellow));
            } else {
                gridViewtextView.setBackgroundColor(context.getResources().getColor(R.color.white));
                gridViewtextView.setTextColor(Color.BLACK);
            }


        } else {

            if ((d.length()) == 1) {
                otherallTime = getOtherYearMonth(position) + "-" + d;
            } else {
                otherallTime = getOtherYearMonth(position) + "-" + d;
            }
            days = dateToWeek(Constants.WEEK_SELECT_WEEK);
            dateList = new ArrayList<>();

            for (Date date : days) {
                dateList.add(sdf.format(date));
            }


            if (dateList.contains(otherallTime)) {

                gridViewtextView.setTextColor(Color.WHITE);

               // gridViewtextView.setBackground(res.getDrawable(R.drawable.calendar_background));
                if (position == 0 || position % 7 == 0) {
                    gridViewtextView.setText(d + "\r\n" + "开始");
                }
                if (position == 6 || (position - 6) % 7 == 0) {
                    gridViewtextView.setText(d + "\r\n" + "结束");
                }
            } else {
                gridViewtextView.setBackgroundColor(context.getResources().getColor(R.color.white));
                gridViewtextView.setTextColor(Color.GRAY);
            }


        }


        return convertView;
    }

    // 得到某年的某月的天数且这月的第一天是星期几
    public void getCalendar(int year, int month) {
        isLeapyear = sc.isLeapYear(year); // 是否为闰年
        daysOfMonth = sc.getDaysOfMonth(isLeapyear, month); // 某月的总天数
        dayOfWeek = sc.getWeekdayOfMonth(year, month); // 某月第一天为星期几
        lastDaysOfMonth = sc.getDaysOfMonth(isLeapyear, month - 1); // 上一个月的总天数

        Log.d("DAY", isLeapyear + " ======  " + daysOfMonth + "  ============  " + dayOfWeek + "  =========   " + lastDaysOfMonth);
        getweek(year, month);
    }

    // 将一个月中的每一天的值添加入数组dayNuber中
    private void getweek(int year, int month) {
        int j = 1;
        int flag = 0;
        String lunarDay = "";

        // 得到当前月的所有日程日期(这些日期需要标记)

        for (int i = 0; i < dayNumber.length; i++) {
            // 周一
            // if(i<7){
            // dayNumber[i]=week[i]+"."+" ";
            // }
            if (i < dayOfWeek) { // 前一个月
                int temp = lastDaysOfMonth - dayOfWeek + 1;
                lunarDay = lc.getLunarDate(year, month - 1, temp + i, false);
                dayNumber[i] = (temp + i) + "." + lunarDay;

            } else if (i < daysOfMonth + dayOfWeek) { // 本月
                String day = String.valueOf(i - dayOfWeek + 1); // 得到的日期
                lunarDay = lc.getLunarDate(year, month, i - dayOfWeek + 1, false);
                dayNumber[i] = i - dayOfWeek + 1 + "." + lunarDay;
                // 对于当前月才去标记当前日期
                if (sys_year.equals(String.valueOf(year)) && sys_month.equals(String.valueOf(month)) && sys_day.equals(day)) {
                    // 标记当前日期

                    Constants.currentWeekDay = getYearMonth() + dayNumber[i].split("\\.")[0];

                }
                setShowYear(String.valueOf(year));
                setShowMonth(String.valueOf(month));
                setAnimalsYear(lc.animalsYear(year));
                setLeapMonth(lc.leapMonth == 0 ? "" : String.valueOf(lc.leapMonth));
                setCyclical(lc.cyclical(year));
            } else { // 下一个月
                lunarDay = lc.getLunarDate(year, month + 1, j, false);
                dayNumber[i] = j + "." + lunarDay;
                j++;
            }
        }


    }


    /**
     * 点击每一个item时返回item中的日期
     *
     * @param position
     * @return
     */
    public String getDateByClickItem(int position) {
        return dayNumber[position];
    }

    /**
     * 在点击gridView时，得到这个月中第一天的位置
     *
     * @return
     */
    public int getStartPositon() {
        return dayOfWeek + 7;
    }

    /**
     * 在点击gridView时，得到这个月中最后一天的位置
     *
     * @return
     */
    public int getEndPosition() {
        return (dayOfWeek + daysOfMonth + 7) - 1;
    }

    public String getShowYear() {
        return showYear;
    }

    public void setShowYear(String showYear) {
        this.showYear = showYear;
    }

    public String getShowMonth() {
        return showMonth;
    }

    public void setShowMonth(String showMonth) {
        this.showMonth = showMonth;
    }

    public String getAnimalsYear() {
        return animalsYear;
    }

    public void setAnimalsYear(String animalsYear) {
        this.animalsYear = animalsYear;
    }

    public String getLeapMonth() {
        return leapMonth;
    }

    public void setLeapMonth(String leapMonth) {
        this.leapMonth = leapMonth;
    }

    public String getCyclical() {
        return cyclical;
    }

    public void setCyclical(String cyclical) {
        this.cyclical = cyclical;
    }

    private String getYearMonth() {
        if ((stepMonth + "").length() == 1) {
            return getCurrentYear + "-" + stepMonth;

        } else {
            return getCurrentYear + "-" + stepMonth;
        }

    }

    private String getOtherYearMonth(int postion) {
        if (postion > (dayOfWeek + daysOfMonth + 7) - 1 - 7) {
            newstepMonth = stepMonth + 1;
        } else if (postion < dayOfWeek) {
            newstepMonth = stepMonth - 1;
        } else {
            newstepMonth = stepMonth;
        }

        if ((newstepMonth + "").length() == 1) {
            return getCurrentYear + "-" + newstepMonth;

        } else {
            return getCurrentYear + "-" + newstepMonth;
        }

    }

    public List<Date> dateToWeek(Date mdate) {
        int b = mdate.getDay();
        Date fdate;
        List<Date> list = new ArrayList<Date>();
        Long fTime = mdate.getTime() - b * 24 * 3600000;
        for (int a = 1; a <= 7; a++) {
            fdate = new Date();
            fdate.setTime(fTime + (a * 24 * 3600000));
            list.add(a - 1, fdate);
        }
        return list;
    }

    public void setDate(Date date) {
        this.data = date;
    }


}
