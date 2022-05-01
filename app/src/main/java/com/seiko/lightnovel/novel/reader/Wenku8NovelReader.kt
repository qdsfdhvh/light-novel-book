package com.seiko.lightnovel.novel.reader

import com.seiko.lightnovel.data.model.bean.ArticleContent
import com.seiko.lightnovel.datasource.Wenku8DataSource

class Wenku8NovelReader(
    private val aid: Int,
    private val vid: Int,
    private val dataSource: Wenku8DataSource,
) : NovelReader {

    override suspend fun getVolumeList(): List<NovelChapter> {
        val volume = dataSource.getDetailVolumes(aid).find { it.id == vid } ?: return emptyList()
        return volume.chapters.map { chapter ->
            NovelChapter(
                key = chapter.id,
                title = chapter.title,
            )
        }
    }

    override suspend fun getContentList(chapter: NovelChapter): List<NovelContent> {
        return dataSource.getContentList(aid, chapter.key as Int).map { content ->
            when (content) {
                is ArticleContent.Title -> NovelContent.Title(content.title)
                is ArticleContent.Text -> NovelContent.Text(content.text)
                is ArticleContent.Image -> NovelContent.Text(content.url)
            }
        }
    }
}
