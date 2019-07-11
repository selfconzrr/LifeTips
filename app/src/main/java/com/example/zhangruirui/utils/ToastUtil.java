package com.example.zhangruirui.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/05/18
 */
public class ToastUtil {

  private static Toast mToast = null;
  /**
   * 用于自定义 toast 内容展示，系统自带的 Toast.LENGTH_LONG 是3.5秒，Toast.LENGTH_SHORT 是2秒，
   * <p>
   * 使用 Toast 时，建议定义一个全局的 Toast 对象，这样可以避免连续显示 Toast 时不能取消上一次 Toast 消息的情况
   * (如果你有连续弹出 Toast 的情况，避免使用 Toast.makeText)。 见下方 showToastNew（）
   *
   * @param activity context
   * @param word     想要 toast 展示的文字
   */
  public static void showToast(@NonNull final Activity activity, final String word) {
    activity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        final Toast toast = Toast.makeText(activity, word, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.show();
      }
    });
  }

  public static void showToastNew(@NonNull final Activity activity, final String word, final int duration) {
    activity.runOnUiThread(new Runnable() {
      @SuppressLint("ShowToast")
      @Override
      public void run() {
        if (mToast == null) {
          mToast = Toast.makeText(activity, word, duration);
        } else {
          mToast.setText(word);
          mToast.setDuration(duration);
        }
        mToast.setGravity(Gravity.BOTTOM, 0, 50);
        mToast.show();
      }
    });
  }
}
