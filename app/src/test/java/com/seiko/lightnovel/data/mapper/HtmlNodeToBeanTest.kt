package com.seiko.lightnovel.data.mapper

import com.seiko.lightnovel.data.model.enums.ArticleLibrary
import com.seiko.lightnovel.data.model.enums.ArticleState
import com.seiko.lightnovel.util.JsoupUtils
import org.junit.Test
import java.io.File
import java.nio.charset.Charset

class HtmlNodeToBeanTest {

    @Test
    fun testArticleList() {
        val file = File("src/test/resources/wenku8/getTopList.html")
        val document = JsoupUtils.parse(file, Charset.forName("GBK"))
        val list = document.toArticleList()
        assert(list.size == 20)
    }

    @Test
    fun testArticleDetail() {
        val file = File("src/test/resources/wenku8/getDetail.html")
        val document = JsoupUtils.parse(file, Charset.forName("GBK"))
        val detail = document.toArticleDetail(1100)
        assert(detail.title == "女性向游戏世界对路人角色很不友好(乙女游戏世界对路人角色很不友好)")
        assert(detail.cover == "https://img.wenku8.com/image/2/2638/2638s.jpg")
        assert(detail.author == "三嶋与梦")
        assert(detail.library == ArticleLibrary.Other)
        assert(detail.tags.isNotEmpty())
        assert(detail.desc.startsWith("原本是现代日本的社会人"))
        assert(detail.updateTime == "2022-04-27")
        assert(detail.codeSize == "1617039字")
        assert(detail.state == ArticleState.Update)
        assert(detail.hasDrama)
    }

    @Test
    fun testArticleDetailVolumes() {
        val file = File("src/test/resources/wenku8/getDetailVolumes.html")
        val document = JsoupUtils.parse(file, Charset.forName("GBK"))
        val volumes = document.toArticleVolumes()
        assert(volumes.size == 10)
        assert(volumes.first().id == 101127)
        assert(volumes.first().title == "第一卷")
        assert(volumes.first().chapters.size == 20)
        assert(volumes.first().chapters.first().id == 101128)
        assert(volumes.first().chapters.first().title == "序章")
        assert(volumes.last().title == "第九卷")
        assert(volumes.last().chapters.size == 13)
    }
}