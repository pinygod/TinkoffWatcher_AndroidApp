package com.example.tinkoffwatcher.data.network

import com.example.tinkoffwatcher.data.ApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PATCH

interface NotificationsApi {
    @PATCH("Notifications/add_fcm_token/")
    suspend fun addFCMToken(@Body token: String): ApiResponse

    @DELETE("Notifications/delete_fcm_token/")
    suspend fun deleteFCMToken(): ApiResponse
}