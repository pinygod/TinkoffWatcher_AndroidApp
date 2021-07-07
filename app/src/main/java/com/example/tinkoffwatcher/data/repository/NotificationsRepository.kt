package com.example.tinkoffwatcher.data.repository

import com.example.tinkoffwatcher.data.network.NotificationsApi

class NotificationsRepository(private val notificationsApi: NotificationsApi) {

    suspend fun addFCMToken(token: String) = notificationsApi.addFCMToken(token)

    suspend fun deleteFCMToken() = notificationsApi.deleteFCMToken()
}