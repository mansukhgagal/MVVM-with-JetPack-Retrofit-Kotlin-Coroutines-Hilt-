package com.peod_stock.base

import android.app.Application
import com.peod_stock.BuildConfig
import com.peod_stock.utils.Prefs
import timber.log.Timber
import timber.log.Timber.DebugTree

val prefs: Prefs by lazy {
    MyApp.prefss!!
}
class MyApp: Application() {

    override fun onCreate() {
        prefss = Prefs(applicationContext)
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
    companion object {
        lateinit var instance: MyApp
            private set
        var prefss: Prefs? = null
    }
}