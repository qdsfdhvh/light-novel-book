package com.seiko.lightnovel.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seiko.lightnovel.data.model.ui.UiArticleDetail
import com.seiko.lightnovel.datasource.Wenku8DataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

class DetailViewModel(
    private val aid: Int,
    private val dataSource: Wenku8DataSource,
) : ViewModel() {

    val bookDetail = combine(
        flow { emit(dataSource.getDetail(aid)) },
        flow { emit(dataSource.getDetailVolumes(aid)) },
    ) { detail, volumes ->
        UiArticleDetail.of(detail, volumes)
    }.flowOn(Dispatchers.IO).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = UiArticleDetail.Empty,
    )

    override fun onCleared() {
        super.onCleared()
        Log.d("DetailViewModel", "onCleared $aid")
    }
}
