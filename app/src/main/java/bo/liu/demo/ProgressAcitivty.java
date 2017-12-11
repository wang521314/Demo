package bo.liu.demo;

import android.app.Notification;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;



public class ProgressAcitivty extends BaseActivity implements OnClickListener {
	private Button btn_show_progress;
	private Button btn_show_un_progress;
	private Button btn_show_custom_progress;
	/** Notification��ID */
	int notifyId = 102;
	/** Notification�Ľ�������ֵ */
	int progress = 0;
	//Builder mBuilder;
	Button btn_download_start;
	Button btn_download_pause;
	Button btn_download_cancel;
	/** �����߳��Ƿ���ͣ */
	public boolean isPause = false;
	/** ֪ͨ�����Ƿ����Զ���� */
	public boolean isCustom = false;
	DownloadThread downloadThread;
	/** true:Ϊ��ȷ����ʽ��   false:ȷ����ʽ*/
	public Boolean indeterminate = false;
	 Builder mBuilder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progress);
		initView();
		initNotify();
	}

	private void initView() {
		btn_show_progress = (Button) findViewById(R.id.btn_show_progress);
		btn_show_un_progress = (Button) findViewById(R.id.btn_show_un_progress);
		btn_show_custom_progress = (Button) findViewById(R.id.btn_show_custom_progress);
		btn_download_start = (Button) findViewById(R.id.btn_download_start);
		btn_download_pause = (Button) findViewById(R.id.btn_download_pause);
		btn_download_cancel = (Button) findViewById(R.id.btn_download_cancel);
		btn_show_progress.setOnClickListener(this);
		btn_show_un_progress.setOnClickListener(this);
		btn_show_custom_progress.setOnClickListener(this);
		btn_download_start.setOnClickListener(this);
		btn_download_pause.setOnClickListener(this);
		btn_download_cancel.setOnClickListener(this);
	}

	/** ��ʼ��֪ͨ�� */
	private void initNotify() {
		mBuilder = new Builder(this);
		mBuilder.setWhen(System.currentTimeMillis())// ֪ͨ������ʱ�䣬����֪ͨ��Ϣ����ʾ
				.setContentIntent(getDefalutIntent(0))
				// .setNumber(number)//��ʾ����
				.setPriority(Notification.PRIORITY_DEFAULT)// ���ø�֪ͨ���ȼ�
				// .setAutoCancel(true)//���������־���û��������Ϳ�����֪ͨ���Զ�ȡ��
				.setOngoing(false)// ture��������Ϊһ�����ڽ��е�֪ͨ������ͨ����������ʾһ����̨����,�û���������(�粥������)����ĳ�ַ�ʽ���ڵȴ�,���ռ���豸(��һ���ļ�����,ͬ������,������������)
				.setDefaults(Notification.DEFAULT_VIBRATE)// ��֪ͨ������������ƺ���Ч������򵥡���һ�µķ�ʽ��ʹ�õ�ǰ���û�Ĭ�����ã�ʹ��defaults���ԣ�������ϣ�
				// Notification.DEFAULT_ALL Notification.DEFAULT_SOUND ������� //
				// requires VIBRATE permission
				.setSmallIcon(R.mipmap.icon);
	}

	/** ��ʾ��������֪ͨ�� */
	public void showProgressNotify() {
		mBuilder.setContentTitle("�ȴ�����")
				.setContentText("����:")
				.setTicker("��ʼ����");// ֪ͨ�״γ�����֪ͨ��������������Ч����
		if(indeterminate){
			//��ȷ�����ȵ�
			mBuilder.setProgress(0, 0, true);
		}else{
			//ȷ�����ȵ�
			mBuilder.setProgress(100, progress, false); // �����������ʾ������  ����Ϊtrue���ǲ�ȷ�������ֽ�����Ч��
		}
		mNotificationManager.notify(notifyId, mBuilder.build());
	}
	
	/** ��ʾ�Զ���Ĵ�������֪ͨ�� */
	private void showCustomProgressNotify(String status) {
		RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.view_custom_progress);
		mRemoteViews.setImageViewResource(R.id.custom_progress_icon, R.mipmap.icon);
		mRemoteViews.setTextViewText(R.id.tv_custom_progress_title, "����ͷ��");
		mRemoteViews.setTextViewText(R.id.tv_custom_progress_status, status);
		if(progress >= 100 || downloadThread == null){
			mRemoteViews.setProgressBar(R.id.custom_progressbar, 0, 0, false);
			mRemoteViews.setViewVisibility(R.id.custom_progressbar, View.GONE);
		}else{
			mRemoteViews.setProgressBar(R.id.custom_progressbar, 100, progress, false);
			mRemoteViews.setViewVisibility(R.id.custom_progressbar, View.VISIBLE);
		}
		mBuilder.setContent(mRemoteViews)
				.setContentIntent(getDefalutIntent(0))
				.setTicker("ͷ������")
				.setContentText("今日头条")
				.setDefaults(Notification.DEFAULT_LIGHTS);
//		Notification nitify = mBuilder.build();
//		nitify.contentView = mRemoteViews;
		NotificationCompat.Builder builder=mBuilder.setNumber(0);
		builder.setContent(mRemoteViews);
		mNotificationManager.notify(notifyId,builder.build() );
	}
	
	/** ��ʼ���� */
	public void startDownloadNotify() {
		isPause = false;
		if (downloadThread != null && downloadThread.isAlive()) {
//			downloadThread.start();
		}else{
			downloadThread = new DownloadThread();
			downloadThread.start();
		}
	}

	/** ��ͣ���� */
	public void pauseDownloadNotify() {
		isPause = true;
		if(!isCustom){
			mBuilder.setContentTitle("����ͣ");
			setNotify(progress);
		}else{
           // NotiManager.getNotificationCenter(getApplicationContext()).createDownloadNotification(getApplicationContext(),0,"mm");
			showCustomProgressNotify("����ͣ");
		}
	}

	/** ȡ������ */
	public void stopDownloadNotify() {
		if (downloadThread != null) {
			downloadThread.interrupt();
		}
		downloadThread = null;
		if(!isCustom){
			mBuilder.setContentTitle("������ȡ��").setProgress(0, 0, false);
			mNotificationManager.notify(notifyId, mBuilder.build());
		}else{
            //NotiManager.getNotificationCenter(getApplicationContext()).createDownloadNotification(getApplicationContext(),0,"mm");
			showCustomProgressNotify("������ȡ��");
		}
	}

	/** �������ؽ��� */
	public void setNotify(int progress) {
		mBuilder.setProgress(100, progress, false); // �����������ʾ������
		mNotificationManager.notify(notifyId, mBuilder.build());
	}

	/**
	 * �����߳�
	 */
	class DownloadThread extends Thread {

		@Override
		public void run() {
			int now_progress = 0;
			// Do the "lengthy" operation 20 times
			while (now_progress <= 100) {
				// Sets the progress indicator to a max value, the
				// current completion percentage, and "determinate"
				if(downloadThread == null){
					break;
				}
				if (!isPause) {
					progress = now_progress;
					if(!isCustom){
						mBuilder.setContentTitle("������");
						if(!indeterminate){
							setNotify(progress);
						}
					}else{
                        //NotiManager.getNotificationCenter(getApplicationContext()).createDownloadNotification(getApplicationContext(),0,"mm");
						showCustomProgressNotify("������");
					}
					now_progress += 10;
				}
				try {
					// Sleep for 1 seconds
					Thread.sleep(1 * 1000);
				} catch (InterruptedException e) {
				}
			}
			// When the loop is finished, updates the notification
			if(downloadThread != null){
				if(!isCustom){
					mBuilder.setContentText("�������")
					// Removes the progress bar
					.setProgress(0, 0, false);
					mNotificationManager.notify(notifyId, mBuilder.build());
				}else{
                   // NotiManager.getNotificationCenter(getApplicationContext()).createDownloadNotification(getApplicationContext(),0,"mm");
					showCustomProgressNotify("�������");
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_show_progress:
			downloadThread = null;
			isCustom = false;
			indeterminate = false;
			showProgressNotify();
			break;
		case R.id.btn_show_un_progress:
			downloadThread = null;
			isCustom = false;
			indeterminate = true;
			showProgressNotify();
			break;
		case R.id.btn_show_custom_progress:
			downloadThread = null;
			isCustom = true;
			indeterminate = false;
           // NotiManager.getNotificationCenter(getApplicationContext()).createDownloadNotification(getApplicationContext(),0,"mm");
			showCustomProgressNotify("�ȴ�����..");
			break;
		case R.id.btn_download_start:
			startDownloadNotify();
			break;
		case R.id.btn_download_pause:
			pauseDownloadNotify();
			break;
		case R.id.btn_download_cancel:
			stopDownloadNotify();
			break;
		default:
			break;
		}
	}
}
