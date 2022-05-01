package com.seiko.lightnovel.novel.reader

data class NovelVolume(
    val title: String,
    val key: Any,
)

sealed class NovelChapter {
    data class Title(
        val value: String,
    ) : NovelChapter()

    data class Content(
        val value: String,
    ) : NovelChapter()

    data class Image(
        val url: String,
    ) : NovelChapter()
}
