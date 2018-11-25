package com.example.zhangruirui.lifetips.notebook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/25/18
 * <p>
 * 关于 notes 数据的 CRUD
 */
public class NoteDAO {
  private DBHelper mHelper;

  public NoteDAO(Context context) {
    mHelper = new DBHelper(context);
  }

  public long insertNote(ContentValues contentValues) {
    SQLiteDatabase db = mHelper.getWritableDatabase();
    long id = db.insert(DBHelper.DB_NAME, null, contentValues);
    db.close();
    return id;
  }

  public int updateNote(ContentValues values, String whereClause, String[] whereArgs) {
    SQLiteDatabase db = mHelper.getWritableDatabase();
    int count = db.update(DBHelper.DB_NAME, values, whereClause, whereArgs);
    db.close();
    return count;
  }

  public int deleteNote(String whereClause, String[] whereArgs) {
    SQLiteDatabase db = mHelper.getWritableDatabase();
    return db.delete(DBHelper.DB_NAME, whereClause, whereArgs);
  }

  public Cursor queryNote(String selection, String[] selectionArgs) {
    SQLiteDatabase db = mHelper.getReadableDatabase();
    return db.query(false, DBHelper.DB_NAME, null, selection, selectionArgs
        , null, null, null, null);
  }
}
