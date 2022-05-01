package com.seiko.lightnovel.util

import kotlin.test.Test
import kotlin.test.assertTrue

class RegexTest {
    @Test
    fun chapterTest() {
        assertTrue("第一卷".isChapterTitle())
        assertTrue("第一卷 序章".isChapterTitle())
        assertTrue("第1卷".isChapterTitle())
        assertTrue("第1卷 序章".isChapterTitle())
        assertTrue("第壹卷".isChapterTitle())
        assertTrue("第壹卷 序章".isChapterTitle())
        assertTrue("Chapter 1".isChapterTitle())
        assertTrue(!"哆啦A梦".isChapterTitle())
    }
}
