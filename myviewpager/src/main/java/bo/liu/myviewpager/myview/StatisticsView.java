package bo.liu.myviewpager.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import bo.liu.myviewpager.R;

import static bo.liu.myviewpager.util.DensityUtil.dip2px;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class StatisticsView extends View {

    private int maxValue;//纵轴最大值
    private int dividerCount;//纵轴分割数量
    private String title;//底部标题

    private Paint mBorderPaint;
    private Paint circlePaint;
    private Paint mPathPaint;
    private TextPaint textPaint;
    private Path mPath;
    //纵轴每个单位值
    private int perValue;

    //底部显示String
    private String[] bottomStr = {};

    //具体的值
    private float[] values = {};

    //底部横轴单位间距
    private float bottomGap;

    //左边纵轴间距
    private float leftGap;

    private static final String TAG = "StatisticsView";

    public void setValues(float[] values) {
        this.values = values;
        invalidate();
    }


    public void setBottomStr(String[] bottomStr) {
        this.bottomStr = bottomStr;
        requestLayout();
    }
    public StatisticsView(Context context) {
        this(context,null);
    }

    public StatisticsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StatisticsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.StatisticsView);
        maxValue = array.getInt(R.styleable.StatisticsView_maxValue, 100);
        dividerCount = array.getInt(R.styleable.StatisticsView_dividerCount, 10);
        title = array.getString(R.styleable.StatisticsView_title);
        if (dividerCount ==0){
            return;
        }
        perValue = maxValue/dividerCount;
        int lineColor = array.getColor(R.styleable.StatisticsView_lineColor, Color.BLACK);
        int textColor =array.getColor(R.styleable.StatisticsView_textColor,Color.BLACK);

        mBorderPaint = new Paint();//边界，画横纵轴
        circlePaint = new Paint();//圆心，画坐标点的圆心
        mPathPaint = new Paint();//路线，画折线图

        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(lineColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);//画笔样式
        mBorderPaint.setStrokeWidth(1);
        mPathPaint.setAntiAlias(true);
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setStrokeWidth(3);

        textPaint = new TextPaint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(dip2px(getContext(),12));
        mPath = new Path();
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);

        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.d(TAG, "onMeasure: widthSize"+widthSize+"heightSize"+heightSize);
        if (widthMode==MeasureSpec.EXACTLY&&heightMode==MeasureSpec.EXACTLY){
            setMeasuredDimension(widthSize,heightSize);//设置当前view的大小
        }else if (widthMeasureSpec==MeasureSpec.EXACTLY){
            setMeasuredDimension(widthSize,widthSize);
        }else if (heightMeasureSpec==MeasureSpec.EXACTLY){
            setMeasuredDimension(heightSize,heightSize);
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d(TAG, "onLayout: getwidth"+getWidth()+"&&"+getHeight());
        bottomGap = getWidth()/(bottomStr.length+1);
        leftGap = getHeight()/(dividerCount+2);
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bottomStr == null&&bottomStr.length==0){//当底部字符串不存在就不画
            return;
        }
        //1.先画左边的
        canvas.drawLine(bottomGap,getHeight()-leftGap,bottomGap,leftGap,mBorderPaint);
        Log.d(TAG, "onDraw: bottomGap"+bottomGap+"getHeight()-leftGap"+(getHeight()-leftGap)+"bottomGap"+bottomGap+"lefGap"+leftGap);
        float fontHeight =(textPaint.getFontMetrics().descent-textPaint.getFontMetrics().ascent);

        //2.画下边的的线
       // canvas.drawLine(bottomGap,getHeight()-leftGap,getWidth()-bottomGap,getHeight()-leftGap,mBorderPaint);
        for (int i = 1;i<=bottomStr.length;i++){
            canvas.drawCircle(i*bottomGap,getHeight()-leftGap,6,circlePaint);
            canvas.drawText(bottomStr[i-1],i*bottomGap-(textPaint.measureText(bottomStr[i-1])/2),getHeight()-leftGap/2+fontHeight/2,textPaint);
        }

        canvas.drawText(title,bottomGap,leftGap/2,textPaint);
        for (int i = 1;i<=dividerCount+1;i++){
            //画左边的字
            canvas.drawText(perValue*(i-1)+"",bottomGap/2-(textPaint.measureText(perValue*(i-1)+"")/2),(((dividerCount+2-i)))*leftGap+fontHeight/2,textPaint);

            //画横线
            canvas.drawLine(bottomGap,getHeight()-((i)*leftGap),getWidth()-bottomGap,getHeight()-((i)*leftGap),mBorderPaint);
        }
        /**
         * 画轨迹
         * y的坐标点根据 y/leftGap = values[i]/perValue 计算
         *
         */
        for (int i = 0;i<values.length;i++){
            if (i==0){
                mPath.moveTo(bottomGap,(dividerCount+1)*leftGap-(values[i]*leftGap/perValue));
            }else{
                mPath.lineTo((i+1)*bottomGap,(dividerCount+1)*leftGap-(values[i]*leftGap/perValue));
            }
            /**
             * 画轨迹圆点
             */
            canvas.drawCircle((i+1)*bottomGap,(dividerCount+1)*leftGap-(values[i]*leftGap/perValue),6,circlePaint);
        }
        canvas.drawPath(mPath,mPathPaint);
    }
}
