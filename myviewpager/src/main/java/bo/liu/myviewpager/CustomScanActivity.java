package bo.liu.myviewpager;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.ViewfinderView;

/**
 * Created by Administrator on 2017/11/8 0008.
 */

public class CustomScanActivity extends Activity implements DecoratedBarcodeView.TorchListener{
    private CaptureManager captureManager;
    private DecoratedBarcodeView mDBV;//扫描框
    private Button hint1Show,hint2Show,swichLight;
    private boolean isLightOn = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        initView();
        initListener();
        // 如果没有闪光灯功能，就去掉相关按钮
        if(!hasFlash()) {
            swichLight.setVisibility(View.GONE);
        }

        //重要代码，初始化捕获
        captureManager = new CaptureManager(this,mDBV);
        captureManager.initializeFromIntent(getIntent(),savedInstanceState);
        captureManager.decode();
    }

    private void initListener() {
        swichLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLightOn){
                    mDBV.setTorchOff();
                }else{
                    mDBV.setTorchOn();
                }
            }
        });
    }

    private void initView() {
        mDBV = (DecoratedBarcodeView) findViewById(R.id.dbv_custom);
        hint1Show = (Button) findViewById(R.id.btn_hint1);
        hint2Show = (Button) findViewById(R.id.btn_hint2);
        swichLight = (Button) findViewById(R.id.btn_switch);
        mDBV.setTorchListener(this);


    }

    @Override
    public void onTorchOn() {
        isLightOn = true;
    }

    @Override
    public void onTorchOff() {
        isLightOn = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDBV.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    // 判断是否有闪光灯功能
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }
}
