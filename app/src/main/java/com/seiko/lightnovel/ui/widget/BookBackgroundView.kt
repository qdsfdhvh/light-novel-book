package com.seiko.lightnovel.ui.widget

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import com.seiko.lightnovel.R

class BookBackgroundView(context: Context) : View(context) {

    private lateinit var backgroundEdge: Drawable
    private lateinit var backgroundContent: Drawable

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        backgroundEdge = createBackgroundEdge(w, h)
        backgroundContent = createBackgroundContent(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        backgroundEdge.draw(canvas)
        backgroundContent.draw(canvas)
    }

    private fun createBackgroundEdge(w: Int, h: Int): Drawable {
        val bitmap = BitmapFactory.decodeResource(
            context.resources, R.drawable.reader_bg_yellow_edge
        )
        return BitmapDrawable(context.resources, bitmap).apply {
            setBounds(0, 0, w, h)
        }
    }

    private fun createBackgroundContent(w: Int, h: Int): Drawable {
        val bitmaps = arrayOf(
            BitmapFactory.decodeResource(context.resources, R.drawable.reader_bg_yellow1),
            BitmapFactory.decodeResource(context.resources, R.drawable.reader_bg_yellow2),
            BitmapFactory.decodeResource(context.resources, R.drawable.reader_bg_yellow3),
        )
        return BitmapDrawable(context.resources, bitmaps.random()).apply {
            setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
            setBounds(0, 0, w, h)
        }
    }
}
