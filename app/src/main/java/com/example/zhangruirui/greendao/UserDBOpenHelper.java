package com.example.zhangruirui.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class UserDBOpenHelper extends DaoMaster.DevOpenHelper {
  public UserDBOpenHelper(Context context, String name) {
    super(context, name);
  }

  public UserDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
    super(context, name, factory);
  }

  @Override
  public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    DaoMaster.dropAllTables(wrap(db), true);
  }
}
