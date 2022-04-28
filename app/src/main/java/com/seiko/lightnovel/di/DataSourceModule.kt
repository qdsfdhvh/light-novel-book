package com.seiko.lightnovel.di

import com.seiko.lightnovel.datasource.Wenku8DataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single { Wenku8DataSource(get()) }
}
