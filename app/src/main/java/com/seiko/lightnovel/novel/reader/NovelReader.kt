package com.seiko.lightnovel.novel.reader

interface NovelReader {
    suspend fun getVolumeList(): List<NovelVolume>
    suspend fun getChapterList(volume: NovelVolume): List<NovelChapter>
}
