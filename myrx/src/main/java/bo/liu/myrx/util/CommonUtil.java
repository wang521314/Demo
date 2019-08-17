package bo.liu.myrx.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Field;

import bo.liu.myrx.mode.Subject;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public class CommonUtil {

    private static final String TAG = "CommonUtil";
    private CommonUtil (){
        throw new AssertionError();
    }
    public static void setToast(Context context,String s){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }


    public static float dpToPx(Context context, float dp) {
        if(context == null) {
            return -1;
        }
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static int dpToPx(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    public static <T>T getSubject(String title, int img){
        if (!TextUtils.isEmpty(title)){
            Log.d(TAG, "getSubject: "+TextUtils.isEmpty(title));
        }
        Log.d(TAG, "getSubject: "+TextUtils.isEmpty(title)+title+"3333");
        return (T) new Subject(title,img);
    }
    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int screenHeight = dm.heightPixels;
        return screenHeight;
    }

    /**
     * 获取状态栏的高
     */
    public static int getStatusBarHeight(Activity context) {
        Rect frame = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        if(0 == statusBarHeight) {
            statusBarHeight = getStatusBarHeightByReflection(context);
        }
        return statusBarHeight;
    }

    public static int getStatusBarHeightByReflection(Context context) {
        Class<?> c;
        Object obj;
        Field field;
        // 默认为38，貌似大部分是这样的
        int x, statusBarHeight = 38;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }
}
