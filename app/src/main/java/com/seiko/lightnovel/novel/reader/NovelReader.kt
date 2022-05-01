package com.seiko.lightnovel.novel.reader

interface NovelReader {
    suspend fun getVolumeList(): List<NovelChapter>
    suspend fun getContentList(chapter: NovelChapter): List<NovelContent>
}
