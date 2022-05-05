package com.seiko.lightnovel.novel.paging

import android.graphics.Paint
import androidx.annotation.ColorInt
import com.seiko.lightnovel.novel.reader.NovelChapter
import com.seiko.lightnovel.novel.reader.NovelContent
import com.seiko.lightnovel.novel.view.ReaderConfig

sealed class NovelPagingData {

    abstract val key: Any

    // 纯文字
    data class Text(
        val chapterKey: Any,
        val page: Int,
        val contents: List<Content>,
    ) : NovelPagingData() {

        override val key: Any = "$chapterKey-$page"

        data class Content(
            val text: String,
            val textSize: Float,
            @ColorInt val textColor: Int,
            val x: Float,
            val y: Float,
        )
    }

    // 图片 一页一张
    data class Image(
        val chapterKey: Any,
        val page: Int,
        val url: String,
    ) : NovelPagingData() {
        override val key: Any = "$chapterKey-$page"
    }

    // 居中绘制的说明页
    data class Tip(
        val chapterKey: Any,
        val text: String,
    ) : NovelPagingData() {
        override val key: Any = "$chapterKey-$text"
    }
}

fun List<NovelContent>.toPagingDataList(
    chapter: NovelChapter,
    config: ReaderConfig,
): List<NovelPagingData> {
    if (config == ReaderConfig.None) return emptyList()

    val result = mutableListOf<NovelPagingData>()

    var page = 0

    fun createTextPagingData(
        contents: List<NovelPagingData.Text.Content>,
    ) = NovelPagingData.Text(
        chapterKey = chapter.key,
        page = page++,
        contents = contents,
    ).let { result.add(it) }

    fun createImagePagingData(
        url: String,
    ) = NovelPagingData.Image(
        chapterKey = chapter.key,
        page = page++,
        url = url,
    ).let { result.add(it) }

    val paint = Paint()
    var currentTextContents = mutableListOf<NovelPagingData.Text.Content>()

    val initialHeight = config.paddingTop.toFloat()
    var currentHeight = initialHeight

    val maxWidth = config.viewWidth - config.paddingLeft - config.paddingRight
    val maxHeight = config.viewHeight - config.paddingTop - config.paddingBottom

    fun splitTextToContents(text: String, textSize: Float, textColor: Int) {
        paint.textSize = textSize

        var start = 0
        while (start < text.length) {
            val wordCount = paint.breakText(
                text,
                start, text.length,
                false,
                maxWidth.toFloat(),
                null
            )
            currentTextContents.add(
                NovelPagingData.Text.Content(
                    text = text.substring(start, start + wordCount),
                    textSize = textSize,
                    textColor = textColor,
                    x = config.paddingLeft.toFloat(),
                    y = currentHeight
                )
            )
            start += wordCount
            currentHeight += textSize + config.lineSpace

            if (currentHeight + textSize > maxHeight) {
                createTextPagingData(currentTextContents)
                currentTextContents = mutableListOf()
                currentHeight = initialHeight
            }
        }
    }

    forEach { content ->
        when (content) {
            is NovelContent.Text -> {
                splitTextToContents(
                    text = content.text,
                    textSize = config.textSize,
                    textColor = config.textColor,
                )
            }
            is NovelContent.Title -> {
                splitTextToContents(
                    text = content.title,
                    textSize = config.titleSize,
                    textColor = config.titleColor,
                )
            }
            is NovelContent.Image -> {
                if (currentTextContents.isNotEmpty()) {
                    createTextPagingData(currentTextContents)
                    currentTextContents = mutableListOf()
                    currentHeight = initialHeight
                }
                createImagePagingData(content.url)
            }
        }
    }
    return result
}
