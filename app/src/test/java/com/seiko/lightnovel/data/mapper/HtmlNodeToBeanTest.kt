package com.seiko.lightnovel.data.mapper

import com.seiko.lightnovel.data.model.enums.ArticleLibrary
import com.seiko.lightnovel.data.model.enums.ArticleState
import com.seiko.lightnovel.util.JsoupUtils
import java.io.File
import java.nio.charset.Charset
import kotlin.test.Test
import kotlin.test.assertEquals

class HtmlNodeToBeanTest {

    @Test
    fun testArticleList() {
        val file = File("src/test/resources/wenku8/getTopList.html")
        val document = JsoupUtils.parse(file, Charset.forName("GBK"))
        val list = document.toArticleList()
        assertEquals(list.size, 20)
    }

    @Test
    fun testArticleDetail() {
        val file = File("src/test/resources/wenku8/getDetail.html")
        val document = JsoupUtils.parse(file, Charset.forName("GBK"))
        val detail = document.toArticleDetail(1100)
        assertEquals(detail.title, "女性向游戏世界对路人角色很不友好(乙女游戏世界对路人角色很不友好)")
        assertEquals(detail.cover, "https://img.wenku8.com/image/2/2638/2638s.jpg")
        assertEquals(detail.author, "三嶋与梦")
        assertEquals(detail.library, ArticleLibrary.Other)
        assert(detail.tags.isNotEmpty())
        assert(detail.desc.startsWith("原本是现代日本的社会人"))
        assertEquals(detail.updateTime, "2022-04-27")
        assertEquals(detail.codeSize, "1617039字")
        assertEquals(detail.state, ArticleState.Update)
        assert(detail.hasDrama)
    }

    @Test
    fun testArticleDetailVolumes() {
        val file = File("src/test/resources/wenku8/getDetailVolumes.html")
        val document = JsoupUtils.parse(file, Charset.forName("GBK"))
        val volumes = document.toArticleVolumes()
        assertEquals(volumes.size, 10)
        assertEquals(volumes.first().id, 101127)
        assertEquals(volumes.first().title, "第一卷")
        assertEquals(volumes.first().chapters.size, 20)
        assertEquals(volumes.first().chapters.first().id, 101128)
        assertEquals(volumes.first().chapters.first().title, "序章")
        assertEquals(volumes.last().title, "第九卷")
        assertEquals(volumes.last().chapters.size, 13)
    }
}
