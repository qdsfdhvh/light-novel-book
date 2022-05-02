package com.seiko.lightnovel.novel.reader

import com.seiko.lightnovel.util.GBK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import java.io.File
import kotlin.test.Test
import kotlin.test.assertTrue

class LocalTxtNovelReaderTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test() = runTest {
        val dir = File("src/test/resources/novel")
        dir.listFiles()?.forEach { file ->
            val novelReader = LocalTxtNovelReader(file.inputStream(), Charsets.GBK)
            val chapterList = novelReader.getChapterList()
            // println(volumeList.joinToString(" | ") { it.title })
            assertTrue(chapterList.isNotEmpty())
            val contentList = novelReader.getContentList(chapterList[0])
            // println(chapterList.joinToString("\n") { it.toString() })
            assertTrue(contentList.isNotEmpty())
        }
    }
}
