package com.example.zhangruirui.SomeUsefulJavaCode;

public class sqrt {

  public static void main(String[] args) {
    System.out.println("my sqrt(8) = " + sqrt(8));
    System.out.println("system sqrt(8) = " + Math.sqrt(8));
  }

  public static int sqrt(int x) {
    int low = 0;
    int high = x;
    while (low <= high) {
      long mid = (low + high) / 2; // 防止溢出
      if (mid * mid == x) {
          return (int) mid;
      } else if (mid * mid < x) {
          low = (int) (mid + 1);
      } else {
          high = (int) (mid - 1);
      }
    }
    return high;
  }

}
