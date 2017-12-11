package bo.liu.demo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by zhaoxian on 2017/3/30 9:03
 */

public class DatePopWindow extends PopupWindow implements View.OnClickListener {
    public SelectedDateListener dateListener;
    private Context context;
    public View datePopView;
    private GestureDetector gestureDetector = null;
    private GestureDetector gestureweekDetector = null;
    private CalendarAdapter calV;
    private CalendarWeekAdapter weekAdapter;
    private int sum = 1;
    private boolean confirmflag = true;
    //月份Relative 和id
    RelativeLayout[] monthRelative = new RelativeLayout[12];
    int[] monthReId = {
            R.id.rv_january, R.id.rv_february, R.id.rv_march, R.id.rv_april, R.id.rv_may
            , R.id.rv_june, R.id.rv_july, R.id.rv_august, R.id.rv_september, R.id.rv_october, R.id.rv_november
            , R.id.rv_december};
    TextView[] monthTv = new TextView[12];
    int[] monthTvId = {
            R.id.tv_january, R.id.tv_february, R.id.tv_march, R.id.tv_april, R.id.tv_may
            , R.id.tv_june, R.id.tv_july, R.id.tv_august, R.id.tv_september, R.id.tv_october, R.id.tv_november
            , R.id.tv_december};
    //季度 Relative 和id
    RelativeLayout[] seasonRelative = new RelativeLayout[4];
    int[] seasonReId = {R.id.rv_seasonone, R.id.rv_seasontow, R.id.rv_seasonthree, R.id.rv_seasonfour};
    TextView[] seasonTv = new TextView[4];
    int[] seasonTvId = {R.id.tv_seasonone, R.id.tv_seasonstow, R.id.tv_seasonthree, R.id.tv_seasonfour};
    private ViewStub stubday;
    private ViewStub stubweek;
    private ViewStub stubmonth;
    private ViewStub stubseasons;
    private View weeksinflate;
    private View seasonsinflate;
    private View monthinflate;
    private RelativeLayout day;
    private RelativeLayout week;
    private RelativeLayout month;
    private RelativeLayout seasons;
    private RelativeLayout datepop_cancel;
    public RelativeLayout datepop_confrim;
    private TextView currentMonth;
    private ImageView prevMonth;
    private ImageView nextMonth;
    private TextView currentYear;
    private ImageView prevYear;
    private ImageView nextYear;
    private ImageView iv_month_nextyear;
    private ImageView iv_month_prevyear;
    private TextView tv_month_year;
    private RelativeLayout datepop_month_cancel;
    private RelativeLayout datepop_month_confrim;
    private ImageView iv_seasons_nextyear;
    private ImageView iv_seasons_prevyear;
    private TextView tv_seasons_currentyear;
    private RelativeLayout datepop_season_cancel;
    private RelativeLayout datepop_season_confrim;
    private String FirstscheduleDay;
    private String SecondscheduleDay;
    private String FirstscheduleYear;
    private String SecondscheduleYear;
    private String FirstscheduleMonth;
    private String SecondscheduleMonth;


    private int monthflag;
    private int seasonflag;
    private ViewFlipper flipper;
    private ViewFlipper weekflipper;
    private GridView gridView;
    private GridView weekgridView;
    //时间字符串
    private String currentDate;
    //截取年
    public static int year_c;
    //截取月
    public static int month_c;
    //截取日
    private int day_c;
    private int jumpMonth = 0; // 每次滑动，增加或减去一个月,默认为0（即显示当前月）
    private int jumpYear = 0; // 滑动跨越一年，则增加或者减去一年,默认为0(即当前年)
    private int jumpweekYear = 0; // 滑动跨越一年，则增加或者减去一年,默认为0(即当前年)
    private int jumpweekMonth = 0; // 滑动跨越一年，则增加或者减去一年,默认为0(即当前年)
    private int type;

    /**
     * 每次添加gridview到viewflipper中时给的标记
     */
    private int gvFlag = 0;
    private int weekgvFlag = 0;
    private int monthyear;
    private TextView tv_day;
    private TextView tv_week;
    private TextView tv_month;
    private TextView tv_seasons;
    private View day_view;
    private View month_view;
    private View seasons_view;
    private TextView date_cleardate;
    private TextView tv_custom_date;
    private AppCompatActivity activity;
    private View week_view;
    private TextView popweek_currentyear;
    private TextView popweek_currentMonth;
    private ImageView popweek_prevyear;
    private ImageView popweek_nextyear;
    private ImageView popweek_prevMonth;
    private ImageView popweek_nextMonth;
    private String weekscheduleDay;
    private String weekscheduleYear;
    private int WeekscheduleMonth;
    private RelativeLayout datepopweek_day_cancel;
    private RelativeLayout datepopweek_day_confrim;
    private View view_daily_line;
    private View view_daily_line2;
    private LinearLayout lin_daily;


    //构造方法中 直接把监听 带过来
    public DatePopWindow(Context context, SelectedDateListener dateListener, int type, AppCompatActivity activity) {

        this.context = context;
        this.dateListener = dateListener;
        this.type = type;
        this.activity = activity;
        //获取系统时间方法
        getCurrentTimeYMD();
        //加载日历popwindow最外层控件
        datePopView = View.inflate(context, R.layout.yjbb_popwindow, null);
        this.setContentView(datePopView);
        setView(datePopView);

    }

    public void getCurrentTimeYMD() {
        //获取系统时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = sdf.format(date); // 当期日期

        year_c = Integer.parseInt(currentDate.split("-")[0]);
        month_c = Integer.parseInt(currentDate.split("-")[1]);
        day_c = Integer.parseInt(currentDate.split("-")[2]);

    }

    private void setWeekview(View datePopView) {

        gestureweekDetector = new GestureDetector(context, new MyGestureWeekListener());
        weekflipper = (ViewFlipper) datePopView.findViewById(R.id.popweek_date_flipper);
        weekflipper.removeAllViews();
        //GridView
        weekAdapter = new CalendarWeekAdapter(context, context.getResources(), jumpweekMonth, jumpweekYear, year_c, month_c, day_c);

        addweekGridView();
        weekgridView.setAdapter(weekAdapter);
        weekflipper.addView(weekgridView, 0);

        setStyle();


    }

    public void setView(View datePopView) {
        findViewByid(datePopView);
        tv_seasons_currentyear.setText(year_c + "年");
        tv_month_year.setText(year_c + "年");
        //设置当前年
        currentYear.setText(year_c + "年");
        initLisnter();
        gestureDetector = new GestureDetector(context, new MyGestureListener());
        flipper = (ViewFlipper) datePopView.findViewById(R.id.pop_date_flipper);
        flipper.removeAllViews();
        //GridView
        calV = new CalendarAdapter(context, context.getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);

        addGridView();
        gridView.setAdapter(calV);
        flipper.addView(gridView, 0);
        addTextMonthTextView(currentMonth);
        setStyle();


    }

    public void setStyle() {
        //获取屏幕宽高
        WindowManager wm = (WindowManager) context
                .getSystemService(context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        //设置popwindow横向充满屏幕
        this.setWidth(width);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);//可获取焦点状态
        this.setTouchable(true);
        //设置动画样式
        this.setAnimationStyle(R.style.PeopeleDailogStyle);
    }

    public void initLisnter() {
        //datepopwindow点击事件----------------------
        //月份监听
        prevMonth.setOnClickListener(this);
        nextMonth.setOnClickListener(this);
        //年监听
        prevYear.setOnClickListener(this);
        nextYear.setOnClickListener(this);
        day.setOnClickListener(this);
        week.setOnClickListener(this);
        month.setOnClickListener(this);
        seasons.setOnClickListener(this);
        tv_day.setOnClickListener(this);
        tv_week.setOnClickListener(this);
        tv_month.setOnClickListener(this);
        tv_seasons.setOnClickListener(this);
        //取消，确定按钮
        datepop_cancel.setOnClickListener(this);
        datepop_confrim.setOnClickListener(this);
        //清空按钮监听
        date_cleardate.setOnClickListener(this);
        //月历年份加减监听
        iv_month_prevyear.setOnClickListener(this);
        iv_month_nextyear.setOnClickListener(this);

        //月历监听
        for (
                int i = 0;
                i < 12; i++) {
            monthRelative[i].setOnClickListener(this);
        }
        for (
                int i = 0;
                i < 4; i++)

        {
            seasonRelative[i].setOnClickListener(this);
        }
        datepop_month_cancel.setOnClickListener(this);
        datepop_month_confrim.setOnClickListener(this);
        //季度监听
        iv_seasons_nextyear.setOnClickListener(this);
        iv_seasons_prevyear.setOnClickListener(this);
        datepop_season_cancel.setOnClickListener(this);
        datepop_season_confrim.setOnClickListener(this);
        //---------------------------------------------------
    }

    public void findViewByid(View datePopView) {
        //初始化日历popwindow控件
        stubday = (ViewStub) datePopView.findViewById(R.id.popview_stub_day);
        stubweek = (ViewStub) datePopView.findViewById(R.id.popview_stub_week);
        stubmonth = (ViewStub) datePopView.findViewById(R.id.popview_stub_month);
        stubseasons = (ViewStub) datePopView.findViewById(R.id.popview_stub_seasons);
        view_daily_line = (View) datePopView.findViewById(R.id.view_daily_line);
        view_daily_line2 = (View) datePopView.findViewById(R.id.view_daily_line2);
        lin_daily = (LinearLayout) datePopView.findViewById(R.id.lin_daily);
        weeksinflate = stubweek.inflate();
        weeksinflate.setVisibility(View.GONE);
        seasonsinflate = stubseasons.inflate();
        stubseasons.setVisibility(View.GONE);
        monthinflate = stubmonth.inflate();
        stubmonth.setVisibility(View.GONE);
        stubday.inflate();
        day = (RelativeLayout) datePopView.findViewById(R.id.rv_pop_day);
        week = (RelativeLayout) datePopView.findViewById(R.id.rv_pop_week);
        month = (RelativeLayout) datePopView.findViewById(R.id.rv_pop_month);
        seasons = (RelativeLayout) datePopView.findViewById(R.id.rv_pop_seasons);
        tv_day = (TextView) datePopView.findViewById(R.id.tv_pop_day);
        tv_week = (TextView) datePopView.findViewById(R.id.tv_pop_week);
        tv_month = (TextView) datePopView.findViewById(R.id.tv_pop_month);
        date_cleardate = (TextView) datePopView.findViewById(R.id.date_cleardate);
        tv_seasons = (TextView) datePopView.findViewById(R.id.tv_pop_seasons);
        day_view = (View) datePopView.findViewById(R.id.day_view);
        week_view = (View) datePopView.findViewById(R.id.week_view);
        month_view = (View) datePopView.findViewById(R.id.month_view);
        seasons_view = (View) datePopView.findViewById(R.id.seasons_view);
        datepop_cancel = (RelativeLayout) datePopView.findViewById(R.id.datepop_day_cancel);
        datepop_confrim = (RelativeLayout) datePopView.findViewById(R.id.datepop_day_confrim);
        tv_custom_date = (TextView) datePopView.findViewById(R.id.tv_custom_date);
        popweek_currentyear = (TextView) weeksinflate.findViewById(R.id.popweek_currentyear);
        popweek_currentMonth = (TextView) weeksinflate.findViewById(R.id.popweek_currentMonth);
        popweek_prevyear = (ImageView) weeksinflate.findViewById(R.id.popweek_prevyear);
        popweek_nextyear = (ImageView) weeksinflate.findViewById(R.id.popweek_nextyear);
        popweek_prevMonth = (ImageView) weeksinflate.findViewById(R.id.popweek_prevMonth);
        popweek_nextMonth = (ImageView) weeksinflate.findViewById(R.id.popweek_nextMonth);
        datepopweek_day_cancel = (RelativeLayout) weeksinflate.findViewById(R.id.datepopweek_day_cancel);
        datepopweek_day_confrim = (RelativeLayout) weeksinflate.findViewById(R.id.datepopweek_day_confrim);

        popweek_currentyear.setText(year_c + "年");
        popweek_currentMonth.setText(month_c + "月");
        //如果不支持多选范围日期，也就不支持周历
        if (type == 1) {
            week.setVisibility(View.GONE);
            view_daily_line.setVisibility(View.GONE);
        } else {
            week.setVisibility(View.VISIBLE);
        }
        datepopweek_day_cancel.setOnClickListener(this);
        datepopweek_day_confrim.setOnClickListener(this);
        popweek_prevyear.setOnClickListener(this);
        popweek_nextyear.setOnClickListener(this);
        popweek_prevMonth.setOnClickListener(this);
        popweek_nextMonth.setOnClickListener(this);
        if (type == 1) {
            tv_custom_date.setText("自定义日期");
            month.setVisibility(View.GONE);
            seasons.setVisibility(View.GONE);
            day.setVisibility(View.GONE);
            lin_daily.setVisibility(View.GONE);
            view_daily_line2.setVisibility(View.VISIBLE);
        } else if (type == 2) {
            tv_custom_date.setText("自定义日期(可选范围)");
            view_daily_line2.setVisibility(View.GONE);
            lin_daily.setVisibility(View.VISIBLE);
        }


        currentMonth = (TextView) datePopView.findViewById(R.id.pop_currentMonth);
        prevMonth = (ImageView) datePopView.findViewById(R.id.pop_prevMonth);
        nextMonth = (ImageView) datePopView.findViewById(R.id.pop_nextMonth);
        currentYear = (TextView) datePopView.findViewById(R.id.pop_currentyear);
        prevYear = (ImageView) datePopView.findViewById(R.id.pop_prevyear);
        nextYear = (ImageView) datePopView.findViewById(R.id.pop_nextyear);
        iv_month_prevyear = (ImageView) monthinflate.findViewById(R.id.iv_month_prevyear);
        iv_month_nextyear = (ImageView) monthinflate.findViewById(R.id.iv_month_nextyear);
        tv_month_year = (TextView) monthinflate.findViewById(R.id.tv_month_year);
        for (int i = 0; i < 12; i++) {
            monthRelative[i] = (RelativeLayout) monthinflate.findViewById(monthReId[i]);
            monthTv[i] = (TextView) monthinflate.findViewById(monthTvId[i]);
        }
        for (int i = 0; i < 4; i++) {
            seasonRelative[i] = (RelativeLayout) seasonsinflate.findViewById(seasonReId[i]);
            seasonTv[i] = (TextView) seasonsinflate.findViewById(seasonTvId[i]);
        }
        datepop_month_cancel = (RelativeLayout) monthinflate.findViewById(R.id.datepop_month_cancel);
        datepop_month_confrim = (RelativeLayout) monthinflate.findViewById(R.id.datepop_month_confrim);
        iv_seasons_nextyear = (ImageView) seasonsinflate.findViewById(R.id.iv_seasons_nextyear);
        iv_seasons_prevyear = (ImageView) seasonsinflate.findViewById(R.id.iv_seasons_prevyear);
        tv_seasons_currentyear = (TextView) seasonsinflate.findViewById(R.id.tv_seasons_currentyear);
        datepop_season_cancel = (RelativeLayout) seasonsinflate.findViewById(R.id.datepop_season_cancel);
        datepop_season_confrim = (RelativeLayout) seasonsinflate.findViewById(R.id.datepop_season_confrim);
    }

    /**
     * 添加头部的年份 闰哪月等信息
     *
     * @param view
     */
    public void addTextMonthTextView(TextView view) {
        StringBuffer textDate = new StringBuffer();
        textDate.append(calV.getShowMonth()).append("月");
        view.setText(textDate);
    }

    public void addTextWeekMonthTextView(TextView view) {
        StringBuffer textDate = new StringBuffer();
        textDate.append(weekAdapter.getShowMonth()).append("月");
        view.setText(textDate);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rv_pop_day:
            case R.id.tv_pop_day:

                stubweek.setVisibility(View.GONE);
                stubmonth.setVisibility(View.GONE);
                stubseasons.setVisibility(View.GONE);
                stubday.setVisibility(View.VISIBLE);
                tv_day.setTextColor(context.getResources().getColor(R.color.blue_navy));
                tv_week.setTextColor(context.getResources().getColor(R.color.black));
                tv_month.setTextColor(context.getResources().getColor(R.color.black));
                tv_seasons.setTextColor(context.getResources().getColor(R.color.black));
                day_view.setBackgroundColor(context.getResources().getColor(R.color.blue_navy));
                week_view.setBackgroundColor(context.getResources().getColor(R.color.view_background));
                month_view.setBackgroundColor(context.getResources().getColor(R.color.view_background));
                seasons_view.setBackgroundColor(context.getResources().getColor(R.color.view_background));
                return;
            case R.id.rv_pop_week:
            case R.id.tv_pop_week:


                setWeekview(datePopView);


                stubday.setVisibility(View.GONE);
                stubmonth.setVisibility(View.GONE);
                stubseasons.setVisibility(View.GONE);
                stubweek.setVisibility(View.VISIBLE);
                tv_week.setTextColor(context.getResources().getColor(R.color.blue_navy));
                tv_day.setTextColor(context.getResources().getColor(R.color.black));
                tv_month.setTextColor(context.getResources().getColor(R.color.black));
                tv_seasons.setTextColor(context.getResources().getColor(R.color.black));
                week_view.setBackgroundColor(context.getResources().getColor(R.color.blue_navy));
                day_view.setBackgroundColor(context.getResources().getColor(R.color.view_background));
                month_view.setBackgroundColor(context.getResources().getColor(R.color.view_background));
                seasons_view.setBackgroundColor(context.getResources().getColor(R.color.view_background));
                return;
            case R.id.rv_pop_month:
            case R.id.tv_pop_month:
                stubday.setVisibility(View.GONE);
                stubweek.setVisibility(View.GONE);
                stubseasons.setVisibility(View.GONE);
                stubmonth.setVisibility(View.VISIBLE);
                tv_month.setTextColor(context.getResources().getColor(R.color.blue_navy));
                tv_day.setTextColor(context.getResources().getColor(R.color.black));
                tv_week.setTextColor(context.getResources().getColor(R.color.black));
                tv_seasons.setTextColor(context.getResources().getColor(R.color.black));
                day_view.setBackgroundColor(context.getResources().getColor(R.color.view_background));
                week_view.setBackgroundColor(context.getResources().getColor(R.color.view_background));
                month_view.setBackgroundColor(context.getResources().getColor(R.color.blue_navy));
                seasons_view.setBackgroundColor(context.getResources().getColor(R.color.view_background));
                return;
            case R.id.rv_pop_seasons:
            case R.id.tv_pop_seasons:
                stubday.setVisibility(View.GONE);
                stubweek.setVisibility(View.GONE);
                stubmonth.setVisibility(View.GONE);
                stubseasons.setVisibility(View.VISIBLE);
                tv_seasons.setTextColor(context.getResources().getColor(R.color.blue_navy));
                tv_day.setTextColor(context.getResources().getColor(R.color.black));
                tv_week.setTextColor(context.getResources().getColor(R.color.black));
                tv_month.setTextColor(context.getResources().getColor(R.color.black));
                day_view.setBackgroundColor(context.getResources().getColor(R.color.view_background));
                week_view.setBackgroundColor(context.getResources().getColor(R.color.view_background));
                month_view.setBackgroundColor(context.getResources().getColor(R.color.view_background));
                seasons_view.setBackgroundColor(context.getResources().getColor(R.color.blue_navy));
                return;
            case R.id.pop_nextMonth: // 日历下一个月
                enterNextMonth(gvFlag);
                return;
            case R.id.pop_prevMonth: // 日历上一个月
                enterPrevMonth(gvFlag);
                return;
            case R.id.pop_prevyear: // 日历上一年
                enterPrevYear(gvFlag);
                return;
            case R.id.pop_nextyear: // 日历下一年
                enterNextYear(gvFlag);
                return;
            case R.id.date_cleardate: // 清空日期按钮
                calV.setSelection(3, context);
                confirmflag = false;
                calV.notifyDataSetChanged();
                for (int i = 0; i < 12; i++) {
                    monthRelative[i].setBackgroundColor(Color.WHITE);
                    monthTv[i].setTextColor(Color.BLACK);
                }
                for (int i = 0; i < 4; i++) {

                    seasonRelative[i].setBackgroundColor(Color.WHITE);
                    seasonTv[i].setTextColor(Color.BLACK);

                }
                Constants.FIRST_SELECT_DATE = "";
                Constants.SECOND_SELECT_DATE = "";
                Constants.SELECT_SUM = 1;
                Constants.WEEK_SELECT_DATE = "-1";
                Date weekdate = new Date();
                Constants.WEEK_SELECT_WEEK = weekdate;
                Constants.WEEK_SELECT_SUM = 1;
                year_c = Integer.parseInt(currentDate.split("-")[0]);
                month_c = Integer.parseInt(currentDate.split("-")[1]);
                if (weekAdapter != null) {
                    Constants.WEEK_CLEARTYPE = 3;
                    weekAdapter.notifyDataSetChanged();
                }

                UtilsSystem.getInstance().showShortToast(context, "清空日期后，按钮不可点击，必须选中日期");
                return;
            case R.id.datepop_day_cancel: // 日历取消按钮
                this.dismiss();
                return;
            case R.id.datepop_day_confrim: // 日历确定按钮
                if (!confirmflag) {
                    UtilsSystem.getInstance().showShortToast(context, "清空日期后，按钮不可点击，请重新选择日期或取消");
                    return;
                }

                if (FirstscheduleYear == null && FirstscheduleMonth == null && FirstscheduleDay == null) {
                    if ((month_c + "").length() != 1 && (day_c + "").length() != 1) {
                        dateListener.setSelectedDate(year_c + "-" + month_c + "-" + day_c, activity);
                    } else if ((month_c + "").length() != 1 && (day_c + "").length() == 1) {
                        dateListener.setSelectedDate(year_c + "-" + month_c + "-" + "0" + day_c, activity);
                    } else if ((month_c + "").length() == 1 && (day_c + "").length() != 1) {
                        dateListener.setSelectedDate(year_c + "-" + "0" + month_c + "-" + day_c, activity);
                    } else {
                        dateListener.setSelectedDate(year_c + "-" + "0" + month_c + "-" + "0" + day_c, activity);
                    }
                } else {
                    if (type == 1) {
                        if ((FirstscheduleMonth + "").length() != 1 && (FirstscheduleDay + "").length() != 1) {
                            dateListener.setSelectedDate(FirstscheduleYear + "-" + FirstscheduleMonth + "-" + FirstscheduleDay, activity);
                        } else if ((FirstscheduleMonth + "").length() == 1 && (FirstscheduleDay + "").length() != 1) {
                            dateListener.setSelectedDate(FirstscheduleYear + "-0" + FirstscheduleMonth + "-" + FirstscheduleDay, activity);
                        } else if ((FirstscheduleMonth + "").length() != 1 && (FirstscheduleDay + "").length() == 1) {
                            dateListener.setSelectedDate(FirstscheduleYear + "-" + FirstscheduleMonth + "-0" + FirstscheduleDay, activity);
                        } else {
                            dateListener.setSelectedDate(FirstscheduleYear + "-0" + FirstscheduleMonth + "-0" + FirstscheduleDay, activity);
                        }
                    } else {

                        if (FirstscheduleYear == null && FirstscheduleMonth == null && FirstscheduleDay == null || SecondscheduleYear == null && SecondscheduleMonth == null && SecondscheduleDay == null) {
                            if (FirstscheduleMonth.length() == 1) {
                                FirstscheduleMonth = "0" + FirstscheduleMonth;
                            }
                            if (FirstscheduleDay.length() == 1) {
                                FirstscheduleDay = "0" + FirstscheduleDay;
                            }
                            dateListener.setSelectedDate(FirstscheduleYear + "-" + FirstscheduleMonth + "-" + FirstscheduleDay, activity);
                            this.dismiss();
                            return;
                        }

                        ArrayList<String> list = new ArrayList<>();
                        StringBuilder sb = new StringBuilder();
                        StringBuilder sb1 = new StringBuilder();
                        if (FirstscheduleMonth.length() == 1) {
                            FirstscheduleMonth = "0" + FirstscheduleMonth;
                        }
                        if (FirstscheduleDay.length() == 1) {
                            FirstscheduleDay = "0" + FirstscheduleDay;
                        }
                        if (SecondscheduleMonth.length() == 1) {
                            SecondscheduleMonth = "0" + SecondscheduleMonth;
                        }
                        if (SecondscheduleDay.length() == 1) {
                            SecondscheduleDay = "0" + SecondscheduleDay;
                        }
                        sb.append(FirstscheduleYear + "-").append(FirstscheduleMonth + "-").append(FirstscheduleDay);
                        sb1.append(SecondscheduleYear + "-").append(SecondscheduleMonth + "-").append(SecondscheduleDay);
                        list.add(sb.toString());
                        list.add(sb1.toString());

                        String s1 = list.get(list.size() - 2);
                        String s2 = list.get(list.size() - 1);
                        if (Integer.parseInt(s1.split("-")[0]) > Integer.parseInt(s2.split("-")[0])) {

                            dateListener.setSelectedDate(list.get(list.size() - 1) + "~" + list.get(list.size() - 2), activity);

                        } else if (Integer.parseInt(s1.split("-")[0]) < Integer.parseInt(s2.split("-")[0])) {

                            dateListener.setSelectedDate(list.get(list.size() - 2) + "~" + list.get(list.size() - 1), activity);
                        } else {
                            if (Integer.parseInt(s1.split("-")[1]) > Integer.parseInt(s2.split("-")[1])) {

                                dateListener.setSelectedDate(list.get(list.size() - 1) + "~" + list.get(list.size() - 2), activity);
                            } else if (Integer.parseInt(s1.split("-")[1]) < Integer.parseInt(s2.split("-")[1])) {

                                dateListener.setSelectedDate(list.get(list.size() - 2) + "~" + list.get(list.size() - 1), activity);
                            } else {
                                if (Integer.parseInt(s1.split("-")[2]) > Integer.parseInt(s2.split("-")[2])) {

                                    dateListener.setSelectedDate(list.get(list.size() - 1) + "~" + list.get(list.size() - 2), activity);
                                } else if (Integer.parseInt(s1.split("-")[2]) < Integer.parseInt(s2.split("-")[2])) {

                                    dateListener.setSelectedDate(list.get(list.size() - 2) + "~" + list.get(list.size() - 1), activity);
                                }
                            }
                        }

                    }

                }


                //点击确定按钮pop消失之前一切置为默认初始值
                Constants.FIRST_SELECT_DATE = "";
                Constants.SECOND_SELECT_DATE = "";
                Constants.SELECT_SUM = 1;
                Constants.WEEK_CLEARTYPE = -1;
                this.dismiss();
                return;
            case R.id.datepopweek_day_cancel://周历取消按钮
                this.dismiss();
                return;
            case R.id.datepopweek_day_confrim://周历确定按钮
                if (!confirmflag) {
                    UtilsSystem.getInstance().showShortToast(context, "清空日期后，按钮不可点击，请重新选择日期或取消");
                    return;
                }
                String s = CalendarWeekAdapter.dateList.get(0);
                if (s.split("-")[1].length() == 1 && s.split("-")[2].length() == 1) {
                    s = CalendarWeekAdapter.dateList.get(0).split("-")[0] + "-0" + CalendarWeekAdapter.dateList.get(0).split("-")[1] + "-0" + CalendarWeekAdapter.dateList.get(0).split("-")[2];
                } else if (s.split("-")[1].length() == 2 && s.split("-")[2].length() == 1) {
                    s = CalendarWeekAdapter.dateList.get(0).split("-")[0] + "-" + CalendarWeekAdapter.dateList.get(0).split("-")[1] + "-0" + CalendarWeekAdapter.dateList.get(0).split("-")[2];
                } else if (s.split("-")[1].length() == 1 && s.split("-")[2].length() == 2) {
                    s = CalendarWeekAdapter.dateList.get(0).split("-")[0] + "-0" + CalendarWeekAdapter.dateList.get(0).split("-")[1] + "-" + CalendarWeekAdapter.dateList.get(0).split("-")[2];
                }
                String s1 = CalendarWeekAdapter.dateList.get(CalendarWeekAdapter.dateList.size() - 1);
                if (s1.split("-")[1].length() == 1 && s1.split("-")[2].length() == 1) {
                    s1 = CalendarWeekAdapter.dateList.get(CalendarWeekAdapter.dateList.size() - 1).split("-")[0] + "-0" + CalendarWeekAdapter.dateList.get(CalendarWeekAdapter.dateList.size() - 1).split("-")[1] + "-0" + CalendarWeekAdapter.dateList.get(CalendarWeekAdapter.dateList.size() - 1).split("-")[2];
                } else if (s1.split("-")[1].length() == 2 && s1.split("-")[2].length() == 1) {
                    s1 = CalendarWeekAdapter.dateList.get(CalendarWeekAdapter.dateList.size() - 1).split("-")[0] + "-" + CalendarWeekAdapter.dateList.get(CalendarWeekAdapter.dateList.size() - 1).split("-")[1] + "-0" + CalendarWeekAdapter.dateList.get(CalendarWeekAdapter.dateList.size() - 1).split("-")[2];
                } else if (s1.split("-")[1].length() == 1 && s1.split("-")[2].length() == 2) {
                    s1 = CalendarWeekAdapter.dateList.get(CalendarWeekAdapter.dateList.size() - 1).split("-")[0] + "-0" + CalendarWeekAdapter.dateList.get(CalendarWeekAdapter.dateList.size() - 1).split("-")[1] + "-" + CalendarWeekAdapter.dateList.get(CalendarWeekAdapter.dateList.size() - 1).split("-")[2];
                }

                dateListener.setSelectedDate(s + "~" + s1, activity);


                this.dismiss();
                return;
            case R.id.datepop_month_cancel: // 月历取消按钮

                this.dismiss();
                return;
            case R.id.datepop_month_confrim: // 月历确定按钮
                if (!confirmflag) {
                    UtilsSystem.getInstance().showShortToast(context, "清空日期后，按钮不可点击，请重新选择日期或取消");
                    return;
                }
                if (monthyear == 0) {
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
                    String format = sdf.format(date);// 当期日期
                    int year = Integer.parseInt(format.split("-")[0]);
                    int month = Integer.parseInt(format.split("-")[1]);
                    switch (monthflag) {
                        case 0:
                            dateListener.setSelectedDate(year + "年" + month + "月", activity);

                            break;
                        case 1:
                            dateListener.setSelectedDate(year_c + "-01-01" + "~" + year_c + "-01-31", activity);
                            break;
                        case 2:
                            //判断是否是闰年
                            if (year_c % 4 == 0) {
                                dateListener.setSelectedDate(year_c + "-02-01" + "~" + year_c + "-02-27", activity);
                            }
                            dateListener.setSelectedDate(year_c + "-02-01" + "~" + year_c + "-02-28", activity);
                            break;
                        case 3:
                            dateListener.setSelectedDate(year_c + "-03-01" + "~" + year_c + "-03-31", activity);

                            break;
                        case 4:
                            dateListener.setSelectedDate(year_c + "-04-01" + "~" + year_c + "-04-30", activity);
                            break;
                        case 5:
                            dateListener.setSelectedDate(year_c + "-05-01" + "~" + year_c + "-05-31", activity);
                            break;
                        case 6:
                            dateListener.setSelectedDate(year_c + "-06-01" + "~" + year_c + "-06-30", activity);
                            break;
                        case 7:
                            dateListener.setSelectedDate(year_c + "-07-01" + "~" + year_c + "-07-31", activity);
                            break;
                        case 8:
                            dateListener.setSelectedDate(year_c + "-08-01" + "~" + year_c + "-08-31", activity);
                            break;
                        case 9:
                            dateListener.setSelectedDate(year_c + "-09-01" + "~" + year_c + "-09-30", activity);
                            break;
                        case 10:
                            dateListener.setSelectedDate(year_c + "-10-01" + "~" + year_c + "-10-31", activity);
                            break;
                        case 11:
                            dateListener.setSelectedDate(year_c + "-11-01" + "~" + year_c + "-11-30", activity);
                            break;
                        case 12:
                            dateListener.setSelectedDate(year_c + "-12-01" + "~" + year_c + "-12-31", activity);
                            break;
                    }

                } else {
                    switch (monthflag) {
                        case 0:
                            dateListener.setSelectedDate(monthyear + "年" + month_c + "月", activity);
                            break;
                        case 1:
                            dateListener.setSelectedDate(monthyear + "-01-01" + "~" + monthyear + "-01-31", activity);
                            break;
                        case 2:
                            //判断是否是闰年
                            if (monthyear % 4 == 0) {
                                dateListener.setSelectedDate(monthyear + "-02-01" + "~" + monthyear + "-02-27", activity);
                            } else {
                                dateListener.setSelectedDate(monthyear + "-02-01" + "~" + monthyear + "-02-28", activity);
                            }
                            break;
                        case 3:
                            dateListener.setSelectedDate(monthyear + "-03-01" + "~" + monthyear + "-03-31", activity);
                            break;
                        case 4:
                            dateListener.setSelectedDate(monthyear + "-04-01" + "~" + monthyear + "-04-30", activity);
                            break;
                        case 5:
                            dateListener.setSelectedDate(monthyear + "-05-01" + "~" + monthyear + "-05-31", activity);
                            break;
                        case 6:
                            dateListener.setSelectedDate(monthyear + "-06-01" + "~" + monthyear + "-06-30", activity);
                            break;
                        case 7:
                            dateListener.setSelectedDate(monthyear + "-07-01" + "~" + monthyear + "-07-31", activity);
                            break;
                        case 8:
                            dateListener.setSelectedDate(monthyear + "-08-01" + "~" + monthyear + "-08-31", activity);
                            break;
                        case 9:
                            dateListener.setSelectedDate(monthyear + "-09-01" + "~" + monthyear + "-09-30", activity);
                            break;
                        case 10:
                            dateListener.setSelectedDate(monthyear + "-10-01" + "~" + monthyear + "-10-31", activity);
                            break;
                        case 11:
                            dateListener.setSelectedDate(monthyear + "-11-01" + "~" + monthyear + "-11-30", activity);
                            break;
                        case 12:
                            dateListener.setSelectedDate(monthyear + "-12-01" + "~" + monthyear + "-12-31", activity);
                            break;
                    }
                }

                this.dismiss();
                return;
            case R.id.datepop_season_cancel: // 季历取消按钮
                this.dismiss();
                return;
            case R.id.datepop_season_confrim: // 季历确定按钮
                if (!confirmflag) {
                    UtilsSystem.getInstance().showShortToast(context, "清空日期后，按钮不可点击，请重新选择日期或取消");
                    return;
                }
                if (monthyear == 0) {
                    switch (seasonflag) {
                        case 0:
                            confirmflag = true;
                            dateListener.setSelectedDate(year_c + "年" + month_c + "月", activity);
                            break;
                        case 1:
                            confirmflag = true;
                            dateListener.setSelectedDate(year_c + "-01-01" + "~" + year_c + "-03-31", activity);
                            break;
                        case 2:
                            confirmflag = true;
                            dateListener.setSelectedDate(year_c + "-04-01" + "~" + year_c + "-06-30", activity);
                            break;
                        case 3:
                            confirmflag = true;
                            dateListener.setSelectedDate(year_c + "-07-01" + "~" + year_c + "-09-30", activity);
                            break;
                        case 4:
                            confirmflag = true;
                            dateListener.setSelectedDate(year_c + "-10-01" + "~" + year_c + "-12-31", activity);
                            break;
                    }
                } else {
                    switch (seasonflag) {
                        case 0:
                            confirmflag = true;
                            dateListener.setSelectedDate(monthyear + "年" + month_c + "月", activity);
                            break;
                        case 1:
                            confirmflag = true;
                            dateListener.setSelectedDate(monthyear + "-01-01" + "~" + monthyear + "-03-31", activity);
                            break;
                        case 2:
                            confirmflag = true;
                            dateListener.setSelectedDate(monthyear + "-04-01" + "~" + monthyear + "-06-30", activity);
                            break;
                        case 3:
                            confirmflag = true;
                            dateListener.setSelectedDate(monthyear + "-07-01" + "~" + monthyear + "-09-30", activity);
                            break;
                        case 4:
                            confirmflag = true;
                            dateListener.setSelectedDate(monthyear + "-10-01" + "~" + monthyear + "-12-31", activity);
                            break;
                    }
                }
                this.dismiss();
                return;
            case R.id.popweek_prevyear:
                enterPrevWeekYear(weekgvFlag);
                return;
            case R.id.popweek_nextyear:
                enterWeekNextYear(weekgvFlag);
                return;
            case R.id.popweek_prevMonth:
                enterWeekPrevMonth(weekgvFlag);
                return;
            case R.id.popweek_nextMonth:
                enterWeekNextMonth(weekgvFlag);
                return;
            case R.id.iv_month_prevyear: // 月历上一年按钮
                monthyear = --year_c;
                tv_month_year.setText(monthyear + "年");
                return;
            case R.id.iv_month_nextyear: // 月历下一年按钮
                monthyear = ++year_c;
                tv_month_year.setText(monthyear + "年");
                return;
            case R.id.iv_seasons_nextyear:
                tv_seasons_currentyear.setText(++year_c + "年");
                return;
            case R.id.iv_seasons_prevyear:
                tv_seasons_currentyear.setText(--year_c + "年");
                return;

        }
        for (int i = 0; i < 12; i++) {
            if (v.getId() == monthReId[i]) {
                monthflag = i + 1;
                for (int j = 0; j < 12; j++) {

                    if (j == i) {
                        monthRelative[j].setBackgroundColor(context.getResources().getColor(R.color.blue_navy));
                        monthTv[j].setTextColor(Color.WHITE);
                    } else {
                        monthRelative[j].setBackgroundColor(Color.WHITE);
                        monthTv[j].setTextColor(Color.BLACK);
                    }
                }
                return;
            }
            confirmflag = true;
        }
        for (int i = 0; i < 4; i++) {
            if (v.getId() == seasonReId[i]) {
                seasonflag = i + 1;
                for (int j = 0; j < 4; j++) {

                    if (j == i) {
                        seasonRelative[j].setBackgroundColor(context.getResources().getColor(R.color.blue_navy));
                        seasonTv[j].setTextColor(Color.WHITE);
                    } else {
                        seasonRelative[j].setBackgroundColor(Color.WHITE);
                        seasonTv[j].setTextColor(Color.BLACK);
                    }
                }
            }
            confirmflag = true;
        }
    }

    //日历滑动改变月份
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            int gvFlag = 0; // 每次添加gridview到viewflipper中时给的标记
            if (e1.getX() - e2.getX() > 120) {
                // 像左滑动
                enterNextMonth(gvFlag);
                return true;
            } else if (e1.getX() - e2.getX() < -120) {
                // 向右滑动
                enterPrevMonth(gvFlag);
                return true;
            }
            return false;
        }
    }

    //日历滑动改变月份
    private class MyGestureWeekListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            int gvFlag = 0; // 每次添加gridview到viewflipper中时给的标记
            if (e1.getX() - e2.getX() > 120) {
                // 像左滑动
                enterWeekNextMonth(gvFlag);
                return true;
            } else if (e1.getX() - e2.getX() < -120) {
                // 向右滑动
                enterWeekPrevMonth(gvFlag);
                return true;
            }
            return false;
        }
    }


    private void addGridView() {
        //-------------
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
        // 取得屏幕的宽度和高度
        WindowManager windowManager = (WindowManager) MyApplication.getContextObject().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        int Width = display.getWidth();
        int Height = display.getHeight();
        gridView = new GridView(context);
        gridView = new GridView(context);
        gridView.setNumColumns(7);
        gridView.setColumnWidth(30);
        if (Width == 720 && Height == 1280) {
            gridView.setColumnWidth(30);
        }
        gridView.setGravity(Gravity.CENTER_VERTICAL);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        // 去除gridView边框
        gridView.setVerticalSpacing(0);
        gridView.setHorizontalSpacing(0);
        gridView.setOnTouchListener(new View.OnTouchListener() {
            // 将gridview中的触摸事件回传给gestureDetector

            public boolean onTouch(View v, MotionEvent event) {
                return DatePopWindow.this.gestureDetector.onTouchEvent(event);
            }
        });
        //gridview点击
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                // 点击任何一个item，得到这个item的日期(排除点击的是周日到周六(点击不响应))
                int startPosition = calV.getStartPositon();
                int endPosition = calV.getEndPosition();
                //设置当前不在本月的日期不可以点击
                if (type == 1) {
                    //  if (startPosition <= position + 7 && position <= endPosition - 7) {
                    // 这一天的阳历
                    FirstscheduleDay = calV.getDateByClickItem(position).split("\\.")[0];
                    //获取当前年
                    FirstscheduleYear = calV.getShowYear();
                    //获取当前月
                    if (position > endPosition - 7) {
                        FirstscheduleMonth = (Integer.parseInt(calV.getShowMonth()) + 1) + "";
                    } else if (position < startPosition - 7) {
                        FirstscheduleMonth = (Integer.parseInt(calV.getShowMonth()) - 1) + "";
                    } else {
                        FirstscheduleMonth = calV.getShowMonth();
                    }

                    calV.setSelection(type, context);
                    calV.notifyDataSetChanged();

                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(FirstscheduleYear);
                    if (FirstscheduleMonth.length() == 2) {
                        stringBuffer.append(FirstscheduleMonth);
                    } else {
                        stringBuffer.append(0);
                        stringBuffer.append(FirstscheduleMonth);
                    }
                    if (FirstscheduleDay.length() == 2) {
                        stringBuffer.append(FirstscheduleDay);
                    } else {
                        stringBuffer.append(0);
                        stringBuffer.append(FirstscheduleDay);
                    }
                    Constants.FIRST_SELECT_DATE = stringBuffer.toString();
                    confirmflag = true;
                    return;

                } else {
                    if (sum % 2 == 0) {

                        // 这一天的阳历
                        SecondscheduleDay = calV.getDateByClickItem(position).split("\\.")[0];
                        //获取当前年
                        SecondscheduleYear = calV.getShowYear();
                        //获取当前月
                        if (position > endPosition - 7) {
                            SecondscheduleMonth = (Integer.parseInt(calV.getShowMonth()) + 1) + "";
                        } else if (position < startPosition - 7) {
                            SecondscheduleMonth = (Integer.parseInt(calV.getShowMonth()) - 1) + "";
                        } else {
                            SecondscheduleMonth = calV.getShowMonth();
                        }
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append(SecondscheduleYear);
                        if (SecondscheduleMonth.length() == 2) {
                            stringBuffer.append(SecondscheduleMonth);
                        } else {
                            stringBuffer.append(0);
                            stringBuffer.append(SecondscheduleMonth);
                        }
                        if (SecondscheduleDay.length() == 2) {
                            stringBuffer.append(SecondscheduleDay);
                        } else {
                            stringBuffer.append(0);
                            stringBuffer.append(SecondscheduleDay);
                        }
                        Constants.SECOND_SELECT_DATE = stringBuffer.toString();


                        calV.setSelection(type, context);
                        calV.notifyDataSetChanged();


                    } else {


                        // 这一天的阳历
                        FirstscheduleDay = calV.getDateByClickItem(position).split("\\.")[0];
                        //获取当前年
                        FirstscheduleYear = calV.getShowYear();
                        //获取当前月
                        if (position > endPosition - 7) {
                            FirstscheduleMonth = (Integer.parseInt(calV.getShowMonth()) + 1) + "";
                        } else if (position < startPosition - 7) {
                            FirstscheduleMonth = (Integer.parseInt(calV.getShowMonth()) - 1) + "";
                        } else {
                            FirstscheduleMonth = calV.getShowMonth();
                        }
                        calV.setSelection(type, context);
                        calV.notifyDataSetChanged();

                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append(FirstscheduleYear);
                        if (FirstscheduleMonth.length() == 2) {
                            stringBuffer.append(FirstscheduleMonth);
                        } else {
                            stringBuffer.append(0);
                            stringBuffer.append(FirstscheduleMonth);
                        }
                        if (FirstscheduleDay.length() == 2) {
                            stringBuffer.append(FirstscheduleDay);
                        } else {
                            stringBuffer.append(0);
                            stringBuffer.append(FirstscheduleDay);
                        }
                        Constants.FIRST_SELECT_DATE = stringBuffer.toString();


                    }
                    //为多日期范围选择的单选
                    if (sum % 2 != 0 && sum >= 3) {
                        SecondscheduleDay = null;
                        SecondscheduleYear = null;
                        SecondscheduleMonth = null;
                    }
                    //起始日期和结束日期相同

                    sum++;
                }
                confirmflag = true;
            }
        });
        gridView.setLayoutParams(params);
    }

    private void addweekGridView() {
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
        // 取得屏幕的宽度和高度
        WindowManager windowManager = (WindowManager) MyApplication.getContextObject().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        int Width = display.getWidth();
        int Height = display.getHeight();
        weekgridView = new GridView(context);

        weekgridView.setNumColumns(7);
        weekgridView.setColumnWidth(30);
        if (Width == 720 && Height == 1280) {
            weekgridView.setColumnWidth(30);
        }
        weekgridView.setGravity(Gravity.CENTER_VERTICAL);
        weekgridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        // 去除gridView边框
        weekgridView.setVerticalSpacing(0);
        weekgridView.setHorizontalSpacing(0);
        weekgridView.setOnTouchListener(new View.OnTouchListener() {
            // 将gridview中的触摸事件回传给gestureDetector

            public boolean onTouch(View v, MotionEvent event) {
                return DatePopWindow.this.gestureweekDetector.onTouchEvent(event);
            }
        });
        weekgridView.setLayoutParams(params);
        weekgridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Constants.WEEK_CLEARTYPE = 1;
                int startPosition = weekAdapter.getStartPositon();
                int endPosition = weekAdapter.getEndPosition();
                // 这一天的阳历
                weekscheduleDay = weekAdapter.getDateByClickItem(position).split("\\.")[0];
                //获取当前年
                weekscheduleYear = weekAdapter.getShowYear();
                //获取当前月

                if (position > endPosition - 7) {
                    WeekscheduleMonth = Integer.parseInt(weekAdapter.getShowMonth()) + 1;
                } else if (position < startPosition - 7) {
                    WeekscheduleMonth = Integer.parseInt(weekAdapter.getShowMonth()) - 1;
                } else {
                    WeekscheduleMonth = Integer.parseInt(weekAdapter.getShowMonth());
                }
                if (weekscheduleDay.length() == 1) {
                    weekscheduleDay = "0" + weekscheduleDay;
                }

                StringBuilder sbDay = new StringBuilder();
                sbDay.append(weekscheduleYear);
                if ((WeekscheduleMonth + "").length() == 1) {
                    sbDay.append("0" + WeekscheduleMonth);
                } else {
                    sbDay.append(WeekscheduleMonth);
                }
                sbDay.append(weekscheduleDay);
                Constants.WEEK_SELECT_DATE = sbDay.toString();
                Constants.WEEK_SELECT_SUM++;


                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    Constants.WEEK_SELECT_WEEK = sdf.parse(weekscheduleYear + "-" + WeekscheduleMonth + "-" + (Integer.parseInt(weekscheduleDay) - 1));
                    weekAdapter.setDate(sdf.parse(weekscheduleYear + "-" + WeekscheduleMonth + "-" + (Integer.parseInt(weekscheduleDay) - 1)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                confirmflag = true;
                weekAdapter.notifyDataSetChanged();

            }
        });
    }


    /**
     * 移动到下一个月
     *
     * @param gvFlag
     */
    private void enterNextMonth(int gvFlag) {
        addGridView(); // 添加一个gridView
        jumpMonth++; // 下一个月
        calV = new CalendarAdapter(context, context.getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
        gridView.setAdapter(calV);
        calV.setSelection(type);
        calV.notifyDataSetChanged();
        addTextMonthTextView(currentMonth); // 移动到下一月后，将当月显示在头标题中
        addTextYearTextView(currentYear);
        gvFlag++;
        flipper.addView(gridView, gvFlag);
        flipper.showNext();
        flipper.removeViewAt(0);

    }

    private void enterWeekNextMonth(int weekgvFlag) {
        addweekGridView(); // 添加一个gridView
        jumpweekMonth++; // 下一个月
        weekAdapter = new CalendarWeekAdapter(context, context.getResources(), jumpweekMonth, jumpweekYear, year_c, month_c, day_c);
        weekgridView.setAdapter(weekAdapter);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        weekAdapter.setDate(Constants.WEEK_SELECT_WEEK);
        weekAdapter.notifyDataSetChanged();
        addTextWeekMonthTextView(popweek_currentMonth); // 移动到下一月后，将当月显示在头标题中
        addWeekTextYearTextView(popweek_currentyear);
        weekgvFlag++;
        weekflipper.addView(weekgridView, weekgvFlag);

        weekflipper.showNext();
        weekflipper.removeViewAt(0);

    }


    /**
     * 移动到上一个月
     *
     * @param gvFlag
     */
    private void enterPrevMonth(int gvFlag) {

        addGridView(); // 添加一个gridView
        jumpMonth--; // 上一个月
        if (jumpYear + year_c > 2049) {
            UtilsSystem.getInstance().showShortToast(context, "不支持2050年以后数据查询。。。。");
            return;
        }
        calV = new CalendarAdapter(context, context.getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
        gridView.setAdapter(calV);
        calV.setSelection(type);
        calV.notifyDataSetChanged();
        gvFlag++;
        addTextMonthTextView(currentMonth); // 移动到上一月后，将当月显示在头标题中
        addTextYearTextView(currentYear);
        flipper.addView(gridView, gvFlag);
//        flipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.push_right_in));
//        flipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.push_right_out));
        flipper.showPrevious();
        flipper.removeViewAt(0);
    }

    private void enterWeekPrevMonth(int weekgvFlag) {

        addweekGridView(); // 添加一个gridView
        jumpweekMonth--; // 上一个月
        if (jumpweekYear + year_c > 2049) {
            UtilsSystem.getInstance().showShortToast(context, "不支持2050年以后数据查询。。。。");
            return;
        }
        weekAdapter = new CalendarWeekAdapter(context, context.getResources(), jumpweekMonth, jumpweekYear, year_c, month_c, day_c);
        weekgridView.setAdapter(weekAdapter);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        weekAdapter.setDate(Constants.WEEK_SELECT_WEEK);
        weekAdapter.notifyDataSetChanged();
        weekgvFlag++;
        addTextWeekMonthTextView(popweek_currentMonth); // 移动到上一月后，将当月显示在头标题中
        addWeekTextYearTextView(popweek_currentyear);
        weekflipper.addView(weekgridView, weekgvFlag);
//        weekflipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.push_right_in));
//        weekflipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.push_right_out));
        weekflipper.showPrevious();
        weekflipper.removeViewAt(0);
    }

    //日历下一年
    private void enterNextYear(int gvFlag) {

        addGridView(); // 添加一个gridView
        jumpYear++; // 下一年
        if (jumpYear + year_c > 2049) {
            UtilsSystem.getInstance().showShortToast(context, "不支持2050年以后数据查询。。。。");
            return;
        }
        calV = new CalendarAdapter(context, context.getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
        gridView.setAdapter(calV);
        calV.setSelection(type);
        calV.notifyDataSetChanged();
        gvFlag++;
        addTextYearTextView(currentYear); // 移动到下一年后，将当月显示在头标题中
        flipper.addView(gridView, gvFlag);
//        flipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.push_right_in));
//        flipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.push_right_out));
        flipper.showNext();
        flipper.removeViewAt(0);

    }

    //周历下一年
    private void enterWeekNextYear(int weekgvFlag) {

        addweekGridView(); // 添加一个gridView
        jumpweekYear++; // 下一年
        if (jumpweekYear + year_c > 2049) {
            UtilsSystem.getInstance().showShortToast(context, "不支持2050年以后数据查询。。。。");
            return;
        }
        weekAdapter = new CalendarWeekAdapter(context, context.getResources(), jumpweekMonth, jumpweekYear, year_c, month_c, day_c);
        weekgridView.setAdapter(weekAdapter);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        weekAdapter.setDate(Constants.WEEK_SELECT_WEEK);
        weekAdapter.notifyDataSetChanged();
        weekgvFlag++;
        addWeekTextYearTextView(popweek_currentyear); // 移动到下一年后，将当月显示在头标题中
        weekflipper.addView(weekgridView, weekgvFlag);
//        weekflipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.push_right_in));
//        weekflipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.push_right_out));
        weekflipper.showNext();
        weekflipper.removeViewAt(0);

    }

    //日历上一年
    private void enterPrevYear(int gvFlag) {
        addGridView(); // 添加一个gridView
        jumpYear--; // 上一年
        if (jumpYear + year_c > 2049) {
            UtilsSystem.getInstance().showShortToast(context, "不支持2050年以后数据查询。。。。");
            return;
        }
        calV = new CalendarAdapter(context, context.getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
        gridView.setAdapter(calV);
        calV.setSelection(type);
        calV.notifyDataSetChanged();
        gvFlag++;
        addTextYearTextView(currentYear); // 移动到上一年后，将当月显示在头标题中
        flipper.addView(gridView, gvFlag);
//        flipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.push_left_in));
//        flipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.push_left_out));
        flipper.showPrevious();
        flipper.removeViewAt(0);
    }

    //日历上一年
    private void enterPrevWeekYear(int weekgvFlag) {
        addweekGridView(); // 添加一个gridView
        jumpweekYear--; // 上一年
        if (jumpweekYear + year_c > 2049) {
            UtilsSystem.getInstance().showShortToast(context, "不支持2050年以后数据查询。。。。");
            return;
        }
        weekAdapter = new CalendarWeekAdapter(context, context.getResources(), jumpweekMonth, jumpweekYear, year_c, month_c, day_c);
        weekgridView.setAdapter(weekAdapter);

        weekAdapter.setDate(Constants.WEEK_SELECT_WEEK);

        weekAdapter.notifyDataSetChanged();
        weekgvFlag++;
        addWeekTextYearTextView(popweek_currentyear); // 移动到上一年后，将当月显示在头标题中
        weekflipper.addView(weekgridView, weekgvFlag);
//        weekflipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.push_left_in));
//        weekflipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.push_left_out));
        weekflipper.showPrevious();
        weekflipper.removeViewAt(0);
    }

    /**
     * 添加头部的年份 闰哪年等信息
     *
     * @param view
     */
    public void addTextYearTextView(TextView view) {
        StringBuffer textDate = new StringBuffer();
        textDate.append(calV.getShowYear()).append("年");
        view.setText(textDate);
    }

    public void addWeekTextYearTextView(TextView view) {
        StringBuffer textDate = new StringBuffer();
        textDate.append(weekAdapter.getShowYear()).append("年");
        view.setText(textDate);
    }

    public interface SelectedDateListener {
        void setSelectedDate(String date, AppCompatActivity activity);
    }


}
