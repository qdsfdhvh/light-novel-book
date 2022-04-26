package com.seiko.lightnovel.ui

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.seiko.lightnovel.component.koin.viewModel
import com.seiko.lightnovel.component.view.autoMeasure
import com.seiko.lightnovel.viewmodel.DetailViewModel
import org.koin.core.parameter.parametersOf

@SuppressLint("ViewConstructor")
class DetailLayout(context: Context, key: String) : BaseLayout(context) {

    private val title = TextView(context)

    private val viewModel: DetailViewModel by viewModel(parameters = {
        parametersOf(key)
    })

    init {
        title.text = "Detail"

        addView(title)

        viewModel.init()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        title.autoMeasure()
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        title.layoutCenter()
    }
}
