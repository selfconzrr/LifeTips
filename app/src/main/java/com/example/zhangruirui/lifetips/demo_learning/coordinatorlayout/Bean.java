package com.example.zhangruirui.lifetips.demo_learning.coordinatorlayout;

class Bean {
  private int mId;
  private String mName;

  public Bean(int mId, String mName) {
    this.mId = mId;
    this.mName = mName;
  }

  public int getId() {
    return mId;
  }

  private String getName() {
    return mName;
  }

  @Override
  public boolean equals(Object obj) {
    final Bean bean = (Bean) obj;
    return getName().equals(bean.getName());
  }

  @Override
  public int hashCode() {
    int result = getName().hashCode();
    result = 29 * result + getName().hashCode();
    return result;
  }
}
