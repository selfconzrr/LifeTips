package com.example.zhangruirui.utils;

import android.os.Build;

/**
 * @date：2019/7/4
 * @author：zhangruirui
 * @wechat：18811227256
 * @description： 系统工具类
 * 比如查看系统版本
 */
public class SystemUtil {

    public static boolean isAtLeastOreo() {
        return aboveApiLevel(Build.VERSION_CODES.O);
    }

    public static boolean isAtLeastNougat() {
        return aboveApiLevel(Build.VERSION_CODES.N);
    }

    public static boolean aboveApiLevel(int sdkInt) {
        return getApiLevel() >= sdkInt;
    }

    public static int getApiLevel() {
        return Build.VERSION.SDK_INT;
    }
}
