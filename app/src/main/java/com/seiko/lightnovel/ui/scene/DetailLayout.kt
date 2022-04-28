package com.seiko.lightnovel.ui.scene

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.seiko.lightnovel.component.koin.viewModel
import com.seiko.lightnovel.ui.adapter.ArticleDetailAdapter
import com.seiko.lightnovel.ui.adapter.ArticleDetailVolumeAdapter
import com.seiko.lightnovel.viewmodel.DetailViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.parameter.parametersOf

@SuppressLint("ViewConstructor")
class DetailLayout(context: Context, aid: Int) : BaseListLayout(context) {

    private val viewModel: DetailViewModel by viewModel(parameters = {
        parametersOf(aid)
    })

    init {
        val detailAdapter = ArticleDetailAdapter {

        }
        val volumeAdapter = ArticleDetailVolumeAdapter {
            Toast.makeText(context.applicationContext, it.title, Toast.LENGTH_SHORT).show()
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ConcatAdapter(detailAdapter, volumeAdapter)

        viewModel.bookDetail
            .flowWithLifecycle(lifecycle)
            .onEach { detail ->
                detailAdapter.detail = detail
                volumeAdapter.volumes = detail.volumes
            }
            .launchIn(lifecycleScope)
    }
}
