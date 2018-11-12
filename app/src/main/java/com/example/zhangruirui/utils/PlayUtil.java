package com.example.zhangruirui.utils;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/12/18
 */

public class PlayUtil {
  public static String parseInt(int n) {
    int m = n / 60;
    int s = n % 60;
    StringBuilder builder = new StringBuilder();
    builder.append(m).append(":");
    if (s < 10) {
      builder.append("0");
    }
    builder.append(s);
    return builder.toString();
  }
}
