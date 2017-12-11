package bo.liu.myviewpager;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Set;



/**
 * Created by Administrator on 2017/11/13 0013.
 */

public class BuleActivity extends Activity implements View.OnClickListener{

    private Button ba_bt,ba_tv;

    private BluetoothAdapter mBluetoothAdapter;
    private  String TAG = "bule";
    String BLUETOOTH_NAME = "Galaxy Nexus";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bule);
        initView();
    }

    private void initView() {
        ba_bt = (Button) findViewById(R.id.ba_bt);
        ba_tv = (Button) findViewById(R.id.ba_tv);
        ba_bt.setOnClickListener(this);
        ba_tv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ba_bt:
                //做客户端，主动连接服务端。
                Intent client = new Intent(this, ClientActivity.class);
                startActivity(client);
                finish();
                break;
            case R.id.ba_tv:
                //做服务端，等待客户端的连接请求。
                Intent server = new Intent(this, ServerActivity.class);
                startActivity(server);
                finish();
                break;
        }

    }
}
