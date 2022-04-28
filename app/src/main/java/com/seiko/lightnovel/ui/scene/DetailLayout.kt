package com.seiko.lightnovel.ui.scene

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.seiko.lightnovel.component.koin.viewModel
import com.seiko.lightnovel.component.view.autoMeasure
import com.seiko.lightnovel.viewmodel.DetailViewModel
import org.koin.core.parameter.parametersOf

@SuppressLint("ViewConstructor")
class DetailLayout(context: Context, aid: Int) : BaseLayout(context) {

    private val title = TextView(context)

    private val viewModel: DetailViewModel by viewModel(parameters = {
        parametersOf(aid)
    })

    init {
        title.text = "Detail $aid"

        addView(title)

        lifecycleScope.launchWhenResumed {
            viewModel.init()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        title.autoMeasure()
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        title.layoutCenter()
    }
}
