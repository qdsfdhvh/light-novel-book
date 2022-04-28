package com.seiko.lightnovel.ui.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.seiko.lightnovel.component.view.CustomLayout
import com.seiko.lightnovel.component.view.autoMeasure
import com.seiko.lightnovel.component.view.dp
import com.seiko.lightnovel.component.view.toExactlyMeasureSpec
import com.seiko.lightnovel.data.model.ui.UiArticleDetail

class ArticleDetailAdapter(
    private val itemClickListener: OnItemClickListener,
) : RecyclerView.Adapter<ArticleDetailViewHolder>() {

    var detail: UiArticleDetail = UiArticleDetail.Empty
        set(value) {
            field = value
            notifyItemChanged(0)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleDetailViewHolder {
        val layout = ArticleDetailLayout(parent.context)
        val holder = ArticleDetailViewHolder(layout)
        holder.layout.setOnClickListener {
            itemClickListener.onClick(detail)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ArticleDetailViewHolder, position: Int) {
        holder.layout.update(detail)
    }

    override fun getItemCount(): Int {
        return 1
    }

    fun interface OnItemClickListener {
        fun onClick(detail: UiArticleDetail)
    }
}

class ArticleDetailLayout(context: Context) : CustomLayout(context) {

    private val coverView = ImageView(context)
    private val titleView = TextView(context)

    init {
        addView(coverView, LayoutParams(WRAP_CONTENT, MATCH_PARENT))
        addView(titleView)

        layoutParams = LayoutParams(MATCH_PARENT, 150.dp)
    }

    fun update(state: UiArticleDetail) {
        coverView.load(state.cover)
        titleView.text = state.title
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val parentWidth = MeasureSpec.getSize(widthMeasureSpec)
        val parentHeight = MeasureSpec.getSize(heightMeasureSpec)

        val coverWidth = (parentHeight * (9 / 13f)).toInt()
        coverView.autoMeasure(
            widthMeasureSpec = coverWidth.toExactlyMeasureSpec(),
            heightMeasureSpec = parentHeight.toExactlyMeasureSpec(),
        )

        val titleWidth = parentWidth - coverWidth
        titleView.autoMeasure(
            widthMeasureSpec = titleWidth.toExactlyMeasureSpec(),
        )

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        coverView.layout(0, 0)
        titleView.layout(coverView.measuredWidth, 0)
    }
}

class ArticleDetailViewHolder(
    val layout: ArticleDetailLayout,
) : RecyclerView.ViewHolder(layout)
