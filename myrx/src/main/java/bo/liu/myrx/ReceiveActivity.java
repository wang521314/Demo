package bo.liu.myrx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import bo.liu.myrx.mode.EventMode;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class ReceiveActivity extends BaseActivity {

    private Button tv;
    private static final String TAG = "ReceiveActivity";

    @Override
    public int initContentView() {
        return R.layout.activity_receive;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }

    private void initView() {
        tv = (Button) findViewById(R.id.re_tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventMode eventMode = new EventMode();
                eventMode.setText("xisix;");
                EventBus.getDefault().post(eventMode);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventMainThread(EventMode eventMode){
        Log.d(TAG, "onEventMainThread: "+eventMode.getText());
    }
}
