package com.seiko.lightnovel.di

import com.seiko.lightnovel.viewmodel.DetailViewModel
import com.seiko.lightnovel.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { (aid: Int) -> DetailViewModel(aid, get()) }
}
