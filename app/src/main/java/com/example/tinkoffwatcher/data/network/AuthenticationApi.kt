package com.example.tinkoffwatcher.data.network

import com.example.tinkoffwatcher.data.LoginModel
import com.example.tinkoffwatcher.data.LoginResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthenticationApi {

    @POST("api/Authentication/login/")
    @Headers("No-Authentication: true")
    suspend fun login(@Body model: LoginModel): LoginResponse
}