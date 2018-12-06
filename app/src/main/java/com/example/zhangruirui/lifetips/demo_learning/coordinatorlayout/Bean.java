package com.example.zhangruirui.lifetips.demo_learning.coordinatorlayout;

class Bean {
  private int mId;
  private String mName;

  public Bean(int mId, String mName) {
    this.mId = mId;
    this.mName = mName;
  }

  public int getmId() {
    return mId;
  }

  public void setmId(int mId) {
    this.mId = mId;
  }

  public String getmName() {
    return mName;
  }

  public void setmName(String mName) {
    this.mName = mName;
  }

  @Override
  public boolean equals(Object obj) {
    final Bean bean = (Bean) obj;
    return getmName().equals(bean.getmName());
  }

  @Override
  public int hashCode() {
    int result = getmName().hashCode();
    result = 29 * result + getmName().hashCode();
    return result;
  }
}
