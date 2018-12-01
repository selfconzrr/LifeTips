package com.example.zhangruirui.lifetips.leetcode.model;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/30/18
 * <p>
 * 剑指 Offer 试题的实体类 Problem
 */
public class Problem {

  private int id; // 题目编号
  private String title; // 题目名称
  private String acceptance; // 题目通过率
  private String hot_index; // 题目热度
  private String url; // 题目的网页链接
  private String knowledge_point; // 题目的考察知识点

  Problem(int id, String title, String acceptance, String hot_index, String url,
          String knowledge_point) {
    this.id = id;
    this.title = title;
    this.acceptance = acceptance;
    this.hot_index = hot_index;
    this.url = url;
    this.knowledge_point = knowledge_point;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAcceptance() {
    return acceptance;
  }

  public String getHot_index() {
    return hot_index;
  }

  public String getUrl() {
    return url;
  }

  public String getKnowledge_point() {
    return knowledge_point;
  }

  @Override
  public String toString() {

    return "id: " + id +
        "\ntitle: " + title +
        "\nacceptance: " + acceptance +
        "\nhot_index: " + hot_index +
        "\nurl: " + url +
        "\nknowledge_point: " + knowledge_point;
  }

}

