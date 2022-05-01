package com.seiko.lightnovel.novel.reader

data class NovelChapter(
    val title: String,
    val key: Any,
)

sealed class NovelContent {
    data class Title(val title: String) : NovelContent()
    data class Text(val text: String) : NovelContent()
    data class Image(val url: String) : NovelContent()
}
