package com.seiko.lightnovel.ui.scene

import android.content.Context
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.seiko.lightnovel.component.insets.doOnApplyWindowInsets
import com.seiko.lightnovel.component.koin.viewModel
import com.seiko.lightnovel.component.loading.GlobalLoader
import com.seiko.lightnovel.component.loading.LoadingState
import com.seiko.lightnovel.route.Route
import com.seiko.lightnovel.ui.adapter.ArticleListAdapter
import com.seiko.lightnovel.util.observer
import com.seiko.lightnovel.viewmodel.HomeViewModel
import org.koin.core.component.inject

class HomeLayout(context: Context) : BaseListLayout(context) {

    private val globalLoader: GlobalLoader by inject()

    private val viewModel: HomeViewModel by viewModel()

    init {
        setupWindowInsets()

        val adapter = ArticleListAdapter { article ->
            navController.navigate(Route.Detail(article.id))
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        recyclerView.clipToPadding = false

        viewModel.topPagingList.observer {
            adapter.submitData(it)
        }
        adapter.loadStateFlow.observer {
            val state = when (it.refresh) {
                LoadState.Loading -> LoadingState.Loading
                is LoadState.NotLoading -> LoadingState.Success
                is LoadState.Error -> LoadingState.Failure
            }
            globalLoader.showState(state)
        }
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
