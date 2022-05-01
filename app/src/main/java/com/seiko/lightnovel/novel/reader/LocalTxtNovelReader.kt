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

    private val volumes = mutableListOf<NovelVolume>()
    private val chapters = mutableMapOf<Any, MutableList<NovelChapter>>()

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
                        NovelVolume(
                            title = line,
                            key = currentKey,
                        )
                    )
                }
                val chapters = chapters.getOrPut(currentKey) { mutableListOf() }
                chapters.add(
                    if (isVolume) {
                        NovelChapter.Title(line)
                    } else {
                        NovelChapter.Content(line)
                    }
                )
            }
        }
    }

    override suspend fun getVolumeList(): List<NovelVolume> {
        if (isSetup.compareAndSet(false, true)) {
            setupNovel()
        }
        return volumes
    }

    override suspend fun getChapterList(volume: NovelVolume): List<NovelChapter> {
        if (isSetup.compareAndSet(false, true)) {
            setupNovel()
        }
        return chapters[volume.key] ?: emptyList()
    }
}
