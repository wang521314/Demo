package bo.liu.myrx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import bo.liu.myrx.util.CommonUtil;
import bo.liu.myrx.util.Shotter;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class ScreenShotActivity extends Activity {
    public static final int REQUEST_MEDIA_PROJECTION = 0x2893;
    private static final String TAG = "ScreenShotActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        setTheme(android.R.style.Theme_Dialog);//这个在这里设置 之后导致 的问题是 背景很黑
        super.onCreate(savedInstanceState);

        //如下代码 只是想 启动一个透明的Activity 而上一个activity又不被pause
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setDimAmount(0f);

        requestScreenShot();
    }


    public void requestScreenShot() {
        if (Build.VERSION.SDK_INT >= 21) {
            startActivityForResult(
                    ((MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE)).createScreenCaptureIntent(),
                    REQUEST_MEDIA_PROJECTION
            );
        }
        else
        {
            toast("版本过低,无法截屏");
        }
    }

    private void toast(String str) {
//        Toast.makeText(ScreenShotActivity.this,str,Toast.LENGTH_LONG).show();
        CommonUtil.setToast(ScreenShotActivity.this,str);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_MEDIA_PROJECTION: {
                if (resultCode == -1 && data != null) {
                    Log.d(TAG, "onActivityResult: ++++");
                    Shotter shotter=new Shotter(ScreenShotActivity.this,data);
                    shotter.startScreenShot(new Shotter.OnShotListener() {
                        @Override
                        public void onFinish() {
                            toast("shot finish!");
                            finish(); // don't forget finish activity
                        }
                    });
                }
            }
        }
    }
}
