package bo.liu.demo;




import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

public class CustomActivity extends BaseActivity implements OnClickListener{
	/** TAG */
	private final static String TAG = "CustomActivity";
	/** ��ť����ʾ�Զ���֪ͨ */
	private Button btn_show_custom;
	/** ��ť����ʾ�Զ������ť��֪ͨ */
	private Button btn_show_custom_button;
	/** Notification ��ID */
	int notifyId = 101;
	/** NotificationCompat ������*/
	Builder mBuilder;
	/** �Ƿ��ڲ���*/
	public boolean isPlay = false;
	/** ֪ͨ����ť�㲥 */
	public ButtonBroadcastReceiver bReceiver;
	/** ֪ͨ����ť����¼���Ӧ��ACTION */
	public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom);
		initView();
		initButtonReceiver();
	}

	private void initView() {
		btn_show_custom = (Button) findViewById(R.id.btn_show_custom);
		btn_show_custom.setOnClickListener(this);
		btn_show_custom_button = (Button) findViewById(R.id.btn_show_custom_button);
		btn_show_custom_button.setOnClickListener(this);
	}

	public void shwoNotify(){
		//���趨RemoteViews
		RemoteViews view_custom = new RemoteViews(getPackageName(), R.layout.view_custom);
		//���ö�ӦIMAGEVIEW��ID����ԴͼƬ
		view_custom.setImageViewResource(R.id.custom_icon, R.mipmap.icon);
//		view_custom.setInt(R.id.custom_icon,"setBackgroundResource",R.drawable.icon);
		view_custom.setTextViewText(R.id.tv_custom_title, "����ͷ��");
		view_custom.setTextViewText(R.id.tv_custom_content, "������ʿ�ٷ���������Ѿ��������˧���-�ܿ�ѷ��������������Ľ����");
//		view_custom.setTextViewText(R.id.tv_custom_time, String.valueOf(System.currentTimeMillis()));
		//������ʾ
//		view_custom.setViewVisibility(R.id.tv_custom_time, View.VISIBLE);
//		view_custom.setLong(R.id.tv_custom_time,"setTime", System.currentTimeMillis());//��֪��Ϊɶ�ᱨ�����ῴ����־
		//����number
//		NumberFormat num = NumberFormat.getIntegerInstance();
//		view_custom.setTextViewText(R.id.tv_custom_num, num.format(this.number));
		mBuilder = new Builder(this);
		mBuilder.setContent(view_custom)
				.setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
				.setWhen(System.currentTimeMillis())// ֪ͨ������ʱ�䣬����֪ͨ��Ϣ����ʾ
				.setTicker("������Ѷ")
				.setPriority(Notification.PRIORITY_DEFAULT)// ���ø�֪ͨ���ȼ�
				.setOngoing(false)//�������ڽ��е�   trueΪ���ڽ���  Ч����.flagһ��
				.setSmallIcon(R.mipmap.icon);
//		mNotificationManager.notify(notifyId, mBuilder.build());
		Notification notify = mBuilder.build();
		notify.contentView = view_custom;
//		Notification notify = new Notification();
//		notify.icon = R.drawable.icon;
//		notify.contentView = view_custom;
//		notify.contentIntent = getDefalutIntent(Notification.FLAG_AUTO_CANCEL);
		mNotificationManager.notify(notifyId, notify);
	}
	
	/**
	 * ����ť��֪ͨ��
	 */
	public void showButtonNotify(){
		Builder mBuilder = new Builder(this);
		RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.view_custom_button);
		mRemoteViews.setImageViewResource(R.id.custom_song_icon, R.mipmap.sing_icon);
		//API3.0 ���ϵ�ʱ����ʾ��ť��������ʧ
		mRemoteViews.setTextViewText(R.id.tv_custom_song_singer, "�ܽ���");
		mRemoteViews.setTextViewText(R.id.tv_custom_song_name, "������");
		//����汾�ŵ��ڣ�3��0������ô����ʾ��ť
		if(BaseTools.getSystemVersion() <= 9){
			mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.GONE);
		}else{
			mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.VISIBLE);
			//
			if(isPlay){
				mRemoteViews.setImageViewResource(R.id.btn_custom_play, R.mipmap.btn_pause);
			}else{
				mRemoteViews.setImageViewResource(R.id.btn_custom_play, R.mipmap.btn_play);
			}
		}

		//������¼�����

		Intent buttonIntent = new Intent(ACTION_BUTTON);
		/* ��һ�װ�ť */
		buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PREV_ID);
		//������˹㲥������INTENT�ı�����getBroadcast����
		PendingIntent intent_prev = PendingIntent.getBroadcast(this, 1, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_prev, intent_prev);
		/* ����/��ͣ  ��ť */
		buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PALY_ID);
		PendingIntent intent_paly = PendingIntent.getBroadcast(this, 2, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_play, intent_paly);
		/* ��һ�� ��ť  */
		buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_NEXT_ID);
		PendingIntent intent_next = PendingIntent.getBroadcast(this, 3, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_next, intent_next);
		
		mBuilder.setContent(mRemoteViews)
				.setContentIntent(getDefalutIntent(Notification.FLAG_ONGOING_EVENT))
				.setWhen(System.currentTimeMillis())// ֪ͨ������ʱ�䣬����֪ͨ��Ϣ����ʾ
				.setTicker("���ڲ���")
				.setPriority(Notification.PRIORITY_DEFAULT)// ���ø�֪ͨ���ȼ�
				.setOngoing(true)
				.setSmallIcon(R.mipmap.sing_icon);
		Notification notify = mBuilder.build();
		notify.flags = Notification.FLAG_ONGOING_EVENT;
		//�ᱨ�������ҽ��˼·
//		notify.contentView = mRemoteViews;
//		notify.contentIntent = PendingIntent.getActivity(this, 0, new Intent(), 0);
		mNotificationManager.notify(200, notify);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_show_custom:
			shwoNotify();
			break;
		case R.id.btn_show_custom_button:
			showButtonNotify();
			break;
		default:
			break;
		}
	}
	
	/** ����ť��֪ͨ������㲥���� */
	public void initButtonReceiver(){
		bReceiver = new ButtonBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION_BUTTON);
		registerReceiver(bReceiver, intentFilter);
	}
	
	public final static String INTENT_BUTTONID_TAG = "ButtonId";
	/** ��һ�� ��ť��� ID */
	public final static int BUTTON_PREV_ID = 1;
	/** ����/��ͣ ��ť��� ID */
	public final static int BUTTON_PALY_ID = 2;
	/** ��һ�� ��ť��� ID */
	public final static int BUTTON_NEXT_ID = 3;
	/**
	 *	 �㲥������ť���ʱ�� 
	 */
	public class ButtonBroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if(action.equals(ACTION_BUTTON)){
				//ͨ�����ݹ�����ID�жϰ�ť������Ի���ͨ��getResultCode()�����Ӧ����¼�
				int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
				switch (buttonId) {
				case BUTTON_PREV_ID:
					Log.d(TAG , "��һ��");
					Toast.makeText(getApplicationContext(), "��һ��", Toast.LENGTH_SHORT).show();
					break;
				case BUTTON_PALY_ID:
					String play_status = "";
					isPlay = !isPlay;
					if(isPlay){
						play_status = "��ʼ����";
					}else{
						play_status = "����ͣ";
					}
					showButtonNotify();
					Log.d(TAG , play_status);
					Toast.makeText(getApplicationContext(), play_status, Toast.LENGTH_SHORT).show();
					break;
				case BUTTON_NEXT_ID:
					Log.d(TAG , "��һ��");
					Toast.makeText(getApplicationContext(), "��һ��", Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(bReceiver != null){
			unregisterReceiver(bReceiver);
		}
		super.onDestroy();
	}
}
