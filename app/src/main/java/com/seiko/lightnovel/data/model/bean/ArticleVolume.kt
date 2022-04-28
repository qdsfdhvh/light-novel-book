package com.seiko.lightnovel.data.model.bean

data class ArticleVolume(
    val id: Int,
    val title: String,
    val chapters: List<ArticleChapter>,
)
