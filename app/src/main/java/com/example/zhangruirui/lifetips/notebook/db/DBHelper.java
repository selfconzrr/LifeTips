package com.example.zhangruirui.lifetips.notebook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
  public final static String DB_NAME = "notes";
  private final static int DB_VERSION = 1;

  DBHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("create table " + DB_NAME + "(_id integer primary key autoincrement" +
        ",title text, content text,create_time text)");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("drop table if exists" + DB_NAME);
    onCreate(db);
  }
}
