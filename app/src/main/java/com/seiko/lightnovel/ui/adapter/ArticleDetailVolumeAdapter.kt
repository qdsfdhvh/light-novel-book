package com.seiko.lightnovel.ui.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seiko.lightnovel.component.view.CustomLayout
import com.seiko.lightnovel.component.view.autoMeasure
import com.seiko.lightnovel.component.view.dp
import com.seiko.lightnovel.data.model.ui.UiArticleDetail

class ArticleDetailVolumeAdapter(
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ArticleDetailVolumeViewHolder>() {

    var volumes: List<UiArticleDetail.Volume> = emptyList()
        set(value) {
            field = value
            notifyItemRangeInserted(0, value.size)
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleDetailVolumeViewHolder {
        val layout = ArticleDetailVolumeLayout(parent.context)
        val holder = ArticleDetailVolumeViewHolder(layout)
        holder.layout.setOnClickListener {
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                itemClickListener.onItemClick(volumes[position])
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ArticleDetailVolumeViewHolder, position: Int) {
        holder.layout.update(volumes[position])
    }

    override fun getItemCount(): Int {
        return volumes.size
    }

    fun interface OnItemClickListener {
        fun onItemClick(item: UiArticleDetail.Volume)
    }
}

class ArticleDetailVolumeLayout(context: Context) : CustomLayout(context) {

    private val titleView = TextView(context)

    init {
        addView(titleView)

        layoutParams = LayoutParams(MATCH_PARENT, 50.dp)
    }

    fun update(state: UiArticleDetail.Volume) {
        titleView.text = state.title
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
        titleView.autoMeasure()
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        titleView.layout(0, 0)
    }
}

class ArticleDetailVolumeViewHolder(
    val layout: ArticleDetailVolumeLayout,
) : RecyclerView.ViewHolder(layout)
