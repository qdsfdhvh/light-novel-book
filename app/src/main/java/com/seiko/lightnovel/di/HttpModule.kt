package com.seiko.lightnovel.di

import android.util.Log
import com.seiko.lightnovel.BuildConfig
import com.seiko.lightnovel.data.api.Wenku8Client
import com.seiko.lightnovel.data.api.Wenku8CookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

val httpModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor { msg ->
                    Log.i("okhttp", msg)
                }.apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .cookieJar(Wenku8CookieJar(get()))
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(Wenku8Client.BASE_URL)
            .client(get())
            .build()
            .create(Wenku8Client::class.java)
    }
}
