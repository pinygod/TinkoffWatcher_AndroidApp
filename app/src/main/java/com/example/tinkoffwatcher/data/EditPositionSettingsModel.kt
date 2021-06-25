package com.example.tinkoffwatcher.data

data class EditPositionSettingsModel(
    val figi: String,
    val isTrailStopEnabledByUser: Boolean? = null,
    val takeProfitPrice: Double? = null,
    val stopLossPercent: Double? = null,
    val orderType: OrderType? = null
)