package com.example.zhangruirui.lifetips.demo_learning.coordinatorlayout;

import android.os.Bundle;
import android.support.v7.recyclerview.extensions.AsyncListDiffer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhangruirui.lifetips.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

  private boolean hasNotLongClickFlag = true;

  private List<String> list;

  private OnItemClickListener itemClickListener;

  final AsyncListDiffer<Bean> mItemCallback = new AsyncListDiffer(this, new DiffItemCallBack());

  MyAdapter() {
  }

  public void setDatas(List<String> list) {
    // this.list = list; // 这样实质是保存的引用，会导致两个数据集同步变化
    this.list = new ArrayList<>(list); // 第一种写法
    if (list != null) { // 第二种写法
      this.list.clear();
      this.list.addAll(list);
    }
    Log.e("zhangrr", "setDatas() called with: list = [" + list.size() + "]");
  }

  List getDatas() {
    return list;
  }

  @Override
  public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    Log.e("zhangrr", "onCreateViewHolder() called with: parent = " + "]");
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent,
        false);
    final MyViewHolder viewHolder = new MyViewHolder(view);

    if (itemClickListener != null) {
      viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (hasNotLongClickFlag) {
            itemClickListener.onClick(viewHolder.getLayoutPosition());// 获取当前条目的position
          }
          hasNotLongClickFlag = true;
        }
      });
      viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
          itemClickListener.onLongClick(viewHolder.getLayoutPosition());
          hasNotLongClickFlag = false;
          return false;
        }
      });
    }
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(final MyViewHolder holder, int position) {
    Log.e("zhangrr", "两参 onBindViewHolder() called with: position = " + position + " text = " +
        list.get(position));
    holder.mText.setText(list.get(position));
    holder.mText.setTag(list.get(position));
  }

  /**
   * 三参的 onBindViewHolder 才是真正和 onCreateViewHolder 对应的方法，在该方法内部会调用两参的 onBindViewHolder
   */
  @Override
  public void onBindViewHolder(MyViewHolder holder, int position, List<Object> payloads) {
    Log.e("zhangrr", "三参 onBindViewHolder() called with: position = [" + position + "], " +
        "adapter = [" + holder.getAdapterPosition() + " layout = " + holder.getLayoutPosition()
        + " getpos = " + holder.getPosition());
    if (payloads.isEmpty()) {
      onBindViewHolder(holder, holder.getAdapterPosition());
    } else {
      Bundle payload = (Bundle) payloads.get(0);
      for (String key : payload.keySet()) {
        switch (key) {
          case "NEW_DATA":
            holder.mText.setText(payload.getString(key));
            break;
        }
      }
    }
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.itemClickListener = onItemClickListener;
  }

  @Override
  public void onViewRecycled(MyViewHolder holder) {
    super.onViewRecycled(holder);
  }

  public interface OnItemClickListener {
    void onClick(int position);

    void onLongClick(int position);
  }

  // 去除指定位置的子项
  public void removeItem(int position) {
    if (position < list.size() && position >= 0) {
      list.remove(position);
      notifyDataSetChanged();
      // notifyItemRemoved(position);
    }
  }

  private void clearAll() {
    list.clear();
    notifyDataSetChanged();
  }

  // 在数据集更新的地方调用 submitList 方法即可
  private void submitList(List<Bean> list) {
    mItemCallback.submitList(list);
  }

  // 自定义的ViewHolder
  class MyViewHolder extends RecyclerView.ViewHolder {
    TextView mText;

    MyViewHolder(View itemView) {
      super(itemView);
      mText = (TextView) itemView.findViewById(R.id.item_tv);
    }
  }

}
