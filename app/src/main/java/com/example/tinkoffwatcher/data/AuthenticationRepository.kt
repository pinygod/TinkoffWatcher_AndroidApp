package com.example.tinkoffwatcher.data

import com.example.tinkoffwatcher.network.AuthenticationApi
import com.example.tinkoffwatcher.utils.DataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthenticationRepository(
    private val authenticationApi: AuthenticationApi
) {

    suspend fun login(username: String, password: String): String = withContext(Dispatchers.IO) {
        authenticationApi.login(LoginModel(username, password)).token
    }


}