package com.example.zhangruirui.lifetips.notes;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/18/18
 */
public class Notepad {
  public String content;
  public String data;
  public String id;
  public String title;

  public String getContent() {
    return this.content;
  }

  public String getTitle() {
    return this.title;
  }

  public String getData() {
    return this.data;
  }

  public String getId() {
    return this.id;
  }

  public void setContent(String paramString) {
    this.content = paramString;
  }

  public void setTitle(String paramString) {
    this.title = paramString;
  }

  public void setData(String paramString) {
    this.data = paramString;
  }

  public void setId(String paramString) {
    this.id = paramString;
  }
}
