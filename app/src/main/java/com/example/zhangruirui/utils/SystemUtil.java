package com.example.zhangruirui.utils;

import android.os.Build;

public class SystemUtil {

  public static boolean isAtLeastOreo() {
    return aboveApiLevel(Build.VERSION_CODES.O);
  }

  public static boolean isAtLeastNougat() {
    return aboveApiLevel(Build.VERSION_CODES.N);
  }

  public static boolean aboveApiLevel(int sdkInt) {
    return getApiLevel() >= sdkInt;
  }

  public static int getApiLevel() {
    return Build.VERSION.SDK_INT;
  }
}
