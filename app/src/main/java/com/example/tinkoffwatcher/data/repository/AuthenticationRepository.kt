package com.example.tinkoffwatcher.data.repository

import com.example.tinkoffwatcher.data.LoginModel
import com.example.tinkoffwatcher.data.network.AuthenticationApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthenticationRepository(private val authenticationApi: AuthenticationApi) {

    suspend fun login(username: String, password: String): String = withContext(Dispatchers.IO) {
        authenticationApi.login(LoginModel(username, password)).token
    }
}