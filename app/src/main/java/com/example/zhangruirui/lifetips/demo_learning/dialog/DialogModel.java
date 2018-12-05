package com.example.zhangruirui.lifetips.demo_learning.dialog;

import java.util.List;

public class DialogModel {

  private int type;
  private String title;
  private String desc;
  private String picture;
  private List<BtnsBean> btns;

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public List<BtnsBean> getBtns() {
    return btns;
  }

  public void setBtns(List<BtnsBean> btns) {
    this.btns = btns;
  }

  public static class BtnsBean {

    private int type;
    private String text;
    private UrlBean url;

    public int getType() {
      return type;
    }

    public void setType(int type) {
      this.type = type;
    }

    public String getText() {
      return text;
    }

    public void setText(String text) {
      this.text = text;
    }

    public UrlBean getUrl() {
      return url;
    }

    public void setUrl(UrlBean url) {
      this.url = url;
    }

    public static class UrlBean {

      private String type;
      private String value;

      public String getType() {
        return type;
      }

      public void setType(String type) {
        this.type = type;
      }

      public String getValue() {
        return value;
      }

      public void setValue(String value) {
        this.value = value;
      }
    }
  }
}
