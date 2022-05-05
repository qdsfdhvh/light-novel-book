package com.seiko.lightnovel.ui.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.seiko.lightnovel.novel.paging.NovelPagingData

class ReaderAdapter : PagingDataAdapter<NovelPagingData, RecyclerView.ViewHolder>(PageContentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        return when (viewType) {
            itemTypeText -> {
                val itemTextView = ItemTextView(context)
                ItemTextViewHolder(itemTextView)
            }
            itemTypeImage -> {
                val itemImageView = ItemImageView(context)
                ItemImageViewHolder(itemImageView)
            }
            itemTypeTip -> {
                val textView = TextView(context)
                ItemTipViewHolder(textView)
            }
            else -> throw RuntimeException("unknown viewType=$viewType in ReaderAdapter")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val data = getItem(position)) {
            is NovelPagingData.Text -> {
                (holder as ItemTextViewHolder).itemTextView.update(data)
            }
            is NovelPagingData.Image -> {
                (holder as ItemImageViewHolder).itemImageView.update(data)
            }
            is NovelPagingData.Tip -> {
                (holder as ItemTipViewHolder).textView.text = data.text
            }
            else -> Unit
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is NovelPagingData.Text -> itemTypeText
            is NovelPagingData.Image -> itemTypeImage
            else -> itemTypeTip
        }
    }

    companion object {
        private const val itemTypeTip = 0
        private const val itemTypeText = 1
        private const val itemTypeImage = 2
    }
}

class ItemTextView(context: Context) : View(context) {

    private var pagingData: NovelPagingData.Text? = null
    private val paint = Paint()

    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
        )
    }

    fun update(page: NovelPagingData.Text) {
        pagingData = page
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas) {
        pagingData?.contents?.forEach { content ->
            paint.textSize = content.textSize
            paint.color = content.textColor
            canvas.drawText(
                content.text,
                content.x,
                content.y,
                paint,
            )
        }
    }
}

class ItemImageView(context: Context) : ImageView(context) {
    fun update(data: NovelPagingData.Image) {
        load(data.url)
    }
}

class ItemTextViewHolder(val itemTextView: ItemTextView) : RecyclerView.ViewHolder(itemTextView)
class ItemImageViewHolder(val itemImageView: ItemImageView) : RecyclerView.ViewHolder(itemImageView)
class ItemTipViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

private class PageContentDiffCallback : DiffUtil.ItemCallback<NovelPagingData>() {

    override fun areItemsTheSame(oldItem: NovelPagingData, newItem: NovelPagingData): Boolean {
        return oldItem.key == newItem.key
    }

    override fun areContentsTheSame(oldItem: NovelPagingData, newItem: NovelPagingData): Boolean {
        return oldItem == newItem
    }
}
