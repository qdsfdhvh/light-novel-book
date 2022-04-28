package com.seiko.lightnovel.datasource

import com.seiko.lightnovel.data.api.Wenku8Client
import com.seiko.lightnovel.data.mapper.toArticleDetail
import com.seiko.lightnovel.data.mapper.toArticleList
import com.seiko.lightnovel.data.mapper.toArticleVolumes
import com.seiko.lightnovel.data.model.bean.ArticleBean
import com.seiko.lightnovel.data.model.bean.ArticleDetailBean
import com.seiko.lightnovel.data.model.bean.ArticleVolume
import com.seiko.lightnovel.data.model.enums.ArticleSort
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

    suspend fun getTopList(sort: ArticleSort, page: Int): List<ArticleBean> =
        withContext(Dispatchers.IO) {
            val response = client.getTopList(sort.name.lowercase(), page)
            val document = response.toDocument()
            document.toArticleList()
        }

    suspend fun getDetail(aid: Int): ArticleDetailBean =
        withContext(Dispatchers.IO) {
            val response = client.getDetail(aid)
            val document = response.toDocument()
            document.toArticleDetail(aid)
        }

    suspend fun getDetailVolumes(aid: Int): List<ArticleVolume> =
        withContext(Dispatchers.IO) {
            val response = client.getDetailVolumes(aid / 1000, aid)
            val document = response.toDocument()
            document.toArticleVolumes()
        }

    private fun ResponseBody.toDocument() = use {
        JsoupUtils.parse(it.byteStream(), Charset.forName("GBK"))
    }
}