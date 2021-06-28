package com.example.tinkoffwatcher.data.network

import com.example.tinkoffwatcher.data.ApiResponse
import com.example.tinkoffwatcher.data.LoginModel
import com.example.tinkoffwatcher.data.LoginResponse
import retrofit2.http.*

interface AuthenticationApi {

    @POST("Authentication/login/")
    @Headers("No-Authentication: true")
    suspend fun login(@Body model: LoginModel): LoginResponse

    @PATCH("Authentication/add_fcm_token/")
    suspend fun addFCMToken(@Body token: String): ApiResponse

    @DELETE("Authentication/delete_fcm_token/")
    suspend fun deleteFCMToken(): ApiResponse
}