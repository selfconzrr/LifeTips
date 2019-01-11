package com.example.zhangruirui.lifetips.passwordbook.util;

/**
 * 密码内容操作
 */
public class StringUtil {
  /**
   * 是否不为空
   */
  public static boolean isNotEmpty(String s) {
    return s != null && !"".equals(s.trim());
  }

  /**
   * 是否为空
   */
  public static boolean isEmpty(String s) {
    return s == null || "".equals(s.trim());
  }

  /**
   * 通过{n},格式化.
   */
  public static String format(String src, Object... objects) {
    int k = 0;
    for (Object obj : objects) {
      src = src.replace("{" + k + "}", obj.toString());
      k++;
    }
    return src;
  }
}

