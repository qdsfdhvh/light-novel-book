package com.seiko.lightnovel.ui.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.seiko.lightnovel.novel.paging.PageChapter
import com.seiko.lightnovel.novel.paging.PageContent
import kotlin.math.min

class ReaderAdapter : PagingDataAdapter<PageChapter, ReaderViewHolder>(PageContentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReaderViewHolder {
        val layout = ReaderItemView(parent.context)
        return ReaderViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ReaderViewHolder, position: Int) {
        holder.readerView.update(requireNotNull(getItem(position)))
    }
}

class ReaderItemView(context: Context) : View(context) {

    private var pageContent: PageChapter? = null
    private val paint = Paint()

    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
        )
    }

    fun update(content: PageChapter) {
        this.pageContent = content
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas) {
        pageContent?.contents?.forEach { content ->
            when (content) {
                is PageContent.Text -> {
                    paint.textSize = content.textSize
                    paint.color = content.textColor
                    canvas.drawMultiText(
                        content.text,
                        0f,
                        content.y,
                        paint
                    )
                }
                is PageContent.Title -> {
                    paint.textSize = content.titleSize
                    paint.color = content.titleColor
                    canvas.drawMultiText(
                        content.title,
                        0f,
                        content.y,
                        paint
                    )
                }
                is PageContent.Image -> Unit
            }
        }
    }

    private val measuredWidthArray = FloatArray(1)

    private fun Canvas.drawMultiText(text: String, x: Float, y: Float, paint: Paint) {
        val textWidth = paint.measureText(text)
        if (textWidth <= measuredWidth) {
            drawText(text, x, y, paint)
            return
        }

        var start = 0
        var topY = y
        val textSize = text.length
        while (start < textSize) {
            val breakTextCount = paint.breakText(
                text,
                true,
                measuredWidth.toFloat(),
                measuredWidthArray,
            )
            drawText(
                text,
                start, min(textSize, start + breakTextCount),
                0f, topY,
                paint,
            )
            start += breakTextCount
            topY += paint.textSize
        }
    }
}

class ReaderViewHolder(val readerView: ReaderItemView) : RecyclerView.ViewHolder(readerView)

class PageContentDiffCallback : DiffUtil.ItemCallback<PageChapter>() {

    override fun areItemsTheSame(oldItem: PageChapter, newItem: PageChapter): Boolean {
        return oldItem.chapterKey == newItem.chapterKey &&
            oldItem.chapterPage == newItem.chapterPage
    }

    override fun areContentsTheSame(oldItem: PageChapter, newItem: PageChapter): Boolean {
        return oldItem == newItem
    }
}
