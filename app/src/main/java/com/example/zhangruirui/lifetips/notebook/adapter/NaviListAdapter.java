package com.example.zhangruirui.lifetips.notebook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhangruirui.lifetips.R;

import java.util.ArrayList;
import java.util.List;

// TODO: 2018/11/25 用 RecyclerView 替代 ListView
public class NaviListAdapter extends BaseAdapter {
  private List<String> mLabels = new ArrayList<>();
  private Context mContext;

  public NaviListAdapter() {
  }

  public NaviListAdapter(Context context, List<String> labels) {
    this.mContext = context;
    this.mLabels = labels;
  }

  @Override
  public int getCount() {
    return mLabels.size();
  }

  @Override
  public Object getItem(int position) {
    return mLabels.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    if (convertView == null) {
      LayoutInflater inflater = LayoutInflater.from(mContext);
      holder = new ViewHolder();
      convertView = inflater.inflate(R.layout.item_navigation, parent, false);
      holder.mTvLabel = convertView.findViewById(R.id.id_tv_navi_label);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    holder.mTvLabel.setText(mLabels.get(position));
    return convertView;
  }

  private static class ViewHolder {
    TextView mTvLabel;
  }
}
