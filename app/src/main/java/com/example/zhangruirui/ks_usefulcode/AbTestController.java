package com.example.zhangruirui.ks_usefulcode;

import android.text.TextUtils;

public class AbTestController {
  /**
   * 本地 AB，根据加密后的设备 id 最后一位的奇偶性，区分是否进行相应的实验
   * true : 请求
   * false : 不请求
   */
  public static boolean enableDoSomething() {

    String deviceId = "your device id";
    String deviceIdMd5 = "md5 算法加密后的 device id";
    if (!TextUtils.isEmpty(deviceIdMd5)) {
      int num = Integer.parseInt(deviceIdMd5.substring(deviceIdMd5.length() - 1), 16);
      return num % 2 == 0;
    } else {
      return false;
    }
  }
}
