package com.seiko.lightnovel.datasource

import com.seiko.lightnovel.data.api.Wenku8Client
import com.seiko.lightnovel.data.mapper.toArticleDetail
import com.seiko.lightnovel.data.mapper.toArticleList
import com.seiko.lightnovel.data.mapper.toArticleVolumes
import com.seiko.lightnovel.data.mapper.toContentList
import com.seiko.lightnovel.data.model.bean.Article
import com.seiko.lightnovel.data.model.bean.ArticleContent
import com.seiko.lightnovel.data.model.bean.ArticleDetail
import com.seiko.lightnovel.data.model.bean.ArticleVolume
import com.seiko.lightnovel.data.model.enums.ArticleSort
import com.seiko.lightnovel.util.GBK
import com.seiko.lightnovel.util.JsoupUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.nio.charset.Charset

class Wenku8DataSource(
    private val client: Wenku8Client,
) {

    suspend fun login(username: String, password: String) =
        withContext(Dispatchers.IO) {
            val response = client.login(
                username = username,
                password = password,
                useCookie = 315360000, // ≈ 1 year
                action = "login",
                done = "submit",
            )
            // TODO
        }

    suspend fun getTopList(sort: ArticleSort, page: Int): List<Article> =
        withContext(Dispatchers.IO) {
            val response = client.getTopList(sort.name.lowercase(), page, Charsets.UTF_8.name())
            val document = response.toDocument()
            document.toArticleList()
        }

    suspend fun getDetail(aid: Int): ArticleDetail =
        withContext(Dispatchers.IO) {
            val response = client.getDetail(aid, Charsets.UTF_8.name())
            val document = response.toDocument()
            document.toArticleDetail(aid)
        }

    suspend fun getDetailVolumes(aid: Int): List<ArticleVolume> =
        withContext(Dispatchers.IO) {
            // api not support charset
            val response = client.getDetailVolumes(aid / 1000, aid)
            val document = response.toDocument(Charsets.GBK)
            document.toArticleVolumes()
        }

    suspend fun getContentList(aid: Int, cid: Int): List<ArticleContent> =
        withContext(Dispatchers.IO) {
            val response = client.getContentList(aid, cid, Charsets.UTF_8.name())
            val document = response.toDocument()
            document.toContentList()
        }

    private fun ResponseBody.toDocument(charset: Charset = Charsets.UTF_8) = use {
        JsoupUtils.parse(it.byteStream(), charset = charset)
    }
}
