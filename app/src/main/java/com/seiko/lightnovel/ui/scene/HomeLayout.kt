package com.seiko.lightnovel.ui.scene

import android.content.Context
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seiko.lightnovel.component.koin.viewModel
import com.seiko.lightnovel.component.view.autoMeasure
import com.seiko.lightnovel.ui.adapter.ArticleListAdapter
import com.seiko.lightnovel.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeLayout(context: Context) : BaseLayout(context) {

    private val recyclerView = RecyclerView(context)

    private val viewModel: HomeViewModel by viewModel()

    init {
        addView(recyclerView)

        val adapter = ArticleListAdapter { article ->
            navController.navigate("detail/${article.id}")
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        viewModel.topPagingList
            .flowWithLifecycle(lifecycle)
            .onEach { adapter.submitData(it) }
            .launchIn(lifecycleScope)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        recyclerView.autoMeasure()
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        recyclerView.layout(0, 0)
    }
}
