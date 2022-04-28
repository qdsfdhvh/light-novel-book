package com.seiko.lightnovel.ui.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.seiko.lightnovel.component.view.CustomLayout
import com.seiko.lightnovel.component.view.autoMeasure
import com.seiko.lightnovel.component.view.dp
import com.seiko.lightnovel.component.view.toExactlyMeasureSpec
import com.seiko.lightnovel.data.model.ui.UiArticle

class ArticleListAdapter : ListAdapter<UiArticle, ArticleListViewHolder>(ArticleListDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleListViewHolder {
        val layout = ArticleItemLayout(parent.context)
        return ArticleListViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ArticleListViewHolder, position: Int) {
        holder.layout.update(getItem(position))
    }
}

class ArticleItemLayout(context: Context) : CustomLayout(context) {

    private val coverView = ImageView(context)
    private val titleView = TextView(context)

    init {
        addView(coverView, LayoutParams(WRAP_CONTENT, MATCH_PARENT))
        addView(titleView)

        layoutParams = LayoutParams(MATCH_PARENT, 150.dp)
    }

    fun update(state: UiArticle) {
        // TODO
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

class ArticleListViewHolder(val layout: ArticleItemLayout) : RecyclerView.ViewHolder(layout)

class ArticleListDiffCallback : DiffUtil.ItemCallback<UiArticle>() {
    override fun areItemsTheSame(oldItem: UiArticle, newItem: UiArticle): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UiArticle, newItem: UiArticle): Boolean {
        return oldItem == newItem
    }
}
