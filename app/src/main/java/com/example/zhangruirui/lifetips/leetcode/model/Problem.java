package com.example.zhangruirui.lifetips.leetcode.model;

public class Problem {

  private int id;
  private String title;
  private String acceptance;
  private String hot_degree;
  private String url;
  private String knowledge_point;

  public Problem(int id, String title, String acceptance, String hot_degree, String url,
                 String knowledge_point) {
    this.id = id;
    this.title = title;
    this.acceptance = acceptance;
    this.hot_degree = hot_degree;
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

  public void setAcceptance(String acceptance) {
    this.acceptance = acceptance;
  }

  public String getHot_degree() {
    return hot_degree;
  }

  public void setHot_degree(String hot_degree) {
    this.hot_degree = hot_degree;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getKnowledge_point() {
    return knowledge_point;
  }

  public void setKnowledge_point(String knowledge_point) {
    this.knowledge_point = knowledge_point;
  }

  @Override
  public String toString() {

    return "id: " + id +
        "\ntitle: " + title +
        "\nacceptance: " + acceptance +
        "\nhot_degree: " + hot_degree +
        "\nurl: " + url +
        "\nknowledge_point: " + knowledge_point;
  }

}

