package com.example.zhangruirui.lifetips.passwordbook.util;

public class RoundUtil {
  /**
   * 点在圆肉
   *
   * @param sx 圆心横坐标
   * @param sy 圆心纵坐标
   * @param r  圆的半径
   * @param x  点的横坐标
   * @param y  点的纵坐标
   */
  public static boolean checkInRound(float sx, float sy, float r, float x,
                                     float y) {
    return Math.sqrt((sx - x) * (sx - x) + (sy - y) * (sy - y)) < r;
  }
}

