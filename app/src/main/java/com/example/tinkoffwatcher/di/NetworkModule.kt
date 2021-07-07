package com.example.tinkoffwatcher.di

import com.example.tinkoffwatcher.data.network.*
import org.koin.dsl.module

val networkModule = module {
    factory { provideAuthenticationApi(get()) }
    factory { providePositionsApi(get()) }
    factory { provideNotificationsApi(get()) }
    factory { provideOkHttpClient(get()) }
    single { AuthorizationHeaderInterceptor(get()) }
    single { provideRetrofit(get()) }
}