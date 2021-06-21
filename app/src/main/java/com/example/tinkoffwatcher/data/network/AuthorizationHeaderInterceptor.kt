package com.example.tinkoffwatcher.data.network

import androidx.lifecycle.asLiveData
import com.example.tinkoffwatcher.utils.DataStore
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthorizationHeaderInterceptor(private val dataStore: DataStore) :
    Interceptor {

    private var token = ""

    init {
        dataStore.userTokenFlow.asLiveData().observeForever {
            token = it
        }
    }

    fun updateToken(token: String? = null) {
        if (token != null) {
            this.token = token
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }
}