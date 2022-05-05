package com.seiko.lightnovel.ui.scene

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.seiko.lightnovel.component.insets.doOnApplyWindowInsets
import com.seiko.lightnovel.component.koin.viewModel
import com.seiko.lightnovel.component.view.dp
import com.seiko.lightnovel.component.view.sp
import com.seiko.lightnovel.novel.view.ReaderConfig
import com.seiko.lightnovel.ui.adapter.ReaderAdapter
import com.seiko.lightnovel.ui.widget.viewpager.ViewPagerLayoutManager
import com.seiko.lightnovel.util.observer
import com.seiko.lightnovel.viewmodel.ReaderWenku8ViewModel
import org.koin.core.parameter.parametersOf

@SuppressLint("ViewConstructor")
class ReaderWenku8Layout(context: Context, aid: Int, vid: Int) : BaseListLayout(context) {

    private val viewModel: ReaderWenku8ViewModel by viewModel(parameters = {
        parametersOf(aid, vid)
    })

    init {
        setupWindowInsets()

        val adapter = ReaderAdapter()

        recyclerView.layoutManager = ViewPagerLayoutManager(context)
        recyclerView.adapter = adapter

        viewModel.novelPageData.observer {
            adapter.submitData(it)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w == 0 || h == 0) return
        val config = ReaderConfig(
            viewWidth = w,
            viewHeight = h,
            titleSize = 16.sp,
            textSize = 20.sp,
            widgetSize = 12.sp,
            lineSpace = 10.sp,
            paddingTop = recyclerView.paddingTop,
            paddingBottom = recyclerView.paddingBottom,
            paddingLeft = recyclerView.paddingLeft + 10.dp,
            paddingRight = recyclerView.paddingRight + 10.dp,
        )
        viewModel.updateReaderConfig(config)
    }

    private fun setupWindowInsets() {
        doOnApplyWindowInsets { windowInsets, padding, _ ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            recyclerView.updatePadding(
                top = padding.top + insets.top,
                bottom = padding.bottom + insets.bottom
            )
        }
    }
}
