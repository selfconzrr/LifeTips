package com.example.zhangruirui;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class User {

  @Id
  private long id;

  private String name;

  private int age;

  @Generated(hash = 446251977)
  public User(long id, String name, int age) {
    this.id = id;
    this.name = name;
    this.age = age;
  }

  @Generated(hash = 586692638)
  public User() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}
