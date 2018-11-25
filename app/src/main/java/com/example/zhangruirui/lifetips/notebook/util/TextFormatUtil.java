package com.example.zhangruirui.lifetips.notebook.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/24/18
 * <p>
 * 格式化字符串的工具类
 */

public class TextFormatUtil {
  private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale
      .CHINA);

  public static String formatDate(Date date) {
    return dateFormat.format(date);
  }

  public static Date parseText(String text) throws ParseException {
    return dateFormat.parse(text);
  }

  public static String getNoteSummary(String content) {
    if (content.length() > 10) {
      return content.substring(0, 10) + "...";
    }
    return content;
  }

}
