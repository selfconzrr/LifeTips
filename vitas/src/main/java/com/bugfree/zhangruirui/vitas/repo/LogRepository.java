package com.bugfree.zhangruirui.vitas.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bugfree.zhangruirui.vitas.Vitas;
import com.bugfree.zhangruirui.vitas.presenter.OnSaveDBListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogRepository {

  private static final String URL = "url";
  private static final String REQUEST_TIME = "request_time";
  private static final String METHOD = "method";
  private static final String REQUEST_HEADERS = "request_headers";
  private static final String REQUEST_BODY = "request_body";
  private static final String CONTENT_TYPE = "content_type";
  private static final String RESPONSE_CODE = "response_code";
  private static final String TOOK_TIME_MS = "took_time_ms";
  private static final String RESPONSE_BODY = "response_body";
  private static final String RESPONSE_HEADERS = "response_headers";
  private static final String ID = "id";
  private static final String TAG = "LogRepository";

  private SQLiteDatabase mLogDataBase;
  private ExecutorService mSaveDBExecutor;

  public LogRepository() {
    LogDBHelper logDBHelper = new LogDBHelper(Vitas.getInstance().getContext(), LogDBHelper
        .S_DB_NAME, null, LogDBHelper.S_DB_VERSION, null);
    mLogDataBase = logDBHelper.getWritableDatabase();
    mSaveDBExecutor = Executors.newSingleThreadExecutor();
  }

  public void saveToDB(final RequestInfo requestInfo, final OnSaveDBListener onSaveDBListener) {
    Runnable saveDBTask = new Runnable() {
      @Override
      public void run() {
        try {
          Log.d(TAG, "saveToDB: ");
          saveToDB(requestInfo);
          if (onSaveDBListener != null) {
            onSaveDBListener.saveSuccess();
          }
        } catch (Exception e) {
          Log.d(TAG, e.toString());
          if (onSaveDBListener != null) {
            onSaveDBListener.saveFailed();
          }
        }
      }
    };
    mSaveDBExecutor.execute(saveDBTask);
  }

  /**
   * 查询全部数据
   */
  public List<RequestInfo> queryDB() {
    return queryDB("");
  }

  /**
   * Query the database based on criteria
   */
  public List<RequestInfo> queryDB(String s) {
    Cursor cursor = mLogDataBase.query(LogDBHelper.S_TABLE_NAME, null, null, null, null, null,
        null);
    cursor.moveToFirst();
    List<RequestInfo> requestInfoList = new ArrayList<>();
    for (int i = 0; i < cursor.getCount(); i++) {
      int id = cursor.getInt(cursor.getColumnIndex(ID));
      String url = cursor.getString(cursor.getColumnIndex(URL));
      if (!url.contains(s)) {
        break;
      }
      String requestHeaders = cursor.getString(cursor.getColumnIndex(REQUEST_HEADERS));
      String method = cursor.getString(cursor.getColumnIndex(METHOD));
      String requestBody = cursor.getString(cursor.getColumnIndex(REQUEST_BODY));
      int responseCode = cursor.getInt(cursor.getColumnIndex(RESPONSE_CODE));
      String responseHeaders = cursor.getString(cursor.getColumnIndex(RESPONSE_HEADERS));
      long tookTimeMS = cursor.getLong(cursor.getColumnIndex(TOOK_TIME_MS));
      String contentType = cursor.getString(cursor.getColumnIndex(CONTENT_TYPE));
      String responseBody = cursor.getString(cursor.getColumnIndex(RESPONSE_BODY));
      String requestTime = cursor.getString(cursor.getColumnIndex(REQUEST_TIME));
      cursor.moveToNext();

      RequestInfo requestInfo = new RequestInfo(id, url, requestHeaders, method, requestBody,
          responseCode, responseHeaders, tookTimeMS, contentType, responseBody, requestTime);
      requestInfoList.add(0, requestInfo); // 头插法，按时间倒序排列
    }

    cursor.close();
    return requestInfoList;
  }

  public void clearDB() {
    mLogDataBase.delete(LogDBHelper.S_TABLE_NAME, null, null);
  }

  private void saveToDB(RequestInfo requestInfo) {
    ContentValues values = new ContentValues();
    values.put(URL, requestInfo.getUrl());
    values.put(REQUEST_TIME, requestInfo.getRequestTime());
    values.put(METHOD, requestInfo.getMethod());
    values.put(REQUEST_HEADERS, requestInfo.getRequestHeaders());
    values.put(REQUEST_BODY, requestInfo.getRequestBody());
    values.put(CONTENT_TYPE, requestInfo.getResponseContentType());
    values.put(RESPONSE_CODE, requestInfo.getResponseCode());
    values.put(TOOK_TIME_MS, requestInfo.getTookTimeMS());
    values.put(RESPONSE_BODY, requestInfo.getResponseBody());
    values.put(RESPONSE_HEADERS, requestInfo.getResponseHeaders());
    mLogDataBase.insert(LogDBHelper.S_TABLE_NAME, null, values);
  }

}