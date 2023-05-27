package com.mx.rockstar.kratospoc.initializer

import android.content.Context
import androidx.startup.Initializer
import timber.log.Timber
import com.mx.rockstar.kratospoc.BuildConfig

class TimberInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("TimberInitializer is initialized.")
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()

}