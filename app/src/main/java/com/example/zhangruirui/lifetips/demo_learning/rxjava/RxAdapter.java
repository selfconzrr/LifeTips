package com.example.zhangruirui.lifetips.demo_learning.rxjava;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhangruirui.lifetips.R;

import java.util.ArrayList;

public class RxAdapter extends RecyclerView.Adapter<RxAdapter.ViewHolder> {
  private ArrayList<String> mDataSet;

  public RxAdapter(ArrayList<String> dataSet) {
    mDataSet = dataSet;
  }

  @NonNull
  @Override
  public RxAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_layout, parent,
        false);
    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.mTitle.setText(mDataSet.get(position));
  }

  @Override
  public int getItemCount() {
    return mDataSet.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    TextView mTitle;

    ViewHolder(View itemView) {
      super(itemView);
      mTitle = itemView.findViewById(R.id.item_tv);
    }
  }
}
