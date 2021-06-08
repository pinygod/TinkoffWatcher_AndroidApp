package com.example.tinkoffwatcher.di

import com.example.tinkoffwatcher.network.*
import org.koin.dsl.module

val networkModule = module {
    factory { provideAuthenticationApi(get()) }
    factory { provideStocksApi(get()) }
    factory { provideOkHttpClient(get()) }
    single { AuthorizationHeaderInterceptor(get()) }
    single { provideRetrofit(get()) }
}