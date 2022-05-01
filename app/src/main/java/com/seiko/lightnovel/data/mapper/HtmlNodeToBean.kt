package com.seiko.lightnovel.data.mapper

import com.seiko.lightnovel.data.model.bean.Article
import com.seiko.lightnovel.data.model.bean.ArticleChapter
import com.seiko.lightnovel.data.model.bean.ArticleContent
import com.seiko.lightnovel.data.model.bean.ArticleDetail
import com.seiko.lightnovel.data.model.bean.ArticleVolume
import com.seiko.lightnovel.data.model.enums.ArticleLibrary
import com.seiko.lightnovel.data.model.enums.ArticleState
import com.seiko.lightnovel.data.model.enums.valueOfText
import com.seiko.lightnovel.data.model.enums.valueOfTitle
import com.seiko.lightnovel.util.HtmlNode

fun HtmlNode.toArticleList(): List<Article> {
    return select("#content td > div").map { node ->
        val a = node.select("b > a")
        val id = "(\\d+)".toRegex()
            .find(a.attr("href").substringAfterLast('/'))!!.value.toInt()
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

        Article(
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

fun HtmlNode.toArticleDetail(aid: Int): ArticleDetail {
    val content = select("#content")

    val tables = content.select("div > table")
    val table1 = tables.eq(0)
    val table2 = tables.eq(1)

    val title = table1.select("b").text()
    val cover = table2.select("img").attr("src")

    val info = table1.select("tr > td[width=\"20%\"]")
    val library = info.eq(0).text().substring(5).let { ArticleLibrary.valueOfTitle(it) }
    val author = info.eq(1).text().substring(5)
    val state = info.eq(2).text().substring(5).let { ArticleState.valueOfText(it) }
    val updateTime = info.eq(3).text().substring(5)
    val codeSize = info.eq(4).text().substring(5)

    val tags = table2.select("td > span.hottext")
        .eq(1).select("b").text().substringAfter('：').split(" ")
    val desc = table2.select("td > span[style=\"font-size:14px;\"]")
        .eq(1).text().substringAfter('：')

    val hasDrama = table2.select("td > span.hottext").eq(0).text().contains("动画化")

    return ArticleDetail(
        id = aid,
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

fun HtmlNode.toArticleVolumes(): List<ArticleVolume> {
    val content = select("table[border=\"0\"]")
    val trs = content.select("tr")

    val volumes = mutableListOf<ArticleVolume>()
    var chapters = mutableListOf<ArticleChapter>()
    trs.forEach { tr ->
        if (tr.child(0).hasAttr("vid")) {
            chapters = mutableListOf()
            volumes.add(
                ArticleVolume(
                    id = tr.child(0).attr("vid").toInt(),
                    title = tr.text(),
                    chapters = chapters,
                )
            )
        } else {
            chapters.addAll(
                tr.children()
                    .asSequence()
                    .filter { it.select("a").hasAttr("href") }
                    .map { td ->
                        ArticleChapter(
                            id = "(\\d+)".toRegex().find(td.child(0).attr("href"))
                            !!.value.toInt(),
                            title = td.text(),
                        )
                    }
            )
        }
    }
    return volumes
}

fun HtmlNode.toContentList(): List<ArticleContent> {
    val title = select("#title")
    val titles = if (title.isEmpty()) emptyList() else listOf(ArticleContent.Title(title.text()))

    val content = select("#content")
    val texts = content.textNodes().filterNot { it.isBlank }.map {
        ArticleContent.Text(it.text().trim())
    }
    val images = content.select(".divimage > a").filter { it.hasAttr("href") }.map {
        ArticleContent.Image(it.attr("href"))
    }
    return titles + texts + images
}
