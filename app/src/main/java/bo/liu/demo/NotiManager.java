package bo.liu.demo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;



import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class NotiManager {
	
	private volatile static NotiManager center = null;//7.3原来为=null
	private static NotificationManager manager;//7.3原来为=null
	private static final String TAG = "NotiManager";

	private static HashMap<String, NotificationCompat.Builder> builders;

	private static HashMap<String, Integer> lastPercentMaps;

   

    /**
	 * 通知栏中心调度
	 * @param context
	 * @return
	 */
	public static NotiManager getNotificationCenter(Context context) {
		if(center==null) {
			synchronized (NotiManager.class) {
				if (center==null) {
					center=new NotiManager();
					manager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
					builders=new HashMap<>();
					lastPercentMaps=new HashMap<>();
				}
			}
		}
		return center;
	}

	private boolean checkContainId(int id) {
		Iterator iterator=builders.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry= (Map.Entry) iterator.next();
			if (id==Integer.parseInt(entry.getKey().toString())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 普通通知栏
	 * @param context
	 * @param ticker
	 * @param title
     * @param content
     */
	public void createNormalNotification(Context context, String ticker, String title, String content) {
		NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
		builder.setTicker(ticker);
		builder.setContentTitle(title);
		builder.setContentText(content);
		builder.setContentIntent(PendingIntent.getBroadcast(context, (int) SystemClock.uptimeMillis(), new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));
		builder.setColor(context.getResources().getColor(R.color.colorAccent));
		builder.setSmallIcon(R.mipmap.ic_launcher);
		builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
		builder.setWhen(System.currentTimeMillis());
		builder.setAutoCancel(true);
		builder.setPriority(NotificationCompat.PRIORITY_MAX);
		builder.setDefaults(Notification.DEFAULT_LIGHTS);
		builder.setOngoing(true);
		manager.notify((int) (System.currentTimeMillis()/1000), builder.build());
	}
	
	/**
	 * 开启新通知
	 * @param context
	 * @param id
	 */
	public void createDownloadNotification(Context context, int id, String name) {

		if (checkContainId(id)) {
			return;
		}
		RemoteViews views=new RemoteViews(context.getPackageName(), R.layout.view_notification);
		views.setProgressBar(R.id.no_pb, 100, 0, false);
		views.setTextViewText(R.id.no_title, context.getResources().getString(R.string.downloading)+name);
		views.setInt(R.id.no_title, "setTextColor", isDarkNotificationTheme(context) == true?Color.WHITE:Color.BLACK);//7.4加
		views.setTextViewText(R.id.no_pb_num, "0%");
		views.setInt(R.id.no_pb_num, "setTextColor", isDarkNotificationTheme(context) == true?Color.WHITE:Color.BLACK);//7.4加
		NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
		builder.setSmallIcon(R.mipmap.ic_launcher);
		builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
		builder.setWhen(System.currentTimeMillis());
		builder.setPriority(NotificationCompat.PRIORITY_MAX);
		builder.setColor(context.getResources().getColor(R.color.colorAccent));
		builder.setContentTitle(context.getResources().getString(R.string.downloading)+name);
		builder.setOngoing(true);
		builder.setTicker(context.getResources().getString(R.string.downloading)+name);
		builder.setContentText(context.getResources().getString(R.string.downloading)+name);
		builder.setDefaults(Notification.DEFAULT_LIGHTS);
		builder.setContent(views);
		builder.setAutoCancel(false);
		builder.setContentIntent(PendingIntent.getBroadcast(context, (int) SystemClock.uptimeMillis(), new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));
		manager.notify(id, builder.build());
		builders.put(""+id, builder);
		lastPercentMaps.put(""+id, 0);
	}
	
	/**
	 * 更新相应id的通知栏
	 * @param context
	 * @param id
	 */
	public void updateDownloadNotification(Context context, int id, int persent, String name) {
		if (!checkContainId(id)) {
			return;
		}
		if (lastPercentMaps.containsKey(""+id)) {
			if (persent-10>lastPercentMaps.get(""+id).intValue()) {
				lastPercentMaps.put(""+id, persent);
			}
			else {
				return;
			}
		}
		else {
			return;
		}
		RemoteViews views=new RemoteViews(context.getPackageName(), R.layout.view_notification);
		views.setProgressBar(R.id.no_pb, 100, persent, false);
		views.setTextViewText(R.id.no_title, context.getResources().getString(R.string.downloading)+name);
		views.setInt(R.id.no_title, "setTextColor", isDarkNotificationTheme(context) == true?Color.WHITE:Color.BLACK);
		views.setTextViewText(R.id.no_pb_num, persent+"%");
		views.setInt(R.id.no_pb_num, "setTextColor", isDarkNotificationTheme(context) == true?Color.WHITE:Color.BLACK);
		NotificationCompat.Builder builder=builders.get(""+id);
		builder.setContent(views);
		manager.notify(id, builder.build());
        ;
	}
	
	/**
	 * 关闭通知
	 * @param id
	 */
	public void cancelNotification(int id) {
		manager.cancel(id);
		builders.remove(""+id);
		lastPercentMaps.remove(""+id);
	}

	/**
	 * 取消全部通知
	 */
	public void cancelAll() {
		manager.cancelAll();
		builders.clear();
		lastPercentMaps.clear();
	}
	
	/**
	 * 最后提示
	 * @param context
	 * @param id
	 */
	public void showEndNotification(Context context, int id) {
		if (!checkContainId(id)) {
			return;
		}
		NotificationCompat.Builder builder=builders.get(""+id);
		builder.setContentIntent(PendingIntent.getActivity(context, (int) SystemClock.uptimeMillis(), new Intent(context, NotificationActivity.class), PendingIntent.FLAG_UPDATE_CURRENT));
		manager.notify(id, builder.build());
		manager.cancel(id);
		builders.remove(""+id);
		lastPercentMaps.remove(""+id);
	}

	private static boolean isDarkNotificationTheme(Context context) {
		return !isSimilarColor(Color.BLACK, getNotificationColor(context));
	}

	/**
	 * 获取通知栏颜色
	 * @param context
	 * @return
	 */
	private static int getNotificationColor(Context context) {

		NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
		Notification notification=builder.build();
            int layoutId =  notification.contentView.getLayoutId();
			ViewGroup viewGroup= (ViewGroup) LayoutInflater.from(context).inflate(layoutId, null, false);
			if (viewGroup.findViewById(android.R.id.title)!=null) {
				return ((TextView) viewGroup.findViewById(android.R.id.title)).getCurrentTextColor();
			}


		return findColor(viewGroup);
	}

	private static int findColor(ViewGroup viewGroupSource) {
		int color=Color.TRANSPARENT;
		LinkedList<ViewGroup> viewGroups=new LinkedList<>();
		viewGroups.add(viewGroupSource);
		while (viewGroups.size()>0) {
			ViewGroup viewGroup1=viewGroups.getFirst();
			for (int i = 0; i < viewGroup1.getChildCount(); i++) {
				if (viewGroup1.getChildAt(i) instanceof ViewGroup) {
					viewGroups.add((ViewGroup) viewGroup1.getChildAt(i));
				}
				else if (viewGroup1.getChildAt(i) instanceof TextView) {
					if (((TextView) viewGroup1.getChildAt(i)).getCurrentTextColor()!=-1) {
						color=((TextView) viewGroup1.getChildAt(i)).getCurrentTextColor();
					}
				}
			}
			viewGroups.remove(viewGroup1);
		}
		return color;
	}

	private static boolean isSimilarColor(int baseColor, int color) {
		int simpleBaseColor=baseColor|0xff000000;
		int simpleColor=color|0xff000000;
		int baseRed=Color.red(simpleBaseColor)-Color.red(simpleColor);
		int baseGreen=Color.green(simpleBaseColor)-Color.green(simpleColor);
		int baseBlue=Color.blue(simpleBaseColor)-Color.blue(simpleColor);
		double value=Math.sqrt(baseRed*baseRed+baseGreen*baseGreen+baseBlue*baseBlue);
		if (value<180.0) {
			return true;
		}
		return false;
	}
}