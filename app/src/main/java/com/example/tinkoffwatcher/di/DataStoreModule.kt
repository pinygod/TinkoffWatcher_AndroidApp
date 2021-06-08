package com.example.tinkoffwatcher.di

import com.example.tinkoffwatcher.utils.DataStore
import org.koin.dsl.module

val dataStoreModule = module {
    single { DataStore(get()) }
}