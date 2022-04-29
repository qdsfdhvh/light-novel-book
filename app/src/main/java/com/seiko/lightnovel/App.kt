package com.seiko.lightnovel

import android.app.Application
import com.seiko.lightnovel.di.componentModule
import com.seiko.lightnovel.di.dataSourceModule
import com.seiko.lightnovel.di.httpModule
import com.seiko.lightnovel.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@App)
            modules(
                httpModule,
                dataSourceModule,
                viewModelModule,
                componentModule,
            )
        }
    }
}
