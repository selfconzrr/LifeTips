package com.example.zhangruirui.utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfcon
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/05/18
 */
public class ToastUtil {

  /**
   * 用于自定义 toast 内容展示
   *
   * @param activity context
   * @param word     想要 toast 展示的文字
   */
  public static void showToast(@NonNull final Activity activity, final String word) {
    activity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        final Toast toast = Toast.makeText(activity, word, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.show();
      }
    });
  }
}
