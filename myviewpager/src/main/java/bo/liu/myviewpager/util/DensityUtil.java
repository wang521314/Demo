package bo.liu.myviewpager.util;

import android.content.Context;

import android.icu.text.DecimalFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/11/6 0006.
 */

public class DensityUtil {

    private DensityUtil(){
        /* cannot be instantiated 不能实例化 */
        throw new UnsupportedOperationException("cannot be instantiated");
    }
    /**
     *  根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */

    public static int dip2px(Context context, float f){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (f * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float f){
        final float scale = context.getResources().getDisplayMetrics().density;//该方法获取手机屏幕的密度比
        return (int) (f / scale + 0.5f);
    }

    public static int getScreenW(Context context){
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public static int getScreenH(Context context){
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }


    public static double formatFloat(float f){
        BigDecimal bg = new BigDecimal(f);
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }
}
