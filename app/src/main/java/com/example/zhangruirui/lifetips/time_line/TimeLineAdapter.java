package com.example.zhangruirui.lifetips.time_line;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhangruirui.lifetips.R;

import java.util.ArrayList;
import java.util.HashMap;

public class TimeLineAdapter extends RecyclerView.Adapter {
  private LayoutInflater mInflater;
  private ArrayList<HashMap<String, Object>> mListItem;

  TimeLineAdapter(Context context, ArrayList<HashMap<String, Object>> listItem) {
    mInflater = LayoutInflater.from(context);
    this.mListItem = listItem;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int pos) {
    return new MyViewHolder(mInflater.inflate(R.layout.list_cell, null));
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int pos) {
    MyViewHolder vh = (MyViewHolder) viewHolder;
    vh.Title.setText((String) mListItem.get(pos).get("ItemTitle"));
    vh.Text.setText((String) mListItem.get(pos).get("ItemText"));
  }

  @Override
  public int getItemCount() {
    return mListItem.size();
  }

  class MyViewHolder extends RecyclerView.ViewHolder {
    private TextView Title, Text;

    MyViewHolder(View root) {
      super(root);
      Title = root.findViewById(R.id.tvItemTitle);
      Text = root.findViewById(R.id.tvItemText);
    }

    public TextView getTitle() {
      return Title;
    }

    public TextView getText() {
      return Text;
    }

  }
}
