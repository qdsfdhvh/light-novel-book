package com.seiko.lightnovel.util

import kotlin.test.Test
import kotlin.test.assertEquals

class MD5Test {
    @Test
    fun test() {
        val str = "aaaaaaa"
        assertEquals(str.md5, "5d793fc5b00a2348c3fb9ab59e5ca98a")
        assertEquals(str.md5_16, "b00a2348c3fb9ab5")
    }
}
