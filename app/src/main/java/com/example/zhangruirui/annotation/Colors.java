package com.example.zhangruirui.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：12/04/18
 * <p>
 * 自定义注解
 */
public class Colors {

  @IntDef({COLOR_RED, COLOR_GREEN, COLOR_BLUE})
  @Retention(RetentionPolicy.SOURCE)
  public @interface LightColor {
  }

  private static final int COLOR_RED = 0;
  private static final int COLOR_GREEN = 1;
  private static final int COLOR_BLUE = 2;
}
