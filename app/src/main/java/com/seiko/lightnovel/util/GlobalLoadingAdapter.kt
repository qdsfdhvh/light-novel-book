package com.seiko.lightnovel.util

import android.content.Context
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import com.seiko.lightnovel.component.loading.GlobalLoader
import com.seiko.lightnovel.component.loading.LoadingState
import com.seiko.lightnovel.component.view.CustomLayout
import com.seiko.lightnovel.component.view.autoMeasure

class GlobalLoadingAdapter : GlobalLoader.Adapter {
    override fun showState(convertView: ViewGroup, state: LoadingState) {
        val loadView = convertView.findOrCreateLoadingView()
        loadView.setState(state)
    }
}

private const val LoadingViewTag = "LoadingViewTag"

private fun ViewGroup.findOrCreateLoadingView(): LoadingView {
    var loadView = findViewWithTag<LoadingView>(LoadingViewTag)
    if (loadView == null) {
        loadView = LoadingView(context).apply {
            tag = LoadingViewTag
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
        }
        addView(loadView)
    }
    return loadView
}

private class LoadingView(context: Context) : CustomLayout(context) {

    private val progressBar = ProgressBar(context)

    init {
        addView(progressBar)
    }

    private var loadingState = LoadingState.Success

    fun setState(state: LoadingState) {
        loadingState = state
        progressBar.isVisible = state === LoadingState.Loading
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        when (loadingState) {
            LoadingState.Success -> Unit
            LoadingState.Loading -> progressBar.autoMeasure()
            LoadingState.Failure -> Unit
            LoadingState.Empty -> Unit
        }
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        when (loadingState) {
            LoadingState.Success -> Unit
            LoadingState.Loading -> progressBar.layoutCenter()
            LoadingState.Failure -> Unit
            LoadingState.Empty -> Unit
        }
    }
}
