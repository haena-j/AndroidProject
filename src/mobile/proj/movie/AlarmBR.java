/* 작성일 : 2014년12월14일
 * 작성자 : 정혜윤
 * 클래스 설명 : BroadcastReceiver 사용 부분. 알람이 울릴 때 사용 되는 기능이 적혀 있음
 */package mobile.proj.movie;


import mobile.proj.join.R;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

public class AlarmBR extends BroadcastReceiver {

	public final static String TAG = "MyBroadcastReceiver";
	
	@SuppressLint("NewApi")
	@Override
	public void onReceive(Context context, Intent alarmIntent) {
		Log.i("test", "dd");
		Intent newIntent = new Intent(context, MovieChartActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(context, 0, newIntent, 0);
		
		RemoteViews notiView = new RemoteViews(context.getPackageName(), R.layout.noti_layout);//외부에서띄우는거기때문에 RemoteViews를 사용해서 만든다
		
		NotificationManager notiManager //NotificationManager를 통한 Notification 설정
			= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification myNoti = new NotificationCompat.Builder(context)
		.setTicker("영화개봉알림") 
		.setSmallIcon(R.drawable.alarmmark) //icon은 res에 저장된 이미지 이용
		.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.alarmmark))
		.setContentIntent(pIntent)
		.setContentInfo("1")
		.setAutoCancel(true)
		.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)
		.setVibrate(new long[] { 1000, 1000, 500, 500, 200, 200, 200, 200, 200 }) //바이브레이션 설정!
		.setContent(notiView)//내가만든view로 대체
		.build();
		notiManager.notify(100, myNoti);
	}

}
