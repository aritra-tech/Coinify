package com.aritra.coinify

import android.app.Application
import utils.ApplicationComponent

class Coinify : Application() {
    override fun onCreate() {
        super.onCreate()
        ApplicationComponent.init()
    }
}