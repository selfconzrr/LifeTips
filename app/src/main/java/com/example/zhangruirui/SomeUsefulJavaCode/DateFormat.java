package com.example.zhangruirui.SomeUsefulJavaCode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormat {
  public static void main(String[] args) {
    Calendar calendar = Calendar.getInstance();
    // 2014-12-31
    calendar.set(2014, Calendar.DECEMBER, 31);
    Date strDate1 = calendar.getTime();
    // 2015-01-01
    calendar.set(2015, Calendar.JANUARY, 1);
    Date strDate2 = calendar.getTime();
    // 大写YYYY
    SimpleDateFormat formatUpperCase = new SimpleDateFormat("YYYY/MM/dd");
    System.out.println("2014-12-31 to YYYY/MM/dd: " + formatUpperCase.format(strDate1));
    System.out.println("2015-01-01 to YYYY/MM/dd: " + formatUpperCase.format(strDate2));
    // 小写YYYY
    SimpleDateFormat formatLowerCase = new SimpleDateFormat("yyyy/MM/dd");
    System.out.println("2014-12-31 to yyyy/MM/dd: " + formatLowerCase.format(strDate1));
    System.out.println("2015-01-01 to yyyy/MM/dd: " + formatLowerCase.format(strDate2));
  }
}