package com.example.tinkoffwatcher.di

import android.app.Application
import com.example.tinkoffwatcher.di.dataStoreModule
import com.example.tinkoffwatcher.di.networkModule
import com.example.tinkoffwatcher.di.repositoryModule
import com.example.tinkoffwatcher.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            koin.loadModules(listOf(networkModule, viewModelModule, repositoryModule, dataStoreModule))
        }
    }
}