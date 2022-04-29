package com.seiko.lightnovel.di

import com.seiko.lightnovel.MainActivity
import com.seiko.lightnovel.component.loading.GlobalLoader
import com.seiko.lightnovel.util.GlobalLoadingAdapter
import org.koin.dsl.module

val componentModule = module {
    scope<MainActivity> {
        scoped<GlobalLoader.Adapter> {
            GlobalLoadingAdapter()
        }
        scoped {
            GlobalLoader(get(), get())
        }
    }
}
