package bo.liu.myviewpager.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.UUID;

import bo.liu.myviewpager.bean.TransmitBean;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2017/11/13 0013.
 */

public class BluetoothChatUtil {
    Context mContext;
    private static final boolean D = true;
    private static BluetoothChatUtil mBluetoothChatUtil;
    private final BluetoothAdapter mAdapter;
    private Handler mHandler;
    private int mState;
    private static final String TAG = "BluetoothChatUtil";
    private AcceptThread mAcceptThread;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private BluetoothDevice mConnectedBluetoothDevice;
    private long downbl;
    // 服务名 SDP
    private static final String SERVICE_NAME = "BluetoothChat";
    // uuid SDP
    private static final UUID SERVICE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    //常数，指示当前的连接状态
    public static final int STATE_NONE = 0;       // 当前没有可用的连接
    public static final int STATE_LISTEN = 1;     // 现在侦听传入的连接
    public static final int STATE_CONNECTING = 2; // 现在开始连接
    public static final int STATE_CONNECTED = 3;  // 现在连接到远程设备
    public static final int STATAE_CONNECT_FAILURE = 4; //连接失败

    /**
     * Action：发送文件的百分比
     */
    public  String ACTION_FILE_SEND_PERCENT = "ACTION_FILE_SEND_PERCENT";

    /**
     * Action：接收文件的百分比
     */
    public static final String ACTION_FILE_RECIVE_PERCENT = "ACTION_FILE_RECIVE_PERCENT";
    /**
     * Message：发送文件的百分比
     */
    public static final int FILE_SEND_PERCENT = 11;
    /**
     * Message：接收文件的百分比
     */
    public static final int FILE_RECIVE_PERCENT = 0x00000009;
    /**
     * Message：读取到一个对象
     */
    public static final int MESSAGE_READ_OBJECT = 10;
    public static final int MESSAGE_DISCONNECTED = 5;
    public static final int STATE_CHANGE = 6;
    public static final String DEVICE_NAME = "device_name";
    public static final int MESSAGE_READ = 7;
    public static final int MESSAGE_WRITE= 8;
    public static final String READ_MSG = "read_msg";
    private BluetoothChatUtil(Context context){
        this.mContext=context.getApplicationContext();
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
       // EventBus.getDefault().register(this);
    }
    public static  BluetoothChatUtil getInstance(Context context){
        if (mBluetoothChatUtil == null){
            synchronized (BluetoothChatUtil.class){
                if (mBluetoothChatUtil == null){
                    mBluetoothChatUtil = new BluetoothChatUtil(context);
                }
            }
        }
        return  mBluetoothChatUtil;
    }
  public void registerHandle(Handler handler){
         mHandler = handler;

  }

  public void unRegisterHandle(){
      mHandler = null;
  }
    /**
     * 设置当前状态的聊天连接
     * @param state  整数定义当前连接状态
     */
    private synchronized void setState(int state) {
        if (D) Log.d(TAG, "setState() " + mState + " -> " + state);
        mState = state;
        // 给新状态的处理程序，界面活性可以更新
        mHandler.obtainMessage(STATE_CHANGE, state, -1).sendToTarget();
    }
    /**
     * 返回当前的连接状态。 */
    public synchronized int getState() {
        return mState;
    }

    public BluetoothDevice getConnectedDevice(){
        return mConnectedBluetoothDevice;
    }

    /**
     * 开始聊天服务。特别acceptthread开始
     * 开始服务器模式。 */
    public synchronized void startListen() {
        if (D) Log.d(TAG, "start");
        // 取消任何线程正在运行的连接
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}
        // 启动线程来监听一个bluetoothserversocket
        if (mAcceptThread == null) {
            mAcceptThread = new AcceptThread();
            mAcceptThread.start();
        }
        setState(STATE_LISTEN);
    }

    /**
     * 开始connectthread启动连接到远程设备。
     * @param device 连接的蓝牙设备
     */
    public synchronized void connect(BluetoothDevice device) {
        if (D) Log.d(TAG, "connect to: " + device);
        // 取消任何线程试图建立连接
        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        }
        // 取消任何线程正在运行的连接
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}
        //启动线程连接到远程设备
        mConnectThread = new ConnectThread(device);
        mConnectThread.start();
        setState(STATE_CONNECTING);
    }

    /**
     * 开始ConnectedThread开始管理一个蓝牙连接,传输、接收数据.
     * @param socket socket连接
     * @param device 已连接的蓝牙设备
     */
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
        if (D) Log.d(TAG, "connected");
        //取消任何线程正在运行的连接
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}
        // 启动线程管理连接和传输
        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();
        //把连接设备的名字传到 ui Activity
        mConnectedBluetoothDevice =  device;
        Message msg = mHandler.obtainMessage(STATE_CONNECTED);
        Bundle bundle = new Bundle();
        bundle.putString(DEVICE_NAME, device.getName());
        msg.setData(bundle);
        mHandler.sendMessage(msg);
        setState(STATE_CONNECTED);
    }
    /**
     * 停止所有的线程
     */
    public synchronized void disconnect() {
        if (D) Log.d(TAG, "disconnect");
        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}
        if (mAcceptThread != null) {mAcceptThread.cancel(); mAcceptThread = null;}
        setState(STATE_NONE);
    }

    /**
     * Write to the ConnectedThread in an unsynchronized manner
     * @param out The bytes to write
     * @see ConnectedThread#write(byte[])
     */
    public void write(byte[] out) {
        //创建临时对象
        ConnectedThread r;
        // 同步副本的connectedthread
        synchronized (this) {
            if (mState != STATE_CONNECTED) return;
            r = mConnectedThread;
        }
        // 执行写同步
        r.write(out);
    }

    public void write(Object o){
        //创建临时对象
        ConnectedThread r;
        // 同步副本的connectedthread
        synchronized (this) {
            if (mState != STATE_CONNECTED) return;
            r = mConnectedThread;
        }
        // 执行写同步
        r.write(o);
    }
    /**
     * Indicate that the connection attempt failed and notify the UI Activity.
     */
    private void connectionFailed() {
        // 发送失败的信息带回活动
        Message msg = mHandler.obtainMessage(STATAE_CONNECT_FAILURE);
        mHandler.sendMessage(msg);
        mConnectedBluetoothDevice = null;
        setState(STATE_NONE);
    }

    /**
     * Indicate that the connection was lost and notify the UI Activity.
     */
    private void connectionLost() {
        // 发送失败的信息带回Activity
        Message msg = mHandler.obtainMessage(MESSAGE_DISCONNECTED);
        mHandler.sendMessage(msg);
        mConnectedBluetoothDevice = null;
        setState(STATE_NONE);
    }

    /**
     *本线程 侦听传入的连接。
     *它运行直到连接被接受（或取消）。
     */
    private class AcceptThread extends Thread {
        // 本地服务器套接字
        private final BluetoothServerSocket mServerSocket;
        public AcceptThread() {
            BluetoothServerSocket tmp = null;
            // 创建一个新的侦听服务器套接字
            try {
                tmp = mAdapter.listenUsingRfcommWithServiceRecord(
                        SERVICE_NAME, SERVICE_UUID);
                //tmp = mAdapter.listenUsingInsecureRfcommWithServiceRecord(SERVICE_NAME, SERVICE_UUID);
            } catch (IOException e) {
                Log.e(TAG, "listen() failed", e);
            }
            mServerSocket = tmp;
        }

        public void run() {
            if (D) Log.d(TAG, "BEGIN mAcceptThread" + this);
            setName("AcceptThread");
            BluetoothSocket socket = null;
            // 循环，直到连接成功
            while (mState != STATE_CONNECTED) {
                try {
                    // 这是一个阻塞调用 返回成功的连接
                    // mServerSocket.close()在另一个线程中调用，可以中止该阻塞
                    socket = mServerSocket.accept();
                } catch (IOException e) {
                    Log.e(TAG, "accept() failed", e);
                    break;
                }
                // 如果连接被接受
                if (socket != null) {
                    synchronized (BluetoothChatUtil.this) {
                        switch (mState) {
                            case STATE_LISTEN:
                            case STATE_CONNECTING:
                                // 正常情况。启动ConnectedThread。
                                connected(socket, socket.getRemoteDevice());
                                break;
                            case STATE_NONE:
                            case STATE_CONNECTED:
                                // 没有准备或已连接。新连接终止。
                                try {
                                    socket.close();
                                } catch (IOException e) {
                                    Log.e(TAG, "Could not close unwanted socket", e);
                                }
                                break;
                        }
                    }
                }
            }
            if (D) Log.i(TAG, "END mAcceptThread");
        }

        public void cancel() {
            if (D) Log.d(TAG, "cancel " + this);
            try {
                mServerSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of server failed", e);
            }
        }
    }

    /**
     * 本线程用来连接设备
     *
     */
    private class ConnectThread extends Thread {
        private BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        public ConnectThread(BluetoothDevice device) {
            mmDevice = device;
            BluetoothSocket tmp = null;
            // 得到一个bluetoothsocket
            try {
                mmSocket = device.createRfcommSocketToServiceRecord
                        (SERVICE_UUID);
            } catch (IOException e) {
                Log.e(TAG, "create() failed", e);
                mmSocket = null;
            }
        }

        public void run() {
            Log.i(TAG, "BEGIN mConnectThread");
            try {
                // socket 连接,该调用会阻塞，直到连接成功或失败
                mmSocket.connect();
            } catch (IOException e) {
                connectionFailed();
                try {//关闭这个socket
                    mmSocket.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                return;
            }
            // 启动连接线程
            connected(mmSocket, mmDevice);
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }

    /**
     * 本线程server 和client共用.
     * 它处理所有传入和传出的数据。
     */
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final DataInputStream mmInStream;
        private final DataOutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            Log.d(TAG, "create ConnectedThread");
            mmSocket = socket;
            DataInputStream tmpIn = null;
            DataOutputStream tmpOut = null;
            // 获得bluetoothsocket输入输出流
            try {
                tmpIn = new DataInputStream(socket.getInputStream());
                tmpOut =new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                Log.e(TAG, "没有创建临时sockets", e);
            }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            // 监听输入流
            while (true) {
                try {
//                    byte[] buffer = new byte[1024];
//                    // 读取输入流
//                    int bytes = mmInStream.read(buffer);
//                    // 发送获得的字节的ui activity
//                    Message msg = mHandler.obtainMessage(MESSAGE_READ);
//                    Bundle bundle = new Bundle();
//                    bundle.putByteArray(READ_MSG, buffer);
//                    msg.setData(bundle);
//                    mHandler.sendMessage(msg);
                   read();
                } catch (IOException e) {
                    Log.e(TAG, "disconnected", e);
                    connectionLost();
                    break;
                }
            }
        }
        /**
         * 向外发送。
         * @param buffer  发送的数据
         */
        public void write(byte[] buffer) {
            try {
                mmOutStream.write(buffer);
                // 分享发送的信息到Activity
                mHandler.obtainMessage(MESSAGE_WRITE, -1, -1, buffer)
                        .sendToTarget();
            } catch (IOException e) {
                Log.e(TAG, "Exception during write", e);
            }
        }

        public void write(Object obj){
            TransmitBean transmit_s = (TransmitBean) obj;
            if(transmit_s.getFilename()!=null&&!"".equals(transmit_s.getFilename())){
                String filename=transmit_s.getFilename();
                byte type = 2; //类型为2，即传文件
                //读取文件长度
                FileInputStream fins= null;
                try {
                    fins = new FileInputStream(transmit_s.getFilepath());
                    long fileDataLen = fins.available(); //文件的总长度
                    int f_len=filename.getBytes("UTF-8").length; //文件名长度
                    byte[] data=new byte[f_len];
                    data=filename.getBytes("UTF-8");
                    long totalLen = 4+1+1+f_len+fileDataLen;//数据的总长度
                    mmOutStream.writeLong(totalLen); //1.写入数据的总长度
                    mmOutStream.writeByte(type);//2.写入类型
                    mmOutStream.writeByte(f_len); //3.写入文件名的长度
                    mmOutStream.write(data);    //4.写入文件名的数据
                    mmOutStream.flush();

                    byte[] buffer=new byte[1024*64];
                    downbl=0;
                    int size=0;
                    long sendlen=0;
                    float tspeed=0;
                    int i=0;
                    long time1= Calendar.getInstance().getTimeInMillis();
                    while((size=fins.read(buffer, 0, 1024*64))!=-1)
                    {
                        mmOutStream.write(buffer, 0, size);
                        mmOutStream.flush();
                        sendlen+=size;
                        Log.v("调试" , "fileDataLen:"+fileDataLen);
                        i++;
                        if(i%5==0){
                            long time2=Calendar.getInstance().getTimeInMillis();
                            tspeed=sendlen/(time2-time1)*1000/1024;
                        }
                        //	Log.v("调试" ,"tspeed："+tspeed);
                        downbl = ((sendlen * 100) / fileDataLen);
                        TransmitBean up = new TransmitBean();
                        up.setUppercent(String.valueOf(downbl));
                        up.setTspeed(String.valueOf(tspeed));

                        if(i==1){
                            up.setShowflag(true);
                        }else{
                            up.setShowflag(false);
                        }
                        Message msg = mHandler.obtainMessage();
                        msg.what = FILE_SEND_PERCENT;
                        msg.obj = up;
                        msg.sendToTarget();
                    }
                    fins.close();
                    Log.v("调试" , "文件发送完成");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else{
                Log.v("调试" , "type:"+1);
                byte type = 1; //类型为1，即传文本消息
                String msg=transmit_s.getMsg();
                int f_len= 0; //消息长度
                try {
                    f_len = msg.getBytes("UTF-8").length;
                    long totalLen = 4+1+1+f_len;//数据的总长度
                    byte[] data=new byte[f_len];
                    data=msg.getBytes("UTF-8");
                    mmOutStream.writeLong(totalLen); //1.写入数据的总长度
                    mmOutStream.writeByte(type);//2.写入类型
                    mmOutStream.writeByte(f_len); //3.写入消息的长度
                    mmOutStream.write(data);    //4.写入消息数据
                    mmOutStream.flush();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            try {
                this.read();
                byte[] ef = new byte[3];
                mmInStream.read(ef);//读取消息
                String eof = new String(ef);
                if("EOF".equals(eof)){
                    Log.v("调试" ,"接收EOF");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            }

        public void read() throws IOException{
            TransmitBean transmit_r = new TransmitBean();
            long totalLen = mmInStream.readLong();//总长度
            if(totalLen>0){
                byte type = mmInStream.readByte();//类型
                if(type==1){//文本类型
                    try {
                        byte len = mmInStream.readByte();//消息长度
                        byte[] ml = new byte[len];
                        int size=0;
                        int receivelen=0;
                        while (receivelen <len){
                            size=mmInStream.read(ml,0,ml.length);
                            receivelen+=size;
                        }
                        String msg = new String(ml);
                        Log.v("调试" , "msg:"+msg);
                        transmit_r.setMsg(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(type==2){//文件类型
                    try {
                        byte len = mmInStream.readByte();//文件名长度
                        byte[] fn = new byte[len];
                        mmInStream.read(fn);//读取文件名
                        String filename = new String(fn,"GBK");
                        Log.v("调试" , "filename:"+filename);
                        transmit_r.setFilename(filename);
                        long datalength = totalLen-1-4-1-fn.length;//文件数据
                        String savePath = Environment.getExternalStorageDirectory().getPath() + "/" + transmit_r.getFilename();
                        transmit_r.setFilepath(savePath);
                        FileOutputStream file=new FileOutputStream(savePath, false);
                        byte[] buffer = new byte[1024*1024];
                        int size = -1;
                        long receivelen=0;
                        int i=0;
                        float tspeed=0;
                        long time1=Calendar.getInstance().getTimeInMillis();
                        while (receivelen <datalength){
                            size=mmInStream.read(buffer);
                            file.write(buffer, 0 ,size);
                            receivelen+=size;
                            i++;
                            if(i%10==0){
                                long time2=Calendar.getInstance().getTimeInMillis();
                                tspeed=receivelen/(time2-time1)*1000/1024;
                            }
                            downbl = (receivelen * 100) / datalength;
                            TransmitBean up = new TransmitBean();
                            up.setUppercent(String.valueOf(downbl));
                            up.setTspeed(String.valueOf(tspeed));
                            if(i==1){
                                up.setShowflag(true);
                            }else{
                                up.setShowflag(false);
                            }
                            Message msg = mHandler.obtainMessage();
                            msg.what = FILE_RECIVE_PERCENT;
                            msg.obj = up;
                            msg.sendToTarget();
                        }
                        Log.v("调试" , "接收完成,receivelen:"+receivelen);
                        file.flush();
                        file.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //发送成功读取到对象的消息，消息的obj参数为读取到的对象
                Message msg = mHandler.obtainMessage();
                msg.what = MESSAGE_READ_OBJECT;
                msg.obj = transmit_r;
                msg.sendToTarget();
            }
        }



        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }
}
