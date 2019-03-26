package com.example.zhangruirui.lifetips.demo_learning.coordinatorlayout;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * 封装了一些方法，可以根据用户指定的类型显示不同背景颜色。
 *
 * 向用户传递更多暗示信息，比如 snackbar 背景显示成红色表明这是一个警告提示
 */
public class ColoredSnackBar {

  private static final int red = 0xfff44336;
  private static final int green = 0xff4caf50;
  private static final int blue = 0xff2195f3;
  private static final int orange = 0xffffc107;

  private static View getSnackBarLayout(Snackbar snackbar) {
    if (snackbar != null) {
      return snackbar.getView();
    }
    return null;
  }

  private static Snackbar colorSnackBar(Snackbar snackbar, int colorId) {
    View snackBarView = getSnackBarLayout(snackbar);
    if (snackBarView != null) {
      snackBarView.setBackgroundColor(colorId);
    }

    return snackbar;
  }

  public static Snackbar info(Snackbar snackbar) {
    return colorSnackBar(snackbar, blue);
  }

  public static Snackbar warning(Snackbar snackbar) {
    return colorSnackBar(snackbar, orange);
  }

  public static Snackbar alert(Snackbar snackbar) {
    return colorSnackBar(snackbar, red);
  }

  public static Snackbar confirm(Snackbar snackbar) {
    return colorSnackBar(snackbar, green);
  }
}