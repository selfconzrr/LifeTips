package com.bugfree.zhangruirui.vitas.repo;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LogDBHelper extends SQLiteOpenHelper {
  public static final int S_DB_VERSION = 2;
  public static final String S_TABLE_NAME = "Logs";
  private static final String TAG = "NetLogDB:";

  public static String S_DB_NAME = "NetLog.db";

  public LogDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int
      version, DatabaseErrorHandler errorHandler) {
    super(context, name, factory, version, errorHandler);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {

    String sql = "CREATE TABLE " + S_TABLE_NAME + "(id integer PRIMARY KEY AUTOINCREMENT,url " +
        "text,method text,request_headers text,request_body text," +
        "content_type text,response_code integer,took_time_ms long,response_body text," +
        "response_headers text,request_time text" +
        ")";
    db.execSQL(sql);
  }

  // Called when the database is upgraded with DBVersion is changed
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.d(TAG, "onUpgrade: ");
    if (newVersion > oldVersion) {
      String sql = "ALTER TABLE " + S_TABLE_NAME + " ADD COLUMN request_time text";
      db.execSQL(sql);
    }
  }
}

