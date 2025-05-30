package com.group6.habitforger.app

import android.app.Application
import com.group6.habitforger.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin


class HBApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@HBApp)
            modules(appModule)
        }
    }

}