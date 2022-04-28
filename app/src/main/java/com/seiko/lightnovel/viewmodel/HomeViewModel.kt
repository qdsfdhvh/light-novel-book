package com.seiko.lightnovel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seiko.lightnovel.data.model.enums.ArticleSort
import com.seiko.lightnovel.data.model.ui.UiArticle
import com.seiko.lightnovel.datasource.Wenku8DataSource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val dataSource: Wenku8DataSource,
) : ViewModel() {

    val topList = flow {
        val list = dataSource.getTopList(ArticleSort.LastUpdate, 1).map { item ->
            UiArticle(
                id = item.id,
                title = item.title,
                cover = item.cover,
            )
        }
        emit(list)
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

}
