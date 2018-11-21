package com.example.zhangruirui.lifetips.notes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhangruirui.lifetips.R;

import java.util.ArrayList;
import java.util.Map;

public class NotepadAdapter extends BaseAdapter {
  public Context context;
  private LayoutInflater inflater;
  private ArrayList<Map<String, Object>> list;

  NotepadAdapter(Activity activity, ArrayList<Map<String, Object>> list) {

    this.context = activity;
    this.list = list;
    inflater = LayoutInflater.from(context);
  }

  @Override
  public int getCount() {
    return list.size();
  }

  @Override
  public Object getItem(int arg0) {
    return list.get(arg0);
  }

  @Override
  public long getItemId(int arg0) {
    return arg0;
  }

  @Override
  public View getView(int arg0, View arg1, ViewGroup arg2) {
    SetShow setShow = new SetShow();
    Map<String, Object> map = list.get(arg0);
    boolean boo = (Boolean) map.get("EXPANDED");
    if (!boo) {
      arg1 = inflater.inflate(R.layout.showtypes, arg2, false);
      setShow.contentView = arg1
          .findViewById(R.id.contentTextView);
      setShow.dateView = arg1.findViewById(R.id.dateTextView);
      String str = (String) list.get(arg0).get("titleItem");
      String dateStr = (String) list.get(arg0).get("dateItem");
      setShow.contentView.setText("   " + str);
      setShow.dateView.setText(dateStr);
      setShow.showButtonWrite = arg1
          .findViewById(R.id.notes_edit);
      setShow.showButtonDelete = arg1
          .findViewById(R.id.notes_delete);
      setShow.showButtonWrite.setOnClickListener(new WriteButtonListener(
          arg0));
      setShow.showButtonDelete
          .setOnClickListener(new DeleteButtonListener(arg0));
    } else {
      arg1 = inflater.inflate(R.layout.style, arg2, false);
      setShow.cContentView = arg1
          .findViewById(R.id.changecontentview);
      setShow.cDateView = arg1
          .findViewById(R.id.changedateview);
      String str = (String) list.get(arg0).get("contentItem");
      String dateStr = (String) list.get(arg0).get("dateItem");
      setShow.cContentView.setText("" + str);
      setShow.cDateView.setText(dateStr);
      setShow.styleButtonWrite = arg1
          .findViewById(R.id.notes_edit);
      setShow.styleButtonWrite
          .setOnClickListener(new WriteButtonListener(arg0));
      setShow.styleButtonDelete = arg1
          .findViewById(R.id.notes_delete);
      setShow.styleButtonDelete
          .setOnClickListener(new DeleteButtonListener(arg0));
    }
    return arg1;
  }

  class WriteButtonListener implements View.OnClickListener {
    private int position;

    WriteButtonListener(int position) {
      this.position = position;
    }

    @Override
    public void onClick(View v) {

      Bundle b = new Bundle();
      b.putString("contentItem",
          (String) list.get(position).get("contentItem"));
      b.putString("dateItem", (String) list.get(position).get("dateItem"));
      b.putString("idItem", (String) list.get(position).get("idItem"));
      Intent intent = new Intent(context, EditActivity.class);
      intent.putExtras(b);
      context.startActivity(intent);
    }
  }

  class DeleteButtonListener implements View.OnClickListener {
    private int position;

    DeleteButtonListener(int position) {
      this.position = position;
    }

    @Override
    public void onClick(View v) {
      android.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
      builder.setTitle("确定要删除笔记吗");
      builder.setPositiveButton("删了吧",
          new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int i) {

              SQLiteHelper sql = new SQLiteHelper(context
              );
              SQLiteDatabase dataBase = sql.getWritableDatabase();
              SqliteOperation change = new SqliteOperation();
              Notepad notepad = new Notepad();
              notepad.setId((String) list.get(position).get(
                  "idItem"));
              change.delete(dataBase, notepad);
              ((TimeDiaryActivity) context).showUpdate();
              // a.showUpdate();
            }
          });
      builder.setNegativeButton("留着吧",
          new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

              dialog.dismiss();
            }
          });
      builder.create();
      builder.show();
    }
  }

  class SetShow {
    TextView contentView;
    TextView dateView;
    TextViewLine cContentView;
    TextView cDateView;
    Button styleButtonWrite;
    Button styleButtonDelete;
    Button showButtonWrite;
    Button showButtonDelete;
  }
}
