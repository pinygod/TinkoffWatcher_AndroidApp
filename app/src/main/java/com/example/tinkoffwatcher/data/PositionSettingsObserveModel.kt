package com.example.tinkoffwatcher.data

data class PositionSettingsObserveModel(
    val activationPrice: Double,
    val stopLossPercent: Double,
    val isTrailStopEnabled: Boolean,
    val orderType: OrderType
)
