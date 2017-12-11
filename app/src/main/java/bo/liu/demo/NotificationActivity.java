package bo.liu.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;



/**
 * Created by renyu on 16/1/9.
 */
public class NotificationActivity extends Activity {




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(0);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}
