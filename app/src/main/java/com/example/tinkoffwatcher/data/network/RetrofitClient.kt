package com.example.tinkoffwatcher.data.network

import com.example.tinkoffwatcher.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder().client(client).baseUrl(BuildConfig.API_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideAuthenticationApi(retrofit: Retrofit): AuthenticationApi =
    retrofit.create(AuthenticationApi::class.java)

fun provideStocksApi(retrofit: Retrofit): StocksApi = retrofit.create(StocksApi::class.java)