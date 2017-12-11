package bo.liu.myviewpager;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import bo.liu.myviewpager.myview.QuickIndexView;


/**
 * Created by Administrator on 2017/11/6 0006.
 */

public class ReMengActivity extends Activity {

    private QuickIndexView indexView;
    private TextView rm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remen);
        initView();
    }

    private void initView() {
        indexView = (QuickIndexView) findViewById(R.id.quickIndexView);
        rm = (TextView) findViewById(R.id.rm_tv);
        indexView.setOnIndexChangeListener(new QuickIndexView.OnIndexChangeListener() {
            @Override
            public void onIndexChange(String words) {
                rm.setVisibility(View.VISIBLE);
              rm.setText(words);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rm.setVisibility(View.GONE);
                    }
                },2000);
            }
        });
    }
}
