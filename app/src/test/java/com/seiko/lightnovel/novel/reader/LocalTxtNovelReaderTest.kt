package com.seiko.lightnovel.novel.reader

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import java.io.File
import java.nio.charset.Charset
import kotlin.test.Test
import kotlin.test.assertTrue

class LocalTxtNovelReaderTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test() = runTest {
        val dir = File("src/test/resources/novel")
        dir.listFiles()?.forEach { file ->
            val charset = Charset.forName("GBK")
            val novelReader = LocalTxtNovelReader(file.inputStream(), charset)
            val volumeList = novelReader.getVolumeList()
            // println(volumeList.joinToString(" | ") { it.title })
            assertTrue(volumeList.isNotEmpty())
            val chapterList = novelReader.getChapterList(volumeList[0])
            // println(chapterList.joinToString("\n") { it.toString() })
            assertTrue(chapterList.isNotEmpty())
        }
    }
}
