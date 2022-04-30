package com.seiko.lightnovel.ui.scene

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.seiko.lightnovel.component.view.autoMeasure

abstract class BaseListLayout(context: Context) : BaseLayout(context) {

    protected val recyclerView = RecyclerView(context).apply {
        this@BaseListLayout.addView(this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        recyclerView.autoMeasure()
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        recyclerView.layout(0, 0)
    }
}
