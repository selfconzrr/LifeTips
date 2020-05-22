package com.example.zhangruirui.SomeUsefulJavaCode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormat {
  public static void main(String[] args) {
    int [] A = new int [] {3,3,6,5,-2,2,5,1,-9,4};
    System.out.println(canThreePartsEqualSum(A));
//    Calendar calendar = Calendar.getInstance();
//    // 2014-12-31
//    calendar.set(2014, Calendar.DECEMBER, 31);
//    Date strDate1 = calendar.getTime();
//    // 2015-01-01
//    calendar.set(2015, Calendar.JANUARY, 1);
//    Date strDate2 = calendar.getTime();
//    // 大写YYYY
//    SimpleDateFormat formatUpperCase = new SimpleDateFormat("YYYY/MM/dd");
//    System.out.println("2014-12-31 to YYYY/MM/dd: " + formatUpperCase.format(strDate1));
//    System.out.println("2015-01-01 to YYYY/MM/dd: " + formatUpperCase.format(strDate2));
//    // 小写YYYY
//    SimpleDateFormat formatLowerCase = new SimpleDateFormat("yyyy/MM/dd");
//    System.out.println("2014-12-31 to yyyy/MM/dd: " + formatLowerCase.format(strDate1));
//    System.out.println("2015-01-01 to yyyy/MM/dd: " + formatLowerCase.format(strDate2));
  }

  public static boolean canThreePartsEqualSum(int[] A) {
    if (A == null || A.length < 3) {
      return false;
    }
    int len = A.length, sum = 0, subSum = 0;
    int i = 0, j = 0, k = 0;
    int firstSum = 0, secondSum = 0, thirdSum = 0;
    for (int n : A) {
      sum += n;
    }
    subSum = sum / 3;
    for (i = 0; i < len - 2; i++) {
      firstSum += A[i];
      if (firstSum == subSum) {
        break;
      }
    }
    if (firstSum != subSum) {
      return false;
    }

    for (j = i + 1; j < len - 1; j++) {
      secondSum += A[j];
      if (secondSum == subSum) {
        break;
      }
    }

    if (secondSum != subSum) {
      return false;
    }

    for (k = j + 1; k < len; k++) {
      thirdSum += A[k];
    }

    if (thirdSum != subSum) {
      return false;
    }

    return true;
  }
}