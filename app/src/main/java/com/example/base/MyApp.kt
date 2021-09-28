package com.example.base

import android.app.Application
import com.example.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree


@HiltAndroidApp
class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
    companion object {
        lateinit var instance: MyApp
            private set
    }
}