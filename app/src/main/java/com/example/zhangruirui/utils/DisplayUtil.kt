package com.example.zhangruirui.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.DisplayMetrics
import java.util.*

/**
 * Date：2019/11/18
 * Author：zhangruirui
 * Wechat：18811227256
 * Description：
 */
object DisplayUtil {
    /**
     * typeface：字体，如 Typeface.BOLD
     * color：字体颜色
     */
    @JvmOverloads
    fun changeTextColor(context: Context, text: String, color: Int = 0, typeface: Int = 0, start: Int = 0, end: Int = 0): SpannableString {
        val span = SpannableString(text)
        span.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        span.setSpan(StyleSpan(typeface), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return span
    }

    /**
     * dp 转 px
     */
    fun dp2px(context: Context, dp: Float): Float {
        val scale = context.resources.displayMetrics.density
        return dp * scale + 0.5f
    }

    /**
     * sp 转 px
     */
    fun sp2px(context: Context, sp: Float): Float {
        val scale = context.resources.displayMetrics.scaledDensity
        return sp * scale
    }

    /**
     * 获取屏幕宽度
     */
    fun getScreenWidth(context: Context): Int {
        val metric = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(metric)
        return metric.widthPixels
    }

    /**
     * 获取屏幕高度
     */
    fun getScreenHeight(context: Context): Int {
        val metric = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(metric)
        return metric.heightPixels
    }

    /**
     * 随机颜色
     */
    fun randomColor(): Int {
        val random = Random()
        val red = random.nextInt(150) + 50 // 50-199
        val green = random.nextInt(150) + 50 // 50-199
        val blue = random.nextInt(150) + 50 // 50-199
        return Color.rgb(red, green, blue)
    }
}