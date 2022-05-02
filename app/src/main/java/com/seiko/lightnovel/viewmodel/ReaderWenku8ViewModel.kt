package com.seiko.lightnovel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.seiko.lightnovel.datasource.Wenku8DataSource
import com.seiko.lightnovel.novel.paging.NovelPagingSource
import com.seiko.lightnovel.novel.reader.Wenku8NovelReader
import com.seiko.lightnovel.novel.view.ReaderConfig
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest

class ReaderWenku8ViewModel(
    aid: Int,
    vid: Int,
    dataSource: Wenku8DataSource,
) : ViewModel() {

    private val reader = Wenku8NovelReader(
        aid = aid,
        vid = vid,
        dataSource = dataSource,
    )

    private var readerConfigFlow = MutableSharedFlow<ReaderConfig>(extraBufferCapacity = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val novelPageData = readerConfigFlow.flatMapLatest { config ->
        Pager(config = PagingConfig(20)) {
            NovelPagingSource(reader, config)
        }.flow.cachedIn(viewModelScope)
    }

    fun updateReaderConfig(config: ReaderConfig) {
        readerConfigFlow.tryEmit(config)
    }
}
