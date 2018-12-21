package com.example.zhangruirui.SomeUsefulJavaCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2018欧冠十六强对阵抽签情况及概率预测
 */
public class lcy {

  private static List<Character> groupB = new ArrayList<>();
  public static List<List<String>> result = new ArrayList<>();

  private static Map<String, String> map = new HashMap<>();
  public static String res = "";

  private static void swap(String[] str, int i, int j) {
    String temp;
    temp = str[i];
    str[i] = str[j];
    str[j] = temp;
  }

  private static void arrange(String[] str, int st, int len) {
    if (st == len - 1) {
      List<String> temp = new ArrayList<>();
      for (int i = 0; i < len; i++) {
        res = str[i] + "" + groupB.get(i) + "  ";
        temp.add(res);
        char c = str[i].charAt(0);
        // 抽签规则一：两支球队小组赛必须不同组
        // 抽签规则二：两支球队必须是不同国家的
        if (Character.toLowerCase(c) == groupB.get(i) ||
            map.get(str[i]).equals(map.get(groupB.get(i).toString()))) {
          temp.clear();
          break;
        }
//        System.out.print(res);
      }
      if (temp.size() != 0) {
        Collections.sort(temp);
        result.add(temp);
      }
    } else {
      for (int i = st; i < len; i++) {
        swap(str, st, i);
        arrange(str, st + 1, len);
        swap(str, st, i);
      }
    }

  }

  public static void main(String[] args) {

    /**
     * 此处假设大写字母 依次 为各组的第一，小写字母 依次 为各组的第二
     *
     * 可直接换成对应的队名
     */
    String str[] = {"A", "B", "C", "D", "E", "F", "G", "H"};
    groupB.add('a');
    groupB.add('b');
    groupB.add('c');
    groupB.add('d');
    groupB.add('e');
    groupB.add('f');
    groupB.add('g');
    groupB.add('h');

    map.put("A", "German");
    map.put("E", "German");
    map.put("d", "German");

    map.put("B", "Spanish");
    map.put("G", "Spanish");
    map.put("a", "Spanish");

    map.put("H", "Italy");
    map.put("g", "Italy");

    map.put("C", "France");
    map.put("f", "France");

    map.put("h", "English");
    map.put("F", "English");
    map.put("c", "English");
    map.put("b", "English");

    map.put("D", "Potrum");

    map.put("e", "holand");

    arrange(str, 0, str.length);

    int count = 0;
//    Collections.sort(result);
    String com = "";
    for (List<String> res : result) {
      for (String ss : res) {
        System.out.print(ss);
        com += ss;
      }
      if (com.contains("Ab")) {
        count++;
      }
      System.out.println();
      com = "";
    }
    System.out.println(count + "  " + result.size());

    System.out.println("热刺Vs多特蒙德 概率 = " + 1.0 * count / result.size());
  }
}
