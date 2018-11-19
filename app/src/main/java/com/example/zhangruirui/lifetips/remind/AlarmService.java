package com.example.zhangruirui.lifetips.remind;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.util.Log;

import com.example.zhangruirui.lifetips.MainActivity;
import com.example.zhangruirui.lifetips.R;

import java.util.Timer;
import java.util.TimerTask;

public class AlarmService extends Service {
  static Timer mTimer = null;

  public void cleanAllNotification() {
    NotificationManager mn = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    if (mn != null) {
      mn.cancelAll();
    }
    if (mTimer != null) {
      mTimer.cancel();
      mTimer = null;
    }
  }

  @Override
  public IBinder onBind(Intent arg0) {
    return null;
  }

  @Override
  public void onCreate() {
    Log.e("addNotification", "===========create=======");
  }

  @Override
  public void onDestroy() {
    Log.e("addNotification", "===========destroy=======");
    super.onDestroy();
  }

  @Override
  public int onStartCommand(final Intent intent, int flags, int startId) {

    long period = 24 * 60 * 60 * 1000;
    int delay = intent.getIntExtra("delayTime", 0);
    if (null == mTimer) {
      mTimer = new Timer();
    }
    mTimer.schedule(new TimerTask() {

      @Override
      public void run() {
        NotificationManager mn = (NotificationManager) AlarmService.this
            .getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(
            AlarmService.this);

        Intent notificationIntent = new Intent(AlarmService.this,
            MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(
            AlarmService.this, 0, notificationIntent, 0);
        builder.setContentIntent(contentIntent);
        builder.setSmallIcon(R.drawable.quit);
        builder.setTicker(intent.getStringExtra("tickerText"));
        builder.setContentText(intent.getStringExtra("contentText"));
        builder.setContentTitle(intent.getStringExtra("contentTitle"));
        builder.setAutoCancel(true);
        builder.setVibrate(new long[]{0, 1000, 1000, 1000});
        // builder.setSound(Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI,
        // "5"));
        // builder.setSound(Uri.parse("file:///sdcard/xx/xx.mp3"));
        // builder.setDefaults(Notification.DEFAULT_ALL);
        // builder.addAction("", title, intent);

        Notification notification = builder.build();
        notification.ledARGB = Color.GREEN;
        notification.ledOnMS = 1000;
        notification.ledOffMS = 1000;
        notification.flags = Notification.FLAG_INSISTENT;
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        mn.notify((int) System.currentTimeMillis(), notification);
      }
    }, delay, period);
    return super.onStartCommand(intent, flags, startId);
  }
}
