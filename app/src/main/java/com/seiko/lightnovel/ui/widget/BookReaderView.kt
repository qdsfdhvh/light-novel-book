package com.seiko.lightnovel.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.view.View

// THX: https://github.com/MewX/light-novel-library_Wenku8_Android/blob/master/studio-android/LightNovelLibrary/app/src/main/java/org/mewx/wenku8/reader/view/WenkuReaderPageView.java
class BookReaderView(context: Context) : View(context) {

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawWidgets(canvas)
        drawContent(canvas)
    }

    private fun drawWidgets(canvas: Canvas) {

    }

    private fun drawContent(canvas: Canvas) {

    }
}
