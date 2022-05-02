package com.seiko.lightnovel.ui.scene

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.seiko.lightnovel.component.koin.viewModel
import com.seiko.lightnovel.component.loading.GlobalLoader
import com.seiko.lightnovel.component.loading.LoadingState
import com.seiko.lightnovel.route.Route
import com.seiko.lightnovel.ui.adapter.ArticleDetailAdapter
import com.seiko.lightnovel.ui.adapter.ArticleDetailVolumeAdapter
import com.seiko.lightnovel.util.observer
import com.seiko.lightnovel.viewmodel.DetailState
import com.seiko.lightnovel.viewmodel.DetailViewModel
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

@SuppressLint("ViewConstructor")
class DetailLayout(context: Context, private val aid: Int) : BaseListLayout(context) {

    private val globalLoader: GlobalLoader by inject()

    private val viewModel: DetailViewModel by viewModel(parameters = {
        parametersOf(aid)
    })

    init {
        val detailAdapter = ArticleDetailAdapter {
        }
        val volumeAdapter = ArticleDetailVolumeAdapter {
            navController.navigate(Route.Reader.Wenku8(aid, it.id))
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ConcatAdapter(detailAdapter, volumeAdapter)

        viewModel.bookDetail.observer { state ->
            when (state) {
                DetailState.Loading -> globalLoader.showState(LoadingState.Loading)
                is DetailState.Success -> {
                    globalLoader.showState(LoadingState.Success)
                    detailAdapter.detail = state.detail
                    volumeAdapter.volumes = state.detail.volumes
                }
            }
        }
    }
}
