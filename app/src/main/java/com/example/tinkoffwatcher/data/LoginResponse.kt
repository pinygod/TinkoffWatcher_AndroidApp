package com.example.tinkoffwatcher.data

import java.util.*

data class LoginResponse(
    val token: String,
    val expiration: Date,
    val isSubscriptionPaid: Boolean
)