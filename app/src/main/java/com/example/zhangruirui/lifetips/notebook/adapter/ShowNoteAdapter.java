package com.example.zhangruirui.lifetips.notebook.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.zhangruirui.lifetips.R;
import com.example.zhangruirui.lifetips.notebook.util.TextFormatUtil;

// TODO: 2018/11/25 用 RecyclerView 替代 ListView

/**
 * CursorLoader 和 CursorAdapter 实现异步加载数据
 */
public class ShowNoteAdapter extends CursorAdapter {

  public ShowNoteAdapter(Context context, Cursor cursor) {
    super(context, cursor);
  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    LayoutInflater inflater1 = LayoutInflater.from(context);
    View view = inflater1.inflate(R.layout.item_note, null, false);
    ViewHolder holder = new ViewHolder();
    holder.mTvTitle = view.findViewById(R.id.id_tv_note_title);
    holder.mTvContent = view.findViewById(R.id.id_tv_note_summary);
    holder.mTvCreateTime = view.findViewById(R.id.id_tv_note_create_time);
    view.setTag(holder);
    return view;
  }

  @SuppressLint("SetTextI18n")
  @Override
  public void bindView(View view, Context context, Cursor cursor) {
    ViewHolder holder = (ViewHolder) view.getTag();
    String title = cursor.getString(cursor.getColumnIndex("title"));
    holder.mTvTitle.setText(title);
    holder.mTvContent.setText(TextFormatUtil.getNoteSummary(cursor.getString(cursor
        .getColumnIndex("content"))));
    holder.mTvCreateTime.setText("creation:" + cursor.getString(cursor.getColumnIndex
        ("create_time")));
  }

  final class ViewHolder {
    TextView mTvTitle;
    TextView mTvContent;
    TextView mTvCreateTime;
  }
}
