package com.seiko.lightnovel.data.api

import android.content.Context
import com.seiko.lightnovel.BuildConfig
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class Wenku8CookieJar(context: Context) : CookieJar {

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {

    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return listOf(
            Cookie.Builder()
                .name("PHPSESSID")
                .value(BuildConfig.TEST_PHPSESSID)
                .domain("www.wenku8.net")
                .build()
        )
    }
}