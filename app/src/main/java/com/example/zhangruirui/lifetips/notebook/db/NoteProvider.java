package com.example.zhangruirui.lifetips.notebook.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/25/18
 */
public class NoteProvider extends ContentProvider {
  private NoteDAO mNoteDAO;

  @Override
  public boolean onCreate() {
    mNoteDAO = new NoteDAO(getContext());
    return false;
  }

  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[]
      selectionArgs, String sortOrder) {
    Log.v("LOG", "NoteProvider -- query()");
    Cursor c = mNoteDAO.queryNote(null, null);
    Log.v("LOG", "id=" + c.getInt(1) + " content=" + c.getString(2));
    return c;
  }

  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    return null;
  }

  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri, ContentValues values) {
    return null;
  }

  @Override
  public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
    return 0;
  }

  @Override
  public int update(@NonNull Uri uri, ContentValues values, String selection, String[]
      selectionArgs) {
    return 0;
  }
}
