package com.example.zhangruirui.greendao;

import android.content.Context;
import android.support.annotation.WorkerThread;

import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.Type;

public class UserManager {
  private volatile static UserManager mInstance = null;
  private Context mContext;
  private UserStorage mUserStorage = new UserStorage(mContext);

  private UserManager() {

  }

  public static UserManager getInstance() {
    if (mInstance == null) {
      synchronized (UserManager.class) {
        if (mInstance == null) {
          mInstance = new UserManager();
        }
      }
    }
    return mInstance;
  }

  public <T> T getUserAge(String key, Type typeOfT) {
    try {
      String json = mUserStorage.getUserAge(key);
      Gson gson = new Gson();

      CacheEntry entry = gson.fromJson(json, CacheEntry.class);
      return gson.fromJson(entry.mJson, typeOfT);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @WorkerThread
  public void setUserAge(String key, Object entity, Type type) {
    setMagicFaceCacheInner(key, entity, type);
  }

  @WorkerThread
  private void setMagicFaceCacheInner(String key, Object entity, Type type) {
    Gson gson = new Gson();
    String json = gson.toJson(entity, type);
    CacheEntry entry = new CacheEntry(json);
    json = gson.toJson(entry, CacheEntry.class);
    if (entity == null) {
      mUserStorage.removeUser(key);
    } else {
      mUserStorage.addUser(key, json);
    }
  }

  static class CacheEntry implements Serializable {

    final String mJson;

    CacheEntry(String json) {
      mJson = json;
    }
  }
}
