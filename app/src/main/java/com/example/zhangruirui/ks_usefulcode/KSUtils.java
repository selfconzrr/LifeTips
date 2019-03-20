package com.example.zhangruirui.ks_usefulcode;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.Window;

import java.util.Calendar;
import java.util.Date;

public class KSUtils {

  private static final Handler UI_HANDLER = new Handler(Looper.getMainLooper());

  public static void runOnUiThreadDelay(Runnable action, long delay) {
    UI_HANDLER.postDelayed(action, delay);
  }

  /**
   * Check whether activity is running or not
   *
   * @return true if activity is running, otherwise false
   */
  public static boolean isActivityRunning(@Nullable Activity activity) {
    return activity != null && !activity.isFinishing();
  }

  /**
   * 检查当前的 {@link Fragment} 是否正在运行. 运行的条件为
   * 1、fragment 的 host 存在
   * 2、fragment 所在的 activity 正在运行
   */
  public static boolean isFragmentRunning(@Nullable Fragment fragment) {
    return fragment != null && fragment.getHost() != null && isActivityRunning(fragment
        .getActivity());
  }

  /**
   * 判断 {@link Activity} 是否 Attach to ViewRootImpl
   *
   * @param activity
   * @return
   */
  public static boolean isAttachedToWindowManager(Activity activity) {
    if (activity == null || activity.isFinishing()) {
      return false;
    }
    Window window = activity.getWindow();
    if (window == null) {
      return false;
    }
    View decorView = window.getDecorView();
    return decorView != null && decorView.getParent() != null;
  }

  /**
   * 检查当前的 {@link Dialog} 是否 Attach to ViewRootImpl
   */
  public static boolean isAttachedToWindowManager(Dialog dialog) {
    if (dialog == null) {
      return false;
    }
    Window window = dialog.getWindow();
    if (window == null) {
      return false;
    }
    View decorView = window.getDecorView();
    return decorView != null && decorView.getParent() != null;
  }

  public static long now() {

    return System.currentTimeMillis();
  }

  public static long since(long begin) {
    return System.currentTimeMillis() - begin;
  }

  /**
   * 获取 view 的位置
   *
   * @param anchorView
   */
  public void getViewPosition(View anchorView) {
    final int[] pos = new int[2];
    anchorView.getLocationOnScreen(pos);
    int[] res = new int[2];
    res[0] = pos[0] + anchorView.getWidth() / 2;
    res[1] = pos[1] + anchorView.getHeight();
    Log.e("zhangrr", "showTips() called with: x = [" + res[0] + " y = " + res[1]);
  }

  /**
   * 计算用户的年龄
   *
   * @param birthDay
   * @return
   */
  private int getUserAge(Date birthDay) {
    Calendar cal = Calendar.getInstance();
    int yearNow = cal.get(Calendar.YEAR);
    int monthNow = cal.get(Calendar.MONTH) + 1;
    int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
    cal.setTime(birthDay);

    int yearBirth = cal.get(Calendar.YEAR);
    int monthBirth = cal.get(Calendar.MONTH) + 1;
    int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

    int age = yearNow - yearBirth;

    if (monthNow <= monthBirth) {
      if (monthNow == monthBirth) {
        if (dayOfMonthNow < dayOfMonthBirth) age--;
      } else {
        age--;
      }
    }
    return age;
  }

  public static void runOnUiThread(Runnable action) {
    if (Looper.getMainLooper() == Looper.myLooper()) {
      action.run();
    } else {
      UI_HANDLER.post(action);
    }
  }


}
