package com.example.zhangruirui.lifetips.demo_learning.coordinatorlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.util.Log;

import java.util.List;

public class DiffCallBack extends DiffUtil.Callback {
  private List<String> mOldDatas, mNewDatas;

  DiffCallBack(List<String> oldDatas, List<String> newDatas) {
    this.mOldDatas = oldDatas;
    this.mNewDatas = newDatas;
  }

  // 老数据集 size
  @Override
  public int getOldListSize() {
    return mOldDatas != null ? mOldDatas.size() : 0;
  }

  // 新数据集 size
  @Override
  public int getNewListSize() {
    return mNewDatas != null ? mNewDatas.size() : 0;
  }

  /**
   * Called by the DiffUtil to decide whether two object represent the same Item.
   * 被 DiffUtil 调用，用来判断两个对象是否是相同的 Item。
   * For example, if your items have unique ids, this method should check their id equality.
   * 例如，如果你的Item有唯一的id字段，这个方法就判断id是否相等。
   *
   * @param oldItemPosition The position of the item in the old list
   * @param newItemPosition The position of the item in the new list
   * @return True if the two items represent the same object or false if they are different.
   */

  @Override
  public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
//    Log.e("zhangrr", "areItemsTheSame: " + (oldItemPosition == newItemPosition)
//    + " oldItemPosition = " + oldItemPosition + " newItemPosition = " + newItemPosition);
//    return oldItemPosition == newItemPosition;
    return mOldDatas.get(oldItemPosition).equals(mNewDatas.get(newItemPosition));
//    return mOldDatas.get(oldItemPosition).getClass().equals(mNewDatas.get(newItemPosition)
// .getClass());
  }

  /**
   * Called by the DiffUtil when it wants to check whether two items have the same data.
   * 被 DiffUtil 调用，用来检查两个 item 是否含有相同的数据
   * DiffUtil uses this information to detect if the contents of an item has changed.
   * DiffUtil 用返回的信息（true false）来检测当前 item 的内容是否发生了变化
   * DiffUtil uses this method to check equality instead of {@link Object#equals(Object)}
   * DiffUtil 用这个方法替代 equals 方法去检查是否相等。
   * so that you can change its behavior depending on your UI.
   * 所以你可以根据你的 UI 去改变它的返回值
   * For example, if you are using DiffUtil with a
   * {@link android.support.v7.widget.RecyclerView.Adapter RecyclerView.Adapter}, you should
   * return whether the items' visual representations are the same.
   * 例如，如果你用 RecyclerView.Adapter 配合 DiffUtil 使用，你需要返回 Item 的视觉表现是否相同。
   * This method is called only if {@link #areItemsTheSame(int, int)} returns
   * {@code true} for these items.
   * 这个方法仅仅在 areItemsTheSame() 返回 true 时，才会被调用。
   *
   * @param oldItemPosition The position of the item in the old list
   * @param newItemPosition The position of the item in the new list which replaces the
   *                        oldItem
   * @return True if the contents of the items are the same or false if they are different.
   */
  @Override
  public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
    String oldData = mOldDatas.get(oldItemPosition);
    String newData = mNewDatas.get(newItemPosition);
//    Log.e("zhangrr", "areContentsTheSame: " + oldData.equals(newData)
//        + " oldItemPosition = " + oldItemPosition + " newItemPosition = " + newItemPosition);
    return oldData.equals(newData);
  }

  /**
   * When {@link #areItemsTheSame(int, int)} returns {@code true} for two items and
   * {@link #areContentsTheSame(int, int)} returns false for them, DiffUtil
   * calls this method to get a payload about the change.
   * 定向刷新中的局部更新
   *
   * @param oldItemPosition The position of the item in the old list
   * @param newItemPosition The position of the item in the new list which replaces the
   *                        oldItem
   * @return A payload object that represents the change between the two items.
   */
  @Nullable
  @Override
  public Object getChangePayload(int oldItemPosition, int newItemPosition) {
    String oldData = mOldDatas.get(oldItemPosition);
    String newData = mNewDatas.get(newItemPosition);

    Bundle payload = new Bundle();
    if (!oldData.equals(newData)) {
      payload.putString("NEW_DATA", newData);
    }
    Log.e("zhangrr", "getChangePayload() called with: oldItemPosition = [" + oldItemPosition +
        "], newItemPosition = "
        + newItemPosition + " oldData = [" + oldData + "], newData = [" + newData + " payload = "
        + payload.size());
    return payload.size() == 0 ? null : payload;
  }
}
