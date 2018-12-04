package com.example.zhangruirui.utils;

/**
 * 当前类注释:重写系统日志管理类
 * 使用方法:还是和平时 Log.v(key,value) 这样使用，需要导入当前类，该类会打印比系统更多的日志信息，
 * 例如：类名称,当前运行的方法,行数,和日志信息
 *
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：12/04/18
 */

@SuppressWarnings("JavaDoc")
public class Log {
  private static boolean mIsShow = true;

  /**
   * 设置是否打开log日志开关
   *
   * @param pIsShow
   */
  public static void setShow(boolean pIsShow) {
    mIsShow = pIsShow;
  }

  /**
   * 根据tag打印相关v信息
   *
   * @param tag
   * @param msg
   */
  public static void v(String tag, String msg) {
    if (mIsShow) {
      StackTraceElement ste = new Throwable().getStackTrace()[1];
      String traceInfo = ste.getClassName() + "::";
      traceInfo += ste.getMethodName();
      traceInfo += "@" + ste.getLineNumber() + ">>>";
      android.util.Log.v(tag, traceInfo + msg);
    }
  }

  /**
   * 根据tag打印v信息,包括Throwable的信息
   * * @param tag
   *
   * @param msg
   * @param tr
   */
  public static void v(String tag, String msg, Throwable tr) {
    if (mIsShow) {
      android.util.Log.v(tag, msg, tr);
    }
  }


  /**
   * 根据tag打印输出debug信息
   *
   * @param tag
   * @param msg
   */
  public static void d(String tag, String msg) {
    if (mIsShow) {
      StackTraceElement ste = new Throwable().getStackTrace()[1];
      String traceInfo = ste.getClassName() + "::";
      traceInfo += ste.getMethodName();
      traceInfo += "@" + ste.getLineNumber() + ">>>";
      android.util.Log.d(tag, traceInfo + msg);
    }
  }

  /**
   * 根据tag打印输出debug信息 包括Throwable的信息
   * * @param tag
   *
   * @param msg
   * @param tr
   */
  public static void d(String tag, String msg, Throwable tr) {
    if (mIsShow) {
      android.util.Log.d(tag, msg, tr);
    }
  }

  /**
   * 根据tag打印输出info的信息
   * * @param tag
   *
   * @param msg
   */
  public static void i(String tag, String msg) {
    if (mIsShow) {
      StackTraceElement ste = new Throwable().getStackTrace()[1];
      String traceInfo = ste.getClassName() + "::";
      traceInfo += ste.getMethodName();
      traceInfo += "@" + ste.getLineNumber() + ">>>";
      android.util.Log.i(tag, traceInfo + msg);
    }
  }

  /**
   * 根据tag打印输出info信息 包括Throwable的信息
   *
   * @param tag
   * @param msg
   * @param tr
   */
  public static void i(String tag, String msg, Throwable tr) {
    if (mIsShow) {
      android.util.Log.i(tag, msg, tr);
    }
  }

  /**
   * 根据tag打印输出error信息
   *
   * @param tag
   * @param msg
   */
  public static void e(String tag, String msg) {
    if (mIsShow) {
      StackTraceElement ste = new Throwable().getStackTrace()[1];
      String traceInfo = ste.getClassName() + "::";
      traceInfo += ste.getMethodName();
      traceInfo += "@" + ste.getLineNumber() + ">>>";
      android.util.Log.e(tag, traceInfo + msg);
    }
  }

  /**
   * 根据tag打印输出的error信息 包括Throwable的信息
   *
   * @param tag
   * @param msg
   * @param tr
   */
  public static void e(String tag, String msg, Throwable tr) {
    if (mIsShow) {
      android.util.Log.e(tag, msg, tr);
    }
  }
}
