package com.example.tinkoffwatcher.di

import com.example.tinkoffwatcher.data.repository.AuthenticationRepository
import com.example.tinkoffwatcher.data.repository.NotificationsRepository
import com.example.tinkoffwatcher.data.repository.PositionsRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AuthenticationRepository(get()) }
    single { PositionsRepository(get()) }
    single { NotificationsRepository(get()) }
}