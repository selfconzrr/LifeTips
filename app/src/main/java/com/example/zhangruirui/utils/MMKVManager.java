package com.example.zhangruirui.utils;

import com.tencent.mmkv.MMKV;

/**
 * singleton
 */
public final class MMKVManager {
  private static final String KEY_LAST_REQUEST_TIME = "key_light_value";
  private MMKV defaultMMKV;

  /**
   * private constructor
   */
  private MMKVManager() {
    defaultMMKV = MMKV.defaultMMKV();
  }

  private static class MMKVHolder {
    private static MMKVManager mInstance = new MMKVManager();
  }

  public static MMKVManager getInstance() {
    return MMKVHolder.mInstance;
  }

  /**
   * @param lightValue save the screen light
   */
  public void setLightValue(int lightValue) {
    defaultMMKV.putInt(KEY_LAST_REQUEST_TIME, lightValue);
  }

  /**
   * @return get the the the screen light
   */
  public int getLightValue() {
    return defaultMMKV.getInt(KEY_LAST_REQUEST_TIME, 180);
  }
}

