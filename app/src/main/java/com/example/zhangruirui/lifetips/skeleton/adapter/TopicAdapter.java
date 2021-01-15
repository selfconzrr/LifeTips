package com.example.zhangruirui.lifetips.skeleton.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.zhangruirui.lifetips.R;

public class TopicAdapter extends RecyclerView.Adapter<SimpleRcvViewHolder> {
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_CONTENT = 2;

    @Override
    public SimpleRcvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new SimpleRcvViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title_more, parent, false));
        }
        return new SimpleRcvViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false));
    }

    @Override
    public void onBindViewHolder(SimpleRcvViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 7;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_CONTENT;
    }
}
