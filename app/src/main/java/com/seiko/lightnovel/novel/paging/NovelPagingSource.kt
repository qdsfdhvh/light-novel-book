package com.seiko.lightnovel.novel.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.seiko.lightnovel.novel.reader.NovelChapter
import com.seiko.lightnovel.novel.reader.NovelReader
import com.seiko.lightnovel.novel.view.ReaderConfig

class NovelPagingSource(
    private val reader: NovelReader,
    private val config: ReaderConfig,
) : PagingSource<Int, PageChapter>() {

    private var _chapters: List<NovelChapter>? = null

    private suspend fun getChapters(): List<NovelChapter> {
        if (_chapters != null) return _chapters!!
        _chapters = reader.getChapterList()
        return _chapters!!
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PageChapter> {
        val page = params.key ?: 0
        val chapters = getChapters()
        return try {
            val chapter = chapters.getOrNull(page)
            val pageContentList = if (chapter != null) {
                reader.getContentList(chapter).toPageContents(chapter, config)
            } else emptyList()
            LoadResult.Page(
                data = pageContentList,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (pageContentList.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PageChapter>): Int? {
        return null
    }
}
