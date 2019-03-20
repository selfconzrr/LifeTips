package com.example.zhangruirui;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

@Entity(
    indexes = {
        @Index(value = "name DESC", unique = true)
    }
)
public class User {

  @Id
  private Long id;

  private String name;

  private String age;

  @Generated(hash = 1666193281)
  public User(Long id, String name, String age) {
      this.id = id;
      this.name = name;
      this.age = age;
  }

  @Generated(hash = 586692638)
  public User() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }
}
