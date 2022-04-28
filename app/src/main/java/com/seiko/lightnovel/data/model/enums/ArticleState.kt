package com.seiko.lightnovel.data.model.enums

enum class ArticleState {
    UnKnown, // 未知
    Update, // 连载中
    Full; // 已完结

    companion object
}

fun ArticleState.Companion.valueOfText(text: String?): ArticleState {
    return when (text) {
        "连载中" -> ArticleState.Update
        "已完结" -> ArticleState.Full
        else -> ArticleState.UnKnown
    }
}