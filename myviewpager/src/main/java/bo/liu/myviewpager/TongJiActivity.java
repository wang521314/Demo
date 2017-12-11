package bo.liu.myviewpager;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;

import bo.liu.myviewpager.myview.PieChartView;
import bo.liu.myviewpager.myview.StatisticsView;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class TongJiActivity extends Activity implements OnClickListener{
    boolean isFangda =false;
    //动画缩放倍数
    private float mAnimZoomTo=5;
    private AccelerateDecelerateInterpolator mZoomInInterpolator,mZoomOutInterpolator;

    private StatisticsView tj_sv;
    private PieChartView pieChartView;
    private static final String TAG = "TongJiActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tongji);
        initView();
        initListener();
    }

    private void initListener() {
        pieChartView.setOnClickListener(this);

    }

    private void initView() {
        tj_sv = (StatisticsView) findViewById(R.id.tj_sv);
        pieChartView = (PieChartView) findViewById(R.id.pie_chart);
        PieChartView.PieItemBean[] items = new PieChartView.PieItemBean[]{
                new PieChartView.PieItemBean("娱乐", 200),
                new PieChartView.PieItemBean("旅行", 100),
                new PieChartView.PieItemBean("学习", 120),
                new PieChartView.PieItemBean("人际关系", 100),
                new PieChartView.PieItemBean("交通", 100),
                new PieChartView.PieItemBean("餐饮", 480)
        };
        pieChartView.setPieItems(items);
        tj_sv.setBottomStr(new String[]{"星期一","星期二","星期三","星期四","星期五","星期六","星期天"});
        tj_sv.setValues(new float[]{10f,90f,33f,66f,42f,99f,0f});
    }


    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: 333333333333333");
        if (!isFangda){
            zoomInAnim(v);
        }else {
            zoomOutAnim(v);
        }

    }

    private void zoomOutAnim(View view) {
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(view, "scaleX", mAnimZoomTo, 1.0f);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(view, "scaleY", mAnimZoomTo, 1.0f);
        objectAnimatorX.setDuration(1000);
        objectAnimatorX.setInterpolator(mZoomOutInterpolator);
        objectAnimatorY.setDuration(1000);
        objectAnimatorY.setInterpolator(mZoomOutInterpolator);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(objectAnimatorX, objectAnimatorY);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isFangda = !isFangda;

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isFangda =!isFangda;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.start();
    }

    private void zoomInAnim(final View view) {
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, mAnimZoomTo);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, mAnimZoomTo);
        objectAnimatorX.setDuration(10000);
        objectAnimatorX.setInterpolator(mZoomInInterpolator);
        objectAnimatorY.setDuration(10000);
        objectAnimatorY.setInterpolator(mZoomInInterpolator);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(objectAnimatorX, objectAnimatorY);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
//放大动画开始
                Log.d(TAG, "onAnimationStart: 放大开始"+isFangda);

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                isFangda = !isFangda;
                Log.d(TAG, "onAnimationEnd: 放大结束"+isFangda);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isFangda = !isFangda;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.start();
    }
}
