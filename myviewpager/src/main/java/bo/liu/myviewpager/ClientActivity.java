package bo.liu.myviewpager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.LocaleDisplayNames;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import bo.liu.myviewpager.adpter.ImageAdapter;
import bo.liu.myviewpager.bean.Folder;
import bo.liu.myviewpager.bean.Image;
import bo.liu.myviewpager.bean.TransmitBean;
import bo.liu.myviewpager.util.BluetoothChatUtil;
import bo.liu.myviewpager.util.ImageModel;

/**
 * Created by Administrator on 2017/11/13 0013.
 */

public class ClientActivity extends Activity implements View.OnClickListener {
    private static final int PERMISSION_REQUEST_CODE = 5;
    private Button mBtnBluetoothConnect;
    private Button mBtnBluetoohDisconnect;
    private Button mBtnSendMessage;
    private EditText mEdttMessage;
    private ImageView im,sv_im;
    //设置绑定的蓝牙名称
    public static final String BLUETOOTH_NAME = "HUAWEI P9";
    private TextView mBtConnectState;
    private TextView mTvChat;
    private ProgressDialog mProgressDialog;
    private BluetoothAdapter mBluetoothAdapter;
    private int REQUEST_ENABLE_BT = 1;
    private RecyclerView bu_rv;
    private ArrayList<Folder> folders;
    private boolean isToSettings;//是否弹出设置

    private static final String TAG = "ClientActivity";
    private BluetoothChatUtil mBlthChatUtil;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothChatUtil.STATE_CONNECTED:
                    String deviceName = msg.getData().getString(BluetoothChatUtil.DEVICE_NAME);
                    mBtConnectState.setText("已成功连接到设备" + deviceName);
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    break;
                case BluetoothChatUtil.STATAE_CONNECT_FAILURE:
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "连接失败", Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothChatUtil.MESSAGE_DISCONNECTED:
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mBtConnectState.setText("与设备断开连接");
                    break;
                case BluetoothChatUtil.MESSAGE_READ:
                    byte[] buf = msg.getData().getByteArray(BluetoothChatUtil.READ_MSG);
                    String str = new String(buf, 0, buf.length);
                    Toast.makeText(getApplicationContext(), "读成功" + str, Toast.LENGTH_SHORT).show();
                    mTvChat.setText(mTvChat.getText().toString() + "\n" + str);
                    break;
                case BluetoothChatUtil.MESSAGE_WRITE:
                    byte[] buf1 = (byte[]) msg.obj;
                    String str1 = new String(buf1, 0, buf1.length);
                    Toast.makeText(getApplicationContext(), "发送成功" + str1, Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;

            }
        }
    };
    private ImageAdapter im1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client);
        initView();
       // initData();
        folders = new ArrayList<>();
        initBluetooth();
        mBlthChatUtil = BluetoothChatUtil.getInstance(this);
        mBlthChatUtil.registerHandle(mHandler);
    }

    private void initData() {
        Log.d(TAG, "initData: ++++++++");
        LinearLayoutManager  mlinearLayout= new LinearLayoutManager(this);
        mlinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        bu_rv.setLayoutManager(mlinearLayout);

        if (folders.isEmpty()&&folders.size()<0){
            Log.d(TAG, "initData: ++++++++"+folders.size());
            bu_rv.setVisibility(View.GONE);
        }else {
            Log.d(TAG, "initData: ++++++++"+1121+folders.size());
            bu_rv.setVisibility(View.VISIBLE);
            Folder folder = folders.get(0);
            ArrayList<Image> images = folder.getImages();
            im1 = new ImageAdapter(getApplicationContext(),true,images);
            bu_rv.setAdapter(im1);
            im1.setOnImageSelectListener(new ImageAdapter.OnImageSelectListener() {
                @Override
                public void OnImageSelect(Image image, boolean isSelect, int selectCount) {
                    bu_rv.setVisibility(View.GONE);
                    Glide.with(ClientActivity.this).load(new File(image.getPath()))
                            .diskCacheStrategy(DiskCacheStrategy.NONE).into(sv_im);

//                    File file =new File(image.getPath());
//                    byte[] buff =null;
//                    try {
//                        FileInputStream fis = new FileInputStream(file);
//                        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
//                        byte[] b = new byte[1024];
//                        int n;
//                        while ((n = fis.read(b)) != -1) {
//                            bos.write(b, 0, n);
//                        }
//                        fis.close();
//                        bos.close();
//                        buff = bos.toByteArray();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    TransmitBean ta = new TransmitBean();
                    ta.setFilepath(image.getPath());
                    ta.setFilename(image.getName());
                    mBlthChatUtil.write(ta);

                }
            });
        }

    }

    private void initBluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "设备不支持蓝牙",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        Log.d(TAG, "initBluetooth: ++++++++++");
        if (!mBluetoothAdapter.isEnabled()) {//蓝牙未开启
            Intent enableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
        //注册广播接收者，监听扫描到的蓝牙设备
        IntentFilter filter = new IntentFilter();
        //发现设备
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mBluetoothReceiver, filter);
    }

    private void initView() {
        mBtnBluetoothConnect = (Button) findViewById(R.id.btn_blth_connect);
        mBtnBluetoohDisconnect = (Button) findViewById(R.id.btn_blth_disconnect);
        mBtnSendMessage = (Button) findViewById(R.id.btn_sendmessage);
        mEdttMessage = (EditText) findViewById(R.id.edt_message);
        mBtConnectState = (TextView) findViewById(R.id.tv_connect_state);
        mTvChat = (TextView) findViewById(R.id.tv_chat);
        im = (ImageView) findViewById(R.id.bu_im);
        sv_im = (ImageView) findViewById(R.id.sv_im);
        bu_rv = (RecyclerView) findViewById(R.id.bu_rv);

        mBtnBluetoothConnect.setOnClickListener(this);
        mBtnBluetoohDisconnect.setOnClickListener(this);
        mBtnSendMessage.setOnClickListener(this);
        im.setOnClickListener(this);
        mProgressDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_blth_connect:
                if (mBlthChatUtil.getState() == BluetoothChatUtil.STATE_CONNECTED) {
                    Toast.makeText(this, "蓝牙已连接", Toast.LENGTH_SHORT).show();
                }else {
                    Log.d(TAG, "onClick: ++++++");
                    discoveryDevices();
                }
                break;
            case R.id.btn_blth_disconnect:
                if (mBlthChatUtil.getState() != BluetoothChatUtil.STATE_CONNECTED) {
                    Toast.makeText(this, "蓝牙未连接", Toast.LENGTH_SHORT).show();
                }else {
                    mBlthChatUtil.disconnect();
                }
                break;
            case R.id.btn_sendmessage:
                String messagesend = mEdttMessage.getText().toString();
                if(null == messagesend || messagesend.length() == 0){
                    return;
                }
                mBlthChatUtil.write(messagesend.getBytes());
                break;
            case R.id.bu_im:
                Log.d(TAG, "onClick: +++++++++++++++++++");
                checkPermissionAndLoadImages();
                break;
            default:
                break;
        }
    }

    /**
     * 检查权限并加载SD卡里的图片。
     */
    private void checkPermissionAndLoadImages() {
        Log.d(TAG, "checkPermissionAndLoadImages: ");
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(getApplication(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED) {
            //有权限，加载图片。
            loadImageForSDCard();
            Log.d(TAG, "checkPermissionAndLoadImages: "+6666);
        } else {
            Log.d(TAG, "checkPermissionAndLoadImages: "+8888);
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(ClientActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * 根据授权的返回结果处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: "+PERMISSION_REQUEST_CODE);
        if(requestCode == PERMISSION_REQUEST_CODE){
            Log.d(TAG, "onRequestPermissionsResult: "+PERMISSION_REQUEST_CODE);
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //允许权限，加载图片。
                loadImageForSDCard();
            }else {
                //拒绝权限，弹出提示框。
                showExceptionDialog();
            }
        }

    }

    /**
     * 发生没有权限等异常时，显示一个提示dialog.
     */
    private void showExceptionDialog() {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("提示")
                .setMessage("该相册需要赋予访问存储的权限，请到“设置”>“应用”>“权限”中配置权限。")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {



            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                startAppSettings();
                isToSettings = true;
            }
        }).show();
    }

    /**
     * 启动应用的设置
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    /***
     * 从sd卡加载图片
     */
    private void loadImageForSDCard() {
        ImageModel.loadImageForSDCard(this, new ImageModel.DataCallback() {
            @Override
            public void onSuccess(final ArrayList<Folder> folder) {
                //folders是图片文件夹的列表，每个文件夹中都有若干张图片。
                Log.d(TAG, "onSuccess: +++++++++++");
                    folders =folder;
               Log.d(TAG, "onSuccess: +++++++++++folders"+folders.size());
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Log.d(TAG, "onSuccess: +++++++++++folders"+folders.size());


                            initData();


                    }
                });
            }
        });



    }


    private void discoveryDevices() {
        if(mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
        if (mBluetoothAdapter.isDiscovering()){
            //如果正在扫描则返回
            return;
        }
        mProgressDialog.setTitle(getResources().getString(R.string.progress_scaning));
        mProgressDialog.show();
        // 扫描蓝牙设备
        mBluetoothAdapter.startDiscovery();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult request=" + requestCode + " result=" + resultCode);
        if (requestCode == 1) {
         if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }

    BroadcastReceiver mBluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "onReceive: ++++++++++++");
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //获取连接到的蓝牙
                BluetoothDevice scanDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (scanDevice == null && scanDevice.getName() == null) {
                    return;
                }
                Log.d(TAG, "name=" + scanDevice.getName() + "address=" + scanDevice.getAddress());
                //获取蓝牙设备的名称
                String name = scanDevice.getName();
                if ( name != null&& name.equals(BLUETOOTH_NAME)) {
                    mBluetoothAdapter.cancelDiscovery();//取消扫描
                    mProgressDialog.setTitle(getResources().getString(R.string.progress_connecting));
                    mBlthChatUtil.connect(scanDevice);
                }

            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (mBlthChatUtil!=null){
            if (mBlthChatUtil.getState() == BluetoothChatUtil.STATE_CONNECTED){
                BluetoothDevice connectedDevice = mBlthChatUtil.getConnectedDevice();
                if(null != connectedDevice && null != connectedDevice.getName()){
                    mBtConnectState.setText("已成功连接到设备" + connectedDevice.getName());
                }else {
                    mBtConnectState.setText("已成功连接到设备");
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBlthChatUtil = null;
        unregisterReceiver(mBluetoothReceiver);
    }

    @SuppressWarnings("unused")
    private void getBtDeviceInfo(){
        //获取本机蓝牙名称
        String name = mBluetoothAdapter.getName();
        //获取本机蓝牙地址
        String address = mBluetoothAdapter.getAddress();
        Log.d(TAG,"bluetooth name ="+name+" address ="+address);
        //获取已配对蓝牙设备
        Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
        Log.d(TAG, "bonded device size ="+devices.size());
        for(BluetoothDevice bonddevice:devices){
            Log.d(TAG, "bonded device name ="+bonddevice.getName()+
                    " address"+bonddevice.getAddress());
        }
    }
}
