package com.example.zhangruirui;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Teacher {

  @Id
  private long teaId;

  private String name;

  @Generated(hash = 57607948)
  public Teacher(long teaId, String name) {
      this.teaId = teaId;
      this.name = name;
  }

  @Generated(hash = 1630413260)
  public Teacher() {
  }

  public long getTeaId() {
      return this.teaId;
  }

  public void setTeaId(long teaId) {
      this.teaId = teaId;
  }

  public String getName() {
      return this.name;
  }

  public void setName(String name) {
      this.name = name;
  }
}
