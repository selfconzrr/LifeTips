package com.example.zhangruirui.lifetips.passwordbook.util;

public class MathUtil {
  /**
   * 两点间的距离
   */
  public static double distance(double x1, double y1, double x2, double y2) {
    return Math.sqrt(Math.abs(x1 - x2) * Math.abs(x1 - x2)
        + Math.abs(y1 - y2) * Math.abs(y1 - y2));
  }

  /**
   * 计算点 a(x,y) 的角度
   */
  public static double pointTotoDegrees(double x, double y) {
    return Math.toDegrees(Math.atan2(x, y));
  }
}

