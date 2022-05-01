package com.seiko.lightnovel.util

// "(第)([0-9零一二两三四五六七八九十百千万壹贰叁肆伍陆柒捌玖拾佰仟]{1,10})([章节回集卷])(.*)"
private val CHAPTER_PATTERNS = arrayOf(
    "^(.{0,8})(\u7b2c)([0-9\u96f6\u4e00\u4e8c\u4e24\u4e09\u56db\u4e94\u516d\u4e03\u516b\u4e5d\u5341\u767e\u5343\u4e07\u58f9\u8d30\u53c1\u8086\u4f0d\u9646\u67d2\u634c\u7396\u62fe\u4f70\u4edf]{1,10})([\u7ae0\u8282\u56de\u96c6\u5377])(.{0,30})$",
    "^(\\s{0,4})([\\(\u3010\u300a]?(\u5377)?)([0-9\u96f6\u4e00\u4e8c\u4e24\u4e09\u56db\u4e94\u516d\u4e03\u516b\u4e5d\u5341\u767e\u5343\u4e07\u58f9\u8d30\u53c1\u8086\u4f0d\u9646\u67d2\u634c\u7396\u62fe\u4f70\u4edf]{1,10})([\\.:\uff1a\u0020\\f\t])(.{0,30})$",
    "^(\\s{0,4})([\\(\uff08\u3010\u300a])(.{0,30})([\\)\uff09\u3011\u300b])(\\s{0,2})$",
    "^(\\s{0,4})(\u6b63\u6587)(.{0,20})$",
    "^(.{0,4})(Chapter|chapter)(\\s{0,4})([0-9]{1,4})(.{0,30})$"
)

fun String.findChapterRegex(): Regex? {
    for (pattern in CHAPTER_PATTERNS) {
        val regex = pattern.toRegex()
        if (this.matches(regex)) {
            return regex
        }
    }
    return null
}

fun String.isChapterTitle(): Boolean {
    for (pattern in CHAPTER_PATTERNS) {
        if (this.matches(pattern.toRegex())) {
            return true
        }
    }
    return false
}
