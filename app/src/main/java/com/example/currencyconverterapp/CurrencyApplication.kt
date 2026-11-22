package com.example.currencyconverterapp

import android.app.Application
import com.example.currencyconverterapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class CurrencyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CurrencyApplication)
            modules(appModule)
        }
    }
}