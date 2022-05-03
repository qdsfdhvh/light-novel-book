package com.seiko.lightnovel.novel.view

import android.graphics.Color
import androidx.annotation.ColorInt

data class ReaderConfig(
    val viewWidth: Int,
    val viewHeight: Int,
    // title
    val titleSize: Float = 20f,
    @ColorInt val titleColor: Int = Color.BLACK,
    // text
    val textSize: Float = 30f,
    @ColorInt val textColor: Int = Color.BLACK,
    // widget
    val widgetSize: Float = 15f,
    @ColorInt val widgetColor: Int = Color.BLACK,
    val widgetBorder: Float = 1f,
    // global
    val contentPadding: Int = 5,
) {
    companion object {
        val None by lazy {
            ReaderConfig(0, 0)
        }
    }
}
