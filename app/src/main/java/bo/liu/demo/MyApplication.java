package bo.liu.demo;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/10/25 0025.
 */

public class MyApplication extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
    //返回
    public static Context getContextObject(){
        return mContext;
    }

}
