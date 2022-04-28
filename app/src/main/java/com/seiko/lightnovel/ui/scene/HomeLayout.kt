package com.seiko.lightnovel.ui.scene

import android.content.Context
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.seiko.lightnovel.component.koin.viewModel
import com.seiko.lightnovel.ui.adapter.ArticleListAdapter
import com.seiko.lightnovel.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeLayout(context: Context) : BaseListLayout(context) {

    private val viewModel: HomeViewModel by viewModel()

    init {
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
}
