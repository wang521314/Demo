package bo.liu.myviewpager.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import bo.liu.myviewpager.util.DensityUtil;

/**
 * Created by Administrator on 2017/11/6 0006.
 */

public class QuickIndexView extends View {
    private final static String[] WORDS = {"当", "热", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z", "#"};//设置城市的字母
    private Paint paint;

    private int cellWidth; //字体占用的空间
    private int cellHeight;
    private int curIndex = -1; //索引

    private OnIndexChangeListener indexChangeListener;

    public QuickIndexView(Context context) {
        this(context, null);
    }

    public QuickIndexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickIndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);//设置画笔
        //paint.setColor(Color.BLACK);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
         paint.setAntiAlias(true);//抗锯齿
        paint.setTextSize(DensityUtil.dip2px(getContext(), 11));
        // paint.setFakeBoldText(true);//设置字体为粗体
        //粗体

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        cellWidth = getMeasuredWidth();
        cellHeight = getMeasuredHeight() / WORDS.length;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < WORDS.length; i++) {
            String words = WORDS[i];
            Rect bound = new Rect();
            paint.getTextBounds(words, 0, words.length(), bound);
            int x = (cellWidth - bound.width()) / 2;
            int y = i * cellHeight + (cellHeight + bound.height()) / 2;
            if (i == curIndex) {
                paint.setColor(Color.RED);
                paint.setFakeBoldText(true);
            }else{
                paint.setColor(Color.BLACK);
            }
            canvas.drawText(words, x, y, paint);



        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int y = (int) event.getY();
                index = y / cellHeight;
                if (index >= 0 && index < WORDS.length) {
                    if (index != curIndex) {

                        if (indexChangeListener != null) {
                            indexChangeListener.onIndexChange(WORDS[index]);
                            curIndex = index;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                curIndex = -1;
                break;
            case MotionEvent.ACTION_MOVE:
                int y1 = (int) event.getY();
                index = y1 / cellHeight;
                if (index >= 0 && index < WORDS.length) {
                    if (index != curIndex) {

                        if (indexChangeListener != null) {
                            indexChangeListener.onIndexChange(WORDS[index]);
                            curIndex = index;
                        }
                    }
                }
                break;
        }
        //重绘，被选中字母颜色变化
        invalidate();
        return true;
    }


    public void setOnIndexChangeListener(OnIndexChangeListener indexChangeListener) {
        this.indexChangeListener = indexChangeListener;
    }

    public interface OnIndexChangeListener {
        void onIndexChange(String words);
    }
}
