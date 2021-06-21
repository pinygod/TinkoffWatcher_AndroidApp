package com.example.tinkoffwatcher.di

import com.example.tinkoffwatcher.data.repository.AuthenticationRepository
import com.example.tinkoffwatcher.data.repository.StocksRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        AuthenticationRepository(get())
    }
    single { StocksRepository(get()) }
}