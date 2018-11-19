package com.example.zhangruirui.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/12/18
 */

public class ActivityCollector {

  private static List<Activity> mActivities = new ArrayList<>();

  public static void addActivity(Activity activity) {
    mActivities.add(activity);
  }

  public static void removeActivity(Activity activity) {
    mActivities.remove(activity);
  }

  public static void finishAll() {
    for (Activity activity : mActivities) {
      if (!activity.isFinishing()) {
        activity.finish();
      }
    }
  }
}
