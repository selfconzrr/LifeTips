package com.example.zhangruirui.utils;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.zhangruirui.lifetips.MainActivity;
import com.example.zhangruirui.lifetips.R;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：12/16/18
 * <p>
 * 应用程序的桌面小工具类
 * <p>
 * {@link AppWidgetProvider}
 * <p>
 * 官方文档：https://developer.android.com/reference/android/appwidget/AppWidgetProvider
 */
public class MyAppWidgetProvider extends AppWidgetProvider {

  public static final String TAG = "MyAppWidgetProvider";
  public static final String CLICK_ACTION = "com.example.zhangruirui.action.CLICK";

  // appwidget 上的操作都必须借助远程对象来操作
  private RemoteViews mRemoteViews;

  /**
   * 接收窗口小部件点击时发送的广播
   * <p>
   * AppWidgetProvider 中的几个回调方法：onEnabled,onDisabled,onDeleted,onUpdated 会自动被其 onReceive
   * 方法在合适的时间调用，确切来说是，当广播到来以后，AppWidgetProvider 会自动根据广播的 action 通过 onReceive
   * 方法来自动派发广播
   */
  @Override
  public void onReceive(Context context, Intent intent) {
    super.onReceive(context, intent);
    Log.e("zhangrr", "onReceive() called with: context = " + context + ", intent = " + intent
        .getAction());

    if (intent.getAction() != null && intent.getAction().equals(CLICK_ACTION)) {
      Toast.makeText(context, "clicked it", Toast.LENGTH_SHORT).show();
      mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.desktop_widget);
      AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

      ComponentName componentName = new ComponentName(context, MyAppWidgetProvider.class);

      appWidgetManager.updateAppWidget(componentName, mRemoteViews);

    }
  }

  /**
   * 每次窗口小部件被点击更新都调用一次该方法
   */
  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    super.onUpdate(context, appWidgetManager, appWidgetIds);
    Log.e("zhangrr", "onUpdate() called with: context = [" + context);
    // Intent intent = new Intent();
    // intent.setAction(CLICK_ACTION);
    // PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

    // 为 APPWidget 设置点击监听事件，点击启动主界面
    mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.desktop_widget);
    Intent mainIntent = new Intent(context, MainActivity.class);
    // PendingIntent 可以看成是一个特殊的 Intent，如果我们把 Intent 看成一封信，那么 PendingIntent
    // 就是一封被信封包裹起来的信。这封信在 remoteViews.setOnClickPendingIntent() 中被“邮寄”到了 appwidget，
    // 当 appwidget 中的按钮单击时他知道将这封信打开，并执行里面的内容。这样就避免了直接从 appwidget 中执行本地代码

    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);
    mRemoteViews.setOnClickPendingIntent(R.id.widget, pendingIntent);
    appWidgetManager.updateAppWidget(appWidgetIds, mRemoteViews);
  }

  /**
   * 每删除一次窗口小部件就调用一次
   */
  @Override
  public void onDeleted(Context context, int[] appWidgetIds) {
    super.onDeleted(context, appWidgetIds);
    Log.e("zhangrr", "onDeleted() called with: context = " + context);
  }

  /**
   * 当该窗口小部件第一次添加到桌面时调用该方法，可添加多次但只第一次调用
   */
  @Override
  public void onEnabled(Context context) {
    super.onEnabled(context);
    Log.e("zhangrr", "onEnabled() called with: context = [" + context);
  }

  /**
   * 当最后一个该窗口小部件删除时调用该方法，注意是最后一个
   */
  @Override
  public void onDisabled(Context context) {
    super.onDisabled(context);
    Log.e("zhangrr", "onDisabled() called with: context = [" + context);
  }
}
