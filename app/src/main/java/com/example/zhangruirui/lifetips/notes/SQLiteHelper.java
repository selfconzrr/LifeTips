package com.example.zhangruirui.lifetips.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/18/18
 */
public class SQLiteHelper extends SQLiteOpenHelper {
  private static String INFONAME;
  private static String NAME;
  private static int VERSION = 1;

  static {
    NAME = " table_notepad";
    INFONAME = "notepad.db";
  }

  SQLiteHelper(Context paramContext) {
    super(paramContext, INFONAME, null, VERSION);
  }

  public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
    paramSQLiteDatabase
        .execSQL("create table "
            + NAME
            + "(id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,date TEXT,content TEXT)");
  }

  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1,
                        int paramInt2) {
  }
}
