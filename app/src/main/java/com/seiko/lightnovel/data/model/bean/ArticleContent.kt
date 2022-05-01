package com.seiko.lightnovel.data.model.bean

sealed class ArticleContent {
    data class Title(val title: String) : ArticleContent()
    data class Text(val text: String) : ArticleContent()
    data class Image(val url: String) : ArticleContent()
}
