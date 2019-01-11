package com.example.zhangruirui.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：01/11/19
 */
public class FileUtils {

  private String SDPATH;

  public String getSDPATH() {
    return SDPATH;
  }

  public FileUtils() {
    // 得到 SD 卡的根目录
    SDPATH = Environment.getExternalStorageDirectory() + "/";
  }

  /*
   * 在 sd 卡上创建文件
   */
  private File creatSDFile(String fileName) throws IOException {

    File file = new File(SDPATH + fileName);
    file.createNewFile();
    return file;
  }

  /*
   * 在 sd 卡上创建目录
   */
  private File creatSDDir(String dirName) {
    File dir = new File(SDPATH + dirName);
    dir.mkdir();
    return dir;
  }

  /*
   * 判断文件是否存在
   */
  public boolean isFileExists(String fileName) {
    File file = new File(SDPATH + fileName);
    return file.exists();
  }

  /*
   * 将 inputStream 里的数据写入 sd 卡
   */
  public File write2SDFromInput(String path, String fileName,
                                InputStream input) {
    File file = null;
    OutputStream output = null;
    try {
      creatSDDir(path);
      file = creatSDFile(path + fileName);
      output = new FileOutputStream(file);
      byte buffer[] = new byte[4 * 1024]; // 每 4k 写一次
      while (input.read(buffer) != -1) {
        output.write(buffer);
      }
      output.flush(); // 清空一下缓存·
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        output.close(); // 关闭流
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return file;
  }
}
