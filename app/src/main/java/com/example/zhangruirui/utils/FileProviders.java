package com.example.zhangruirui.utils;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;

/**
 * Utility class 配合 {@link android.support.v4.content.FileProvider} 使用的工具类
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class FileProviders {

  /**
   * 要在应用间共享文件，应发送一项 content:// URI，并授予 URI 临时访问权限。
   * 进行此授权的最简单方式是使用 FileProvider 类，该字符串即在 manifest 文件中声明的 authority
   */
  private static final String FILE_PROVIDER_AUTHORITY = "com.example.zhangruirui.lifetips.fileprovider";

  /**
   * 获取某个文件相应的 Content URI. 并且授权访问：读写
   *
   * @see #getUriForReadFile(Context, File, Intent) 读
   * @see #getUriForWriteFile(Context, File, Intent) 写
   */
  public static Uri getUriForFile(@NonNull Context context, @NonNull File file,
                                  @Nullable Intent intent) {
    return getUriForFile(context, file, intent,
        FLAG_GRANT_READ_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION);
  }

  /**
   * 获取某个文件相应的 Content URI. 并且授权访问：只读
   *
   * @see #getUriForWriteFile(Context, File, Intent) 写
   * @see #getUriForFile(Context, File, Intent) 读写
   */
  public static Uri getUriForReadFile(@NonNull Context context, @NonNull File file,
                                      @Nullable Intent intent) {
    return getUriForFile(context, file, intent, FLAG_GRANT_READ_URI_PERMISSION);
  }

  /**
   * 获取某个文件相应的 Content URI. 并且授权访问：写
   *
   * @see #getUriForFile(Context, File, Intent) 读写
   * @see #getUriForReadFile(Context, File, Intent) 读
   */
  public static Uri getUriForWriteFile(@NonNull Context context, @NonNull File file,
                                       @Nullable Intent intent) {
    return getUriForFile(context, file, intent, FLAG_GRANT_WRITE_URI_PERMISSION);
  }

  /**
   * 检查当前的进程和用户 是否有某个 Content Uri 的访问权限：读写
   *
   * @see #checkUriReadPermission(Context, Uri) 读
   * @see #checkUriWritePermission(Context, Uri) 写
   */
  public static boolean checkUriPermission(@NonNull Context context, @NonNull Uri uri) {
    return checkUriPermission(context, uri,
        FLAG_GRANT_READ_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION);
  }

  /**
   * 检查当前的进程和用户 是否有某个 Content Uri 的访问权限：读
   *
   * @see #checkUriPermission(Context, Uri) 读写
   * @see #checkUriWritePermission(Context, Uri) 写
   */
  public static boolean checkUriReadPermission(@NonNull Context context, @NonNull Uri uri) {
    return checkUriPermission(context, uri, FLAG_GRANT_READ_URI_PERMISSION);
  }

  /**
   * 检查当前的进程和用户 是否有某个 Content Uri 的访问权限：写
   *
   * @see #checkUriPermission(Context, Uri) 读写
   * @see #checkUriReadPermission(Context, Uri) 读
   */
  public static boolean checkUriWritePermission(@NonNull Context context, @NonNull Uri uri) {
    return checkUriPermission(context, uri, FLAG_GRANT_WRITE_URI_PERMISSION);
  }

  private static boolean checkUriPermission(@NonNull Context context, @NonNull Uri uri,
                                            int permissionFlags) {
    return !SystemUtil.isAtLeastNougat() || context.checkCallingUriPermission(uri,
        permissionFlags) == PackageManager.PERMISSION_GRANTED;
  }

  /**
   * 撤销某个 Uri 的访问授权: 读写
   *
   * @see #revokeUriReadPermission(Context, Uri) 撤销 读
   * @see #revokeUriWritePermission(Context, Uri) 撤销 写
   */
  public static void revokeUriPermission(@NonNull Context context, @NonNull Uri uri) {
    revokeUriPermission(context, uri,
        FLAG_GRANT_READ_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION);
  }

  /**
   * 撤销某个 Uri 的访问授权: 读
   *
   * @see #revokeUriPermission(Context, Uri) 撤销 读写
   * @see #revokeUriWritePermission(Context, Uri) 撤销 写
   */
  public static void revokeUriReadPermission(@NonNull Context context, @NonNull Uri uri) {
    revokeUriPermission(context, uri, FLAG_GRANT_READ_URI_PERMISSION);
  }

  /**
   * 撤销某个 Uri 的访问授权: 写
   *
   * @see #revokeUriPermission(Context, Uri) 撤销 读写
   * @see #revokeUriReadPermission(Context, Uri) 撤销 读
   */
  public static void revokeUriWritePermission(@NonNull Context context, @NonNull Uri uri) {
    revokeUriPermission(context, uri, FLAG_GRANT_WRITE_URI_PERMISSION);
  }

  private static void revokeUriPermission(@NonNull Context context, @NonNull Uri uri,
                                          int permissionFlags) {
    if (SystemUtil.isAtLeastNougat()) {
      context.revokeUriPermission(uri, permissionFlags);
    }
  }

  @NonNull
  private static Uri getUriForFile(@NonNull Context context, @NonNull File file,
                                   @Nullable Intent intent, int permissionFlags) {
    final Uri uri;
    // 为了减少 App 启动时间耗时，仅在系统版本大于等于 Android Nougat(7.0-24) 时启用 FileProvider
    if (SystemUtil.isAtLeastNougat()) {
      try {
        uri = FileProvider.getUriForFile(context, FILE_PROVIDER_AUTHORITY, file);
      } catch (Throwable e) {
        return getUriForFile(file);
      }
      if (intent != null) {
        intent.addFlags(permissionFlags);
      } else {
        // This grants temporary access permission for the content URI to the specified package
        // The permission remains in effect until you revoke it by calling revokeUriPermission()
        // or until the device reboots.
        context.grantUriPermission(context.getPackageName(), uri,
            permissionFlags);
      }
    } else {
      uri = getUriForFile(file);
    }
    return uri;
  }

  @NonNull
  private static Uri getUriForFile(@NonNull File file) {
    try {
      return Uri.fromFile(file);
    } catch (Throwable e) {
      return Uri.EMPTY;
    }
  }


  private FileProviders() {
    // LEFT-DO-NOTHING
  }

}


