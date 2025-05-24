package com.gr6.habitforger.app

import android.app.Application
import com.gr6.habitforger.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin


class GritApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@GritApp)
            modules(appModule)
        }
    }

}