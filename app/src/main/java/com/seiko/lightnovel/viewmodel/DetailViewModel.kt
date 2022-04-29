package com.seiko.lightnovel.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seiko.lightnovel.data.model.ui.UiArticleDetail
import com.seiko.lightnovel.datasource.Wenku8DataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

class DetailViewModel(
    private val aid: Int,
    private val dataSource: Wenku8DataSource,
) : ViewModel() {

    val bookDetail: StateFlow<DetailState> = combine(
        flow { emit(dataSource.getDetail(aid)) },
        flow { emit(dataSource.getDetailVolumes(aid)) },
    ) { detail, volumes ->
        val uiDetail = UiArticleDetail.of(detail, volumes)
        DetailState.Success(uiDetail)
    }.flowOn(Dispatchers.IO).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = DetailState.Loading,
    )

    override fun onCleared() {
        super.onCleared()
        Log.d("DetailViewModel", "onCleared $aid")
    }
}

sealed class DetailState {
    object Loading : DetailState()
    data class Success(val detail: UiArticleDetail) : DetailState()
}
