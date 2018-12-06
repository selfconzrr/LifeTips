package com.example.zhangruirui.lifetips.demo_learning.coordinatorlayout;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

public class DiffItemCallBack extends DiffUtil.ItemCallback<Bean> {
  @Override
  public boolean areItemsTheSame(@NonNull Bean oldItem, @NonNull Bean newItem) {
    return oldItem.getmId() == newItem.getmId();
  }

  @Override
  public boolean areContentsTheSame(@NonNull Bean oldItem, @NonNull Bean newItem) {
    return oldItem.equals(newItem);
  }
}
