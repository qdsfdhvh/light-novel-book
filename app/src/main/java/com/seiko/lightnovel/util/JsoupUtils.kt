package com.seiko.lightnovel.util

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File
import java.io.InputStream
import java.nio.charset.Charset

object JsoupUtils {

    fun parse(file: File, charset: Charset = Charsets.UTF_8): Document {
        return Jsoup.parse(file, charset.name())
    }

    fun parse(inputStream: InputStream, charset: Charset = Charsets.UTF_8): Document {
        return Jsoup.parse(inputStream, charset.name(), "")
    }
}