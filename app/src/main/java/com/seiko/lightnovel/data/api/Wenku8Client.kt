package com.seiko.lightnovel.data.api

import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Wenku8Client {
    companion object {
        const val BASE_URL = "https://www.wenku8.net/"
    }

    @FormUrlEncoded
    @POST("login.php")
    suspend fun login(
        @Query("do") done: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("usecookie") useCookie: Int,
        @Field("action") action: String,
    ): ResponseBody

    @GET("modules/article/toplist.php")
    suspend fun getTopList(
        @Query("sort") sort: String, // lastupdate
        @Query("page") page: Int, // 1+
        @Query("charset") charset: String,
    ): ResponseBody

    @GET("book/{aid}.htm")
    suspend fun getDetail(
        @Path("aid") aid: Int,
        @Query("charset") charset: String,
    ): ResponseBody

    @GET("novel/{dir}/{aid}/index.htm")
    suspend fun getDetailVolumes(
        @Path("dir") dir: Int,
        @Path("aid") aid: Int,
    ): ResponseBody

    @GET("modules/article/reader.php")
    suspend fun getContentList(
        @Query("aid") aid: Int,
        @Query("cid") cid: Int,
        @Query("charset") charset: String,
    ): ResponseBody
}
