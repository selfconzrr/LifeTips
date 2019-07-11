package com.example.zhangruirui.lifetips.notes;

import android.util.Log;

import java.util.Calendar;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/18/18
 */
public class MyDate {
  public String getDate() {
    Calendar localCalendar = Calendar.getInstance();
    Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = 1 + c.get(Calendar.MONTH);
    int date = c.get(Calendar.DATE);
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);
    int second = c.get(Calendar.SECOND);
    Log.e("test", year + " " + month + " " + date + " " + hour + " "
        + minute + " " + second);
    int i = localCalendar.get(1);
    int j = 1 + localCalendar.get(2);
    int k = localCalendar.get(5);
    int l = localCalendar.get(11);
    int i1 = localCalendar.get(12);
    int i2 = localCalendar.get(10);
    Log.e("test", i + " " + j + " " + k + " " + l + " " + i1 + " " + i2);
    if (l >= 13) {
      if (i2 == 0) {
          i2 = 12;
      }
      if (i1 < 10) {
          return i + "-" + j + "-" + k + "           " + "下午" + " " + i2
              + ":" + "0" + i1;
      }
      return i + "-" + j + "-" + k + "           " + "下午" + " " + i2
          + ":" + i1;
    }
    if (i2 == 0) {
        i2 = 12;
    }
    if (i1 < 10) {
        return i + "-" + j + "-" + k + "           " + "上午" + " " + i2
            + ":" + "0" + i1;
    }
    return i + "-" + j + "-" + k + "           " + "上午" + " " + i2 + ":"
        + i1;
  }
}
