package com.example.zhangruirui.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/10/18
 *
 * {@link FileUtils 文件操作工具类}
 */
public class CacheCleanUtil {

  /**
   * 清除目录 (/data/data/com.xxx.xxx/cache) 下的缓存
   */
  public void cleanInternalCache(Context context) {
    deleteFilesByDirectory(context.getCacheDir());
  }

  /**
   * 清除目录 (/mnt/sdcard/android/data/com.xxx.xxx/cache) 下的缓存
   */
  public void cleanExternalCache(Context context) {
    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      deleteFilesByDirectory(context.getExternalCacheDir());
    }
  }

  /**
   * 清除 SharedPreference
   */
  public void cleanSharedPreference(Context context) {
    deleteFilesByDirectory(new File(context.getFilesDir().getPath()
        + context.getPackageName() + "/shared_prefs"));
  }

  /**
   * 清除 /data/data/com.xxx.xxx/files 文件缓存
   */
  public void cleanFiles(Context context) {
    deleteFilesByDirectory(context.getFilesDir());
  }

  /**
   * 删除文件/缓存 directory
   */
  @SuppressWarnings("ResultOfMethodCallIgnored")
  private static void deleteFilesByDirectory(File directory) {
    if (directory != null && directory.exists() && directory.isDirectory()) {
      for (File item : directory.listFiles()) {
        item.delete();
      }
    }
  }
}
