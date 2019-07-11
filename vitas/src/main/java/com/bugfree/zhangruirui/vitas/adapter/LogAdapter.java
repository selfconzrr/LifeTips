package com.bugfree.zhangruirui.vitas.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bugfree.zhangruirui.vitas.R;
import com.bugfree.zhangruirui.vitas.repo.RequestInfo;
import com.bugfree.zhangruirui.vitas.view.LogDetailActivity;

import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {
  private List<RequestInfo> mRequestInfoList;
  private Context mContext;

  public LogAdapter(Context context, List<RequestInfo> mRequestInfoList) {
    mContext = context;
    this.mRequestInfoList = mRequestInfoList;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
        .moudle_recycle_item_log, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
    final RequestInfo requestInfo = mRequestInfoList.get(position);
    holder.responseStatus.setText(String.valueOf(requestInfo.getResponseCode()));
    holder.mRequestTime.setText(requestInfo.getRequestTime());

    if (holder.mCardView.getTag(R.id.tag_simple_url) == null) {
      StringBuilder urlBuilder = new StringBuilder();
      char[] chars = requestInfo.getUrl().toCharArray();
      for (int i = 0; i < chars.length; i++) {
        if (chars[i] == '?') {
            break;
        }
        urlBuilder.append(chars[i]);
      }
      holder.mCardView.setTag(R.id.tag_simple_url, urlBuilder.toString());
    }

    holder.mCardView.setTag(R.id.tag_item_position, position);
    holder.requestUrl.setText(holder.mCardView.getTag(R.id.tag_simple_url).toString());
    holder.mCardView.setOnClickListener(mOnClickListener);
  }

  @Override
  public int getItemCount() {
    return mRequestInfoList.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    CardView mCardView;
    TextView responseStatus;
    TextView mRequestTime;
    TextView requestUrl;

    ViewHolder(View itemView) {
      super(itemView);
      mCardView = (CardView) itemView;
      responseStatus = itemView.findViewById(R.id.list_item_status);
      requestUrl = itemView.findViewById(R.id.list_item_url);
      mRequestTime = itemView.findViewById(R.id.list_item_time);
    }
  }

  View.OnClickListener mOnClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      Intent intent = new Intent(mContext, LogDetailActivity.class);
      RequestInfo info = mRequestInfoList.get((Integer) v.getTag(R.id.tag_item_position));
      intent.putExtra(LogDetailActivity.REQUEST_INFO, info);
      mContext.startActivity(intent);
    }
  };

}