package com.seiko.lightnovel.ui.scene

import android.annotation.SuppressLint
import android.content.Context
import com.seiko.lightnovel.component.view.autoMeasure
import com.seiko.lightnovel.ui.widget.BookBackgroundView
import com.seiko.lightnovel.ui.widget.BookReaderView

@SuppressLint("ViewConstructor")
class ReaderLayout(context: Context, vid: Int) : BaseLayout(context) {

    private val bookBackgroundView = BookBackgroundView(context)
    private val bookReaderView = BookReaderView(context)

    init {
        addView(bookBackgroundView, LayoutParams(MATCH_PARENT, MATCH_PARENT))
        addView(bookReaderView, LayoutParams(MATCH_PARENT, MATCH_PARENT))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        bookBackgroundView.autoMeasure()
        bookReaderView.autoMeasure()
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        bookBackgroundView.layout(0, 0)
        bookReaderView.layout(0, 0)
    }
}