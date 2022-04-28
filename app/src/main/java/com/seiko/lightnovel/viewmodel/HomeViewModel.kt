package com.seiko.lightnovel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.seiko.lightnovel.data.model.enums.ArticleSort
import com.seiko.lightnovel.data.model.ui.UiArticle
import com.seiko.lightnovel.datasource.Wenku8DataSource
import com.seiko.lightnovel.datasource.paging.TopListPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class HomeViewModel(
    private val dataSource: Wenku8DataSource,
) : ViewModel() {

    val topPagingList = Pager(config = PagingConfig(pageSize = 20)) {
        TopListPagingSource(dataSource, ArticleSort.LastUpdate)
    }.flow.map { list ->
        list.map { item ->
            UiArticle(
                id = item.id,
                title = item.title,
                cover = item.cover,
            )
        }
    }.flowOn(Dispatchers.IO).cachedIn(viewModelScope)

}
