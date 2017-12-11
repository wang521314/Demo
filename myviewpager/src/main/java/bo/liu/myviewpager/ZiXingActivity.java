package bo.liu.myviewpager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

/**
 * Created by Administrator on 2017/11/8 0008.
 */

public class ZiXingActivity extends Activity implements View.OnClickListener{

    private static final int WHITE = Color.WHITE;
    private static final int BLACK = Color.BLACK;
    private ImageView im;
    private EditText editText;
    private Button bt,bt1;
    private static final String TAG = "ZiXingActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zixing);
        initView();
    }

    private void initView() {
        im = (ImageView) findViewById(R.id.zx_im);
        editText = (EditText) findViewById(R.id.editText);
        bt = (Button) findViewById(R.id.zx_bt);
        bt1 = (Button) findViewById(R.id.zx_bt1);
        bt1.setOnClickListener(this);
        bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.zx_bt1:
                //获取输入的文本信息
                String str = editText.getText().toString().trim();
                if(str != null && !"".equals(str.trim())){
                    //根据输入的文本生成对应的二维码并且显示出来
                    Log.d(TAG, "encodeAsBitmap: wwwww11111");
                    Bitmap mBitmap = encodeAsBitmap(str);
                    if(mBitmap != null){
                        Log.d(TAG, "encodeAsBitmap: ww7");
                        Toast.makeText(this,"二维码生成成功！",Toast.LENGTH_SHORT).show();
                        im.setVisibility(View.VISIBLE);
                        im.setImageBitmap(mBitmap);

                    }
                }else{
                    Toast.makeText(this,"文本信息不能为空！",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.zx_bt:
                customScan();
                break;
        }
    }
   public Bitmap encodeAsBitmap(String str){
        Bitmap bitmap = null;
        BitMatrix result = null;
       Log.d(TAG, "encodeAsBitmap: wwwww");
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            Log.d(TAG, "encodeAsBitmap: wwwww");
            result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, 200, 200);
            // 使用 ZXing Android Embedded 要写的代码
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
//                    int w = result.getWidth();
//        int h = result.getHeight();
//        int[] pixels = new int[w * h];
//        for (int y = 0; y < h; y++) {
//            int offset = y * w;
//            for (int x = 0; x < w; x++) {
//                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
//            }
//        }
//        bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
//        bitmap.setPixels(pixels,0,100,0,0,w,h);
            bitmap = barcodeEncoder.createBitmap(result);
            return bitmap;
        } catch (WriterException e){
            e.printStackTrace();
        } catch (IllegalArgumentException iae){ // ?
            return null;
        }

        // 如果不使用 ZXing Android Embedded 的话，要写的代码

//        int w = result.getWidth();
//        int h = result.getHeight();
//        int[] pixels = new int[w * h];
//        for (int y = 0; y < h; y++) {
//            int offset = y * w;
//            for (int x = 0; x < w; x++) {
//                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
//            }
//        }
//        bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
//        bitmap.setPixels(pixels,0,100,0,0,w,h);

        return bitmap;
    }

    public void customScan(){
        new IntentIntegrator(this)
                .setOrientationLocked(false)
                .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                .initiateScan(); // 初始化扫描
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null) {
            if(intentResult.getContents() == null) {
                Toast.makeText(this,"内容为空",Toast.LENGTH_LONG).show();
            } else {
                String ScanResult = intentResult.getContents();
                Toast.makeText(this,"扫描成功"+ScanResult,Toast.LENGTH_LONG).show();
                // ScanResult 为 获取到的字符串


            }
        } else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

}
