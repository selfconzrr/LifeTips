package com.example.zhangruirui.lifetips.notes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class SqliteOperation {
  public static String table = "table_notepad";

  public long add(SQLiteDatabase paramSQLiteDatabase, Notepad paramNotepad) {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("title", paramNotepad.getTitle());
    localContentValues.put("date", paramNotepad.getData());
    localContentValues.put("content", paramNotepad.getContent());
    long l = paramSQLiteDatabase.insert(table, null, localContentValues);
    paramSQLiteDatabase.close();
    return l;
  }

  public void delete(SQLiteDatabase paramSQLiteDatabase, Notepad paramNotepad) {
    paramSQLiteDatabase.delete(table, "id=" + paramNotepad.getId(), null);
    paramSQLiteDatabase.close();
  }

  public ArrayList<Notepad> query(SQLiteDatabase paramSQLiteDatabase) {
    ArrayList<Notepad> localArrayList = new ArrayList<Notepad>();
    Cursor localCursor = paramSQLiteDatabase.query(table, new String[]{
            "id", "title", "content", "date"}, null, null, null, null,
        null);
    while (true) {
      if (!localCursor.moveToNext()) {
        paramSQLiteDatabase.close();
        return localArrayList;
      }
      Notepad localNotepad = new Notepad();
      localNotepad.setId(localCursor.getString(localCursor
          .getColumnIndex("id")));
      localNotepad.setTitle(localCursor.getString(localCursor
          .getColumnIndex("title")));
      localNotepad.setContent(localCursor.getString(localCursor
          .getColumnIndex("content")));
      localNotepad.setData(localCursor.getString(localCursor
          .getColumnIndex("date")));
      localArrayList.add(localNotepad);
    }
  }

  public void update(SQLiteDatabase paramSQLiteDatabase, Notepad paramNotepad) {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("title", paramNotepad.getTitle());
    localContentValues.put("content", paramNotepad.getContent());
    localContentValues.put("date", paramNotepad.getData());
    paramSQLiteDatabase.update(table, localContentValues, "id="
        + paramNotepad.getId(), null);
    paramSQLiteDatabase.close();
  }
}
