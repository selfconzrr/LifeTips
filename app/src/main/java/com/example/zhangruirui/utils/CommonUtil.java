package com.example.zhangruirui.utils;

import android.graphics.Color;

import java.util.Random;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：12/26/18
 * <p>
 * 一些常用的 api 的方法工具
 */
public class CommonUtil {

  /**
   * 随机颜色
   */
  public static int randomColor() {
    Random random = new Random();
    int red = random.nextInt(150) + 50; // 50-199
    int green = random.nextInt(150) + 50; // 50-199
    int blue = random.nextInt(150) + 50; // 50-199
    return Color.rgb(red, green, blue);
  }


}
