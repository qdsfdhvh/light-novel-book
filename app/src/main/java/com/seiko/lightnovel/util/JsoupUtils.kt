package com.seiko.lightnovel.util

import org.jsoup.Jsoup
import java.io.File
import java.io.InputStream
import java.nio.charset.Charset

typealias HtmlNode = org.jsoup.nodes.Document

object JsoupUtils {

    fun parse(file: File, charset: Charset = Charsets.UTF_8): HtmlNode {
        return Jsoup.parse(file, charset.name())
    }

    fun parse(inputStream: InputStream, charset: Charset = Charsets.UTF_8): HtmlNode {
        return Jsoup.parse(inputStream, charset.name(), "")
    }
}