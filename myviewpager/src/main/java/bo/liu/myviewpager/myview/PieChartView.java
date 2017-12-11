package bo.liu.myviewpager.myview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import bo.liu.myviewpager.util.DensityUtil;


/**
 * Created by Administrator on 2017/11/22 0022.
 */

public class PieChartView extends View {

    private int screenW;
    private int screenH;
    /**
     * The center and the radius of the pie.
     * 饼状图的中心和半径
     */
    private int pieCenterX, pieCenterY, pieRadius;
    private int smallMargin;
    /**
     * The oval to draw the oval in.
     * 绘制圆形
     */
    private RectF pieOval;
    private Paint textPaint;
    private Paint piePaint;

    private int[] mPieColors = new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.CYAN};

    private PieItemBean[] mPieItems;
    private float totalValue;
    private Paint linePaint;
    //The degree position of the last item arc's center.最后一个项目弧中心的度数位置。
    private float lastDegree = 0;
    //The count of the continues 'small' item.继续“小”项目的计数。
    private int addTimes = 0;




    public PieChartView(Context context) {
        this(context, null);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        screenW = DensityUtil.getScreenW(context);
        screenH = DensityUtil.getScreenH(context);
//        pieCenterX = screenW / 3;
//        pieCenterY = screenH / 4;
//        pieRadius = screenW / 5;


//        pieCenterX = getWidth()/ 2;
//        pieCenterY = getHeight() / 3;
//        pieRadius = getWidth() / 4;

        smallMargin = DensityUtil.dip2px(context, 3);

//        pieOval = new RectF();
//        pieOval.left = pieCenterX - pieRadius;//原因在这
//        pieOval.top = pieCenterY - pieRadius;
//        pieOval.right = pieCenterX + pieRadius;
//        pieOval.bottom = pieCenterY + pieRadius;

        //The paint to draw text.绘制文本的颜料
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(DensityUtil.dip2px(context, 16));

        //The paint to draw circle.绘制圆
        piePaint = new Paint();
        piePaint.setAntiAlias(true);
        piePaint.setStyle(Paint.Style.FILL);

        //The paint to draw line to show the concrete text画出线上显示的具体文字
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(DensityUtil.dip2px(context, 1));

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        Log.d("TongJiActivity", "onMeasure: widthSize"+widthSize+"heightSize"+heightSize);
//        if (widthMode==MeasureSpec.EXACTLY&&heightMode==MeasureSpec.EXACTLY){
//            setMeasuredDimension(widthSize,heightSize);//设置当前view的大小
//        }else if (widthMeasureSpec==MeasureSpec.EXACTLY){
//            setMeasuredDimension(widthSize,widthSize);
//        }else if (heightMeasureSpec==MeasureSpec.EXACTLY){
//            setMeasuredDimension(heightSize,heightSize);
//        }
       // setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        pieCenterX =  getMeasuredWidth()/ 2;
        pieCenterY = getMeasuredHeight() / 2;
        pieRadius = Math.min(pieCenterX,pieCenterY)-10;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        pieOval = new RectF();
        pieOval.left = pieCenterX - pieRadius;//原因在这
        pieOval.top = pieCenterY - pieRadius;
        pieOval.right = pieCenterX + pieRadius;
        pieOval.bottom = pieCenterY + pieRadius;

        if (mPieItems != null && mPieItems.length > 0) {
            float start = 0.0f;
            for (int i = 0; i < mPieItems.length; i++) {
                //draw pie
                piePaint.setColor(mPieColors[i % mPieColors.length]);
                //piePaint.setColor(mPieColors[i]);
                float sweep = mPieItems[i].getItemValue() / totalValue * 360;
                canvas.drawArc(pieOval, start, sweep, true, piePaint);

                //draw line away from the pie 划界限
                float radians = (float) ((start + sweep / 2) / 180 * Math.PI);
                Log.d("TongJiActivity", "init: sweep"+sweep+"radians"+radians+"totalValue"+totalValue);
                float lineStartX = pieCenterX + pieRadius * 0.7f * (float) (Math.cos(radians));
                float lineStartY = pieCenterY + pieRadius * 0.7f * (float) (Math.sin(radians));

                float lineStopX, lineStopY;
                float rate;
                if (getOffset(start + sweep / 2) > 60) {
                  //  rate = 1.3f;
                    rate = 1.1f;
                } else if (getOffset(start + sweep / 2) > 30) {
                    //rate = 1.2f;
                    rate = 1.0f;
                } else {
                    //rate = 1.1f;
                    rate = 0.9f;
                }
                //If the item is very small, make the text further away from the pie to avoid being hided by other text.
                if (start + sweep / 2 - lastDegree < 30) {
                    addTimes++;
                    rate += 0.2f * addTimes;
                } else {
                    addTimes = 0;
                }
                lineStopX = pieCenterX + pieRadius * rate * (float) (Math.cos(radians));
                lineStopY = pieCenterY + pieRadius * rate * (float) (Math.sin(radians));
                canvas.drawLine(lineStartX, lineStartY, lineStopX, lineStopY, linePaint);

                //write text
                String itemTypeText = mPieItems[i].getItemType();
                String itemPercentText = DensityUtil.formatFloat(mPieItems[i].getItemValue() / totalValue * 100) + "%";

                float itemTypeTextLen = textPaint.measureText(itemTypeText);
                float itemPercentTextLen = textPaint.measureText(itemPercentText);
                float lineTextWidth = Math.max(itemTypeTextLen, itemPercentTextLen);

                float textStartX = lineStopX;
                float textStartY = lineStopY - smallMargin;
                float percentStartX = lineStopX;
                float percentStartY = lineStopY + textPaint.getTextSize();
                if (lineStartX > pieCenterX) {
                    textStartX += (smallMargin + Math.abs(itemTypeTextLen - lineTextWidth) / 2);
                    percentStartX += (smallMargin + Math.abs(itemPercentTextLen - lineTextWidth) / 2);
                } else {
                    textStartX -= (smallMargin + lineTextWidth - Math.abs(itemTypeTextLen - lineTextWidth) / 2);
                    percentStartX -= (smallMargin + lineTextWidth - Math.abs(itemPercentTextLen - lineTextWidth) / 2);
                }
                textPaint.setColor(mPieColors[i % mPieColors.length]);
                canvas.drawText(itemTypeText, textStartX, textStartY, textPaint);
                //draw percent text
                canvas.drawText(itemPercentText, percentStartX, percentStartY, textPaint);

                //draw text underline
                float textLineStopX = lineStopX;
                if (lineStartX > pieCenterX) {
                    textLineStopX += (lineTextWidth + smallMargin * 2);
                } else {
                    textLineStopX -= (lineTextWidth + smallMargin * 2);
                }
                canvas.drawLine(lineStopX, lineStopY, textLineStopX, lineStopY, linePaint);

                lastDegree = start + sweep / 2;
                start += sweep;
            }
        }
    }


    public PieItemBean[] getPieItems() {
        return mPieItems;
    }

    public void setPieItems(PieItemBean[] pieItems) {
        this.mPieItems = pieItems;

        totalValue = 0;
        for (PieItemBean item : mPieItems) {
            totalValue += item.getItemValue();
        }

        invalidate();
    }

    private float getOffset(float radius) {
        int a = (int) (radius % 360 / 90);
        switch (a) {
            case 0:
                return radius;
            case 1:
                return 180 - radius;
            case 2:
                return radius - 180;
            case 3:
                return 360 - radius;
        }

        return radius;
    }



    public static class PieItemBean {
        private String itemType;
        private float itemValue;

        public PieItemBean(String itemType, float itemValue) {
            this.itemType = itemType;
            this.itemValue = itemValue;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public float getItemValue() {
            return itemValue;
        }

        public void setItemValue(float itemValue) {
            this.itemValue = itemValue;
        }
    }


}
