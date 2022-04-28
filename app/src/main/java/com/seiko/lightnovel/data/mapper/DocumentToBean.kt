package com.seiko.lightnovel.data.mapper

import com.seiko.lightnovel.data.model.bean.ArticleBean
import com.seiko.lightnovel.data.model.enums.ArticleLibrary
import com.seiko.lightnovel.data.model.enums.ArticleState
import com.seiko.lightnovel.data.model.enums.valueOfText
import com.seiko.lightnovel.data.model.enums.valueOfTitle
import org.jsoup.nodes.Document

private val String.aid: Int
    get() = "(\\d+)".toRegex().find(substringAfterLast('/'))?.value?.toIntOrNull() ?: 0

fun Document.toArticleList(): List<ArticleBean> {
    return select("#content td > div").map { node ->
        val a = node.select("b > a")
        val id = a.attr("href").aid
        val title = a.attr("title")

        val cover = node.select("img").attr("src")

        val ps = node.select("p")

        val p0 = ps[0].text().split("/")
        val author = p0.find { it.startsWith("作者:") }?.substring(3).orEmpty()
        val library = p0.find { it.startsWith("分类:") }?.substring(3).let { ArticleLibrary.valueOfTitle(it) }

        val p1 = ps[1].text().split("/")
        val updateTime = p1.find { it.startsWith("更新:") }?.substring(3).orEmpty()
        val codeSize = p1.find { it.startsWith("字数:") }?.substring(3).orEmpty()
        val state = ArticleState.valueOfText(p1.getOrNull(2))
        val hasDrama = p1.any { it.contains("动画化") }

        val tags = ps[2].child(0).text().split(" ")

        val desc = ps[3].text()

        ArticleBean(
            id = id,
            title = title,
            cover = cover,
            author = author,
            library = library,
            tags = tags,
            desc = desc,
            updateTime = updateTime,
            codeSize = codeSize,
            state = state,
            hasDrama = hasDrama,
        )
    }
}
