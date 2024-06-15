package com

import android.content.Context
import androidx.startup.Initializer

internal lateinit var applicationContext: Context
    private set

data object ContextProviderInitializer

class ContextProvider : Initializer<ContextProviderInitializer> {
    override fun create(context: Context): ContextProviderInitializer {
        applicationContext = context.applicationContext
        return ContextProviderInitializer
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()

}
