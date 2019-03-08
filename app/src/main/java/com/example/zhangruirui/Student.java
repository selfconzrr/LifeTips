package com.example.zhangruirui;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Student {

  @Id(autoincrement = true)
  private long stuId;

  private String name;

  private int age;
  
  private String classNo;

  @Generated(hash = 475962657)
  public Student(long stuId, String name, int age, String classNo) {
      this.stuId = stuId;
      this.name = name;
      this.age = age;
      this.classNo = classNo;
  }

  @Generated(hash = 1556870573)
  public Student() {
  }

  public long getStuId() {
      return this.stuId;
  }

  public void setStuId(long stuId) {
      this.stuId = stuId;
  }

  public String getName() {
      return this.name;
  }

  public void setName(String name) {
      this.name = name;
  }

  public int getAge() {
      return this.age;
  }

  public void setAge(int age) {
      this.age = age;
  }

  public String getClassNo() {
      return this.classNo;
  }

  public void setClassNo(String classNo) {
      this.classNo = classNo;
  }
}
