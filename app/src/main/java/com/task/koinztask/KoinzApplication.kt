package com.task.koinztask

import android.app.Application
import com.task.koinztask.data.di.dataModule
import com.task.koinztask.di.photosModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class KoinzApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin{
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@KoinzApplication)
            modules(dataModule ,photosModule)
        }
    }
}