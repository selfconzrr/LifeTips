package com.example.zhangruirui.utils;

import com.tencent.mmkv.MMKV;

/**
 * singleton
 */
public final class MMKVManager {
  private static final String KEY_LAST_REQUEST_TIME = "key_light_value";
  private static final String MMAP_ID = "light_value";

  private MMKV defaultMMKV;
  private static boolean mUseMMKV;

  static {
    try {
      // MMKV.initialize(); // 初始化 MMKV
      mUseMMKV = true;
    } catch (Exception e) {
      e.printStackTrace();
      mUseMMKV = false;
    }
  }

  /**
   * private constructor
   */
  private MMKVManager() {
    if (mUseMMKV) {
      defaultMMKV = MMKV.mmkvWithID(MMAP_ID, MMKV.SINGLE_PROCESS_MODE);
    }
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
    if (defaultMMKV != null) {
      defaultMMKV.putInt(KEY_LAST_REQUEST_TIME, lightValue);
    }
  }

  /**
   * @return get the the the screen light
   */
  public int getLightValue() {
    if (defaultMMKV != null) {
      return defaultMMKV.getInt(KEY_LAST_REQUEST_TIME, 180);
    }
    return 180;
  }
}

