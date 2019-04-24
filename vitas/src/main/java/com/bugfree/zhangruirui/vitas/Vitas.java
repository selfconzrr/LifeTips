package com.bugfree.zhangruirui.vitas;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.bugfree.zhangruirui.vitas.repo.LogRepository;
import com.bugfree.zhangruirui.vitas.view.ShowLogActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Vitas {

  private static final String CHANNEL_ID_SHOW_LOG = "vitas";
  private static final int S_NOTIFICATION_ID = 123;

  private NotificationManager mManager;
  private Context mContext;
  private LogRepository mLogRepository;
  private boolean mEnable = false;
  private static boolean isInited = false;

  private Vitas() {
  }

  private static class ManagerHolder {
    private static Vitas SingletonInitManager = new Vitas();
  }

  public void init(Context context) {
    synchronized (Vitas.class) {
      if (!isInited) {
        mContext = context;
        mLogRepository = new LogRepository();
        isInited = true;
      }
    }
  }

  public static Vitas getInstance() {
    return ManagerHolder.SingletonInitManager;
  }

  private void showNotification() {
    if (!mEnable) {
      return;
    }
    Intent intent = new Intent(mContext, ShowLogActivity.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);

    // 第二个参数与 channelId 对应
    Notification.Builder builder = new Notification.Builder(mContext);
    builder.setContentTitle(mContext.getString(R.string.notification_title))
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentIntent(pendingIntent);
    mManager = (NotificationManager) mContext.getSystemService(
        NOTIFICATION_SERVICE);

    // 支持 Android 8.0 手机通知栏适配
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      builder.setChannelId(CHANNEL_ID_SHOW_LOG);

      // 创建通知渠道
      CharSequence name = "ShowLog";
      String description = "ShowNetworkLog";
      int importance = NotificationManager.IMPORTANCE_DEFAULT; // 重要性级别
      NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID_SHOW_LOG, name, importance);
      mChannel.setDescription(description); // 渠道描述
      mChannel.enableLights(false); // 是否显示通知指示灯
      mChannel.enableVibration(false); // 是否振动

      if (mManager != null) {
        mManager.createNotificationChannel(mChannel); // 创建通知渠道
      }
    }

    mManager.notify(S_NOTIFICATION_ID, builder.build());
  }

  private void clearNotification() {
    mManager.cancel(S_NOTIFICATION_ID);
  }

  public Context getContext() {
    return mContext;
  }

  public LogRepository getLogRepository() {
    return mLogRepository;
  }

  public void setEnable(boolean enable) {
    this.mEnable = enable;
    if (!enable) {
      clearNotification();
    } else {
      showNotification();
    }
  }

  public boolean isEnable() {
    return mEnable;
  }
}


