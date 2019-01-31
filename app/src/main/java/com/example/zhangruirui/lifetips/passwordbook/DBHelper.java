package com.example.zhangruirui.lifetips.passwordbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
  private static final String TABLE_NAME = "information";
  private final static String TABLE_ID = "ID";
  final static String TABLE_KEYWORD = "keyword";
  private final static String TABLE_ACCOUNT = "account";
  private final static String TABLE_PASSWORD = "password";
  private final static String TABLE_REMIND = "remind";

  private final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + TABLE_ID + " integer" +
      " primary " +
      "key autoincrement, " + TABLE_KEYWORD + " char(40), " + TABLE_ACCOUNT + " char(20), " +
      TABLE_PASSWORD + " char(20), " + TABLE_REMIND + " vchar(50))";

  private static final String DBNAME = "information.db";
  private static final int DBVERSION = 1;

  DBHelper(Context context, String ver) {
    super(context, DBNAME, null, DBVERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }

  public Cursor select() {
    SQLiteDatabase db = this.getReadableDatabase();
    return db.query(TABLE_NAME, null, null, null, null, null, null);
  }

  public long add(String keyword, String account, String password, String remind) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(TABLE_KEYWORD, keyword);
    contentValues.put(TABLE_ACCOUNT, account);
    contentValues.put(TABLE_PASSWORD, password);
    contentValues.put(TABLE_REMIND, remind);
    return db.insert(TABLE_NAME, null, contentValues);
  }

  public Cursor search(String keyword_for_search) {
    final String SEARCH = "select * from" + TABLE_NAME + "( where" + TABLE_KEYWORD + "like %" +
        keyword_for_search + "%)";
    SQLiteDatabase database = this.getWritableDatabase();
    return database.rawQuery(SEARCH, null);
  }

}
