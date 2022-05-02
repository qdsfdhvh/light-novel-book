package com.seiko.lightnovel.novel.paging

import android.text.TextPaint
import androidx.annotation.ColorInt
import com.seiko.lightnovel.novel.reader.NovelChapter
import com.seiko.lightnovel.novel.reader.NovelContent
import com.seiko.lightnovel.novel.view.ReaderConfig

data class PageChapter(
    val chapterKey: Any,
    val chapterPage: Int,
) {
    private val _contents: MutableList<PageContent> = mutableListOf()
    val contents: List<PageContent> get() = _contents

    fun addContent(content: PageContent) {
        _contents.add(content)
    }

    override fun toString(): String {
        return "PageChapter(" +
            "chapterKey=$chapterKey," +
            "chapterPage=$chapterPage," +
            "contents=${contents.joinToString(prefix = "[", postfix = "]")}" +
            ")"
    }
}

sealed class PageContent {
    data class Title(
        val title: String,
        val titleSize: Float,
        @ColorInt val titleColor: Int,
        val y: Float,
    ) : PageContent()

    data class Text(
        val text: String,
        val textSize: Float,
        @ColorInt val textColor: Int,
        val y: Float,
    ) : PageContent()

    data class Image(
        val url: String,
    ) : PageContent()
}

fun List<NovelContent>.toPageContents(
    chapter: NovelChapter,
    config: ReaderConfig,
): List<PageChapter> {
    if (config == ReaderConfig.None) return emptyList()

    val paint = TextPaint().apply {
        textSize = config.textSize
    }

    val viewWidth = config.viewWidth
    val viewHeight = config.viewHeight
    val titleSize = config.titleSize
    val textSize = config.textSize
    val contentPadding = config.contentPadding

    val result = mutableListOf<PageChapter>()

    var page = 0
    fun createNewPageContent() = PageChapter(
        chapterKey = chapter.key,
        chapterPage = page++,
    ).also {
        result.add(it)
    }

    var currentContent = createNewPageContent()
    var currentHeight = 0f

    fun calcTextAllLineHeight(text: String, textHeight: Float): Float {
        val textWidth = paint.measureText(text)
        val textLineNum = (textWidth / viewWidth).toInt() + if ((textWidth % viewWidth).compareTo(0) == 0) 0 else 1
        return textLineNum * textHeight
    }

    fun addPageContent(textAllLineHeight: Float, block: (y: Float) -> PageContent) {
        if (viewHeight - currentHeight < textAllLineHeight) {
            currentContent = createNewPageContent()
            currentHeight = 0f
        }
        currentContent.addContent(block(currentHeight))
        currentHeight += textAllLineHeight
        if (viewHeight - currentHeight < contentPadding) {
            currentHeight += contentPadding
        }
    }

    forEach { content ->
        when (content) {
            is NovelContent.Image -> {
                if (currentContent.contents.isNotEmpty()) {
                    currentContent = createNewPageContent()
                }
                currentContent.addContent(
                    PageContent.Image(content.url)
                )
                currentHeight = viewHeight.toFloat()
            }
            is NovelContent.Title -> {
                val textAllLineHeight = calcTextAllLineHeight(content.title, titleSize)
                addPageContent(textAllLineHeight) { y ->
                    PageContent.Title(
                        title = content.title,
                        titleSize = titleSize,
                        titleColor = config.titleColor,
                        y = y.toFloat(),
                    )
                }
            }
            is NovelContent.Text -> {
                val textAllLineHeight = calcTextAllLineHeight(content.text, textSize)
                addPageContent(textAllLineHeight) { y ->
                    PageContent.Text(
                        text = content.text,
                        textSize = textSize,
                        textColor = config.textColor,
                        y = y.toFloat(),
                    )
                }
            }
        }
    }
    return result
}
