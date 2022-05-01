package com.seiko.lightnovel.util

import java.nio.charset.Charset

inline val Charsets.GBK: Charset
    get() = Charset.forName("GBK")
