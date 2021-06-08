package com.example.tinkoffwatcher.di

import com.example.tinkoffwatcher.data.AuthenticationRepository
import com.example.tinkoffwatcher.data.StocksRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        AuthenticationRepository(get())
    }
    single { StocksRepository(get()) }
}