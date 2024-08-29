package com.aritra.wearapp.presentation

import android.app.Application
import di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WearApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@WearApp)
            modules(appModule)
        }
    }
}