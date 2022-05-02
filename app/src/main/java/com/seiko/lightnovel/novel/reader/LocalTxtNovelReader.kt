package com.seiko.lightnovel.novel.reader

import com.seiko.lightnovel.util.findChapterRegex
import com.seiko.lightnovel.util.md5_16
import java.io.InputStream
import java.nio.charset.Charset
import java.util.concurrent.atomic.AtomicBoolean

class LocalTxtNovelReader(
    private val inputStream: InputStream,
    private val charset: Charset = Charsets.UTF_8,
) : NovelReader {

    private val isSetup = AtomicBoolean(false)

    private val volumes = mutableListOf<NovelChapter>()
    private val chapters = mutableMapOf<Any, MutableList<NovelContent>>()

    private fun setupNovel() {
        inputStream.reader(charset).useLines { lines ->
            var regex: Regex? = null
            var currentKey = ""
            lines.forEach { lineRaw ->
                val line = lineRaw.trim()
                if (line.isEmpty()) return@forEach
                val isVolume = if (regex != null) {
                    line.matches(regex!!)
                } else {
                    regex = line.findChapterRegex()
                    regex != null
                }
                if (isVolume) {
                    currentKey = line.md5_16
                    volumes.add(
                        NovelChapter(
                            title = line,
                            key = currentKey,
                        )
                    )
                }
                val chapters = chapters.getOrPut(currentKey) { mutableListOf() }
                chapters.add(
                    if (isVolume) {
                        NovelContent.Title(line)
                    } else {
                        NovelContent.Text(line)
                    }
                )
            }
        }
    }

    override suspend fun getChapterList(): List<NovelChapter> {
        if (isSetup.compareAndSet(false, true)) {
            setupNovel()
        }
        return volumes
    }

    override suspend fun getContentList(chapter: NovelChapter): List<NovelContent> {
        if (isSetup.compareAndSet(false, true)) {
            setupNovel()
        }
        return chapters[chapter.key] ?: emptyList()
    }
}
