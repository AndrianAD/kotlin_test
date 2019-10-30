package com.example.kotlin_test

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    companion object
    {
        lateinit var instance: MainApplication
    }


    override fun onCreate() {
        super.onCreate()

        instance = this

        startKoin {
            // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger()
            // use the Android context given there
            androidContext(this@MainApplication)
            // module list
            modules(firstModule)
        }
    }
}