package com.example.zhangruirui.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Date：2019/11/18
 * Author：zhangruirui
 * Wechat：18811227256
 * Description：
 */
object NavigateUtil {
    fun startActivity(context: Context, clazz: Class<Any>) {
        val intent = Intent(context, clazz)
        context.startActivity(intent)
    }

    fun startOutSideBrowser(context: Context, url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    }
}