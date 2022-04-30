package com.seiko.lightnovel.data.model.ui

import com.seiko.lightnovel.data.model.bean.ArticleDetail
import com.seiko.lightnovel.data.model.bean.ArticleVolume
import com.seiko.lightnovel.data.model.enums.ArticleLibrary
import com.seiko.lightnovel.data.model.enums.ArticleState

data class UiArticleDetail(
    val id: Int,
    val title: String,
    val cover: String,
    val author: String,
    val library: ArticleLibrary,
    val tags: List<String>,
    val desc: String,
    val updateTime: String,
    val codeSize: String,
    val state: ArticleState,
    val hasDrama: Boolean,
    val volumes: List<Volume>,
) {
    data class Volume(
        val id: Int,
        val title: String,
        val chapters: List<Chapter>
    )

    data class Chapter(
        val id: Int,
        val title: String,
    )

    companion object {
        fun of(detail: ArticleDetail, volumes: List<ArticleVolume>): UiArticleDetail {
            return UiArticleDetail(
                id = detail.id,
                title = detail.title,
                cover = detail.cover,
                author = detail.author,
                library = detail.library,
                tags = detail.tags,
                desc = detail.desc,
                updateTime = detail.updateTime,
                codeSize = detail.codeSize,
                state = detail.state,
                hasDrama = detail.hasDrama,
                volumes = volumes.map { volume ->
                    Volume(
                        id = volume.id,
                        title = volume.title,
                        chapters = volume.chapters.map { chapter ->
                            Chapter(
                                id = chapter.id,
                                title = chapter.title,
                            )
                        }
                    )
                },
            )
        }

        val Empty = UiArticleDetail(
            id = 0,
            title = "",
            cover = "",
            author = "",
            library = ArticleLibrary.Unknown,
            tags = emptyList(),
            desc = "",
            updateTime = "",
            codeSize = "",
            state = ArticleState.UnKnown,
            hasDrama = false,
            volumes = emptyList(),
        )
    }
}
