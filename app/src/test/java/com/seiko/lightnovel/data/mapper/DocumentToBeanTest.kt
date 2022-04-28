package com.seiko.lightnovel.data.mapper

import com.seiko.lightnovel.util.JsoupUtils
import org.junit.Test
import java.io.File
import java.nio.charset.Charset

class DocumentToBeanTest {

    @Test
    fun testArticleList() {
        val file = File("src/test/resources/wenku8/getTopList.html")
        val document = JsoupUtils.parse(file, Charset.forName("GBK"))
        val list = document.toArticleList()
        println(list)
        assert(list.size == 20)
    }

}