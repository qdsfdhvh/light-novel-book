package com.seiko.lightnovel.datasource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.seiko.lightnovel.data.model.bean.ArticleBean
import com.seiko.lightnovel.data.model.enums.ArticleSort
import com.seiko.lightnovel.datasource.Wenku8DataSource

class TopListPagingSource(
    private val dataSource: Wenku8DataSource,
    private val articleSort: ArticleSort,
) : PagingSource<Int, ArticleBean>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleBean> {
        val page = params.key ?: 1
        return try {
            val list = dataSource.getTopList(articleSort, page)
            LoadResult.Page(
                data = list,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (list.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleBean>): Int? {
        return null
    }
}