package com.seiko.lightnovel.data.model.bean

import com.seiko.lightnovel.data.model.enums.ArticleLibrary
import com.seiko.lightnovel.data.model.enums.ArticleState

data class ArticleDetail(
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
)