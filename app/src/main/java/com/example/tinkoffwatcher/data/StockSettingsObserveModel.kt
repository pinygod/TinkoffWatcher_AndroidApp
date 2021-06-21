package com.example.tinkoffwatcher.data

data class StockSettingsObserveModel(
    val activationPrice: Double,
    val stopLossPercent: Double,
    val isTrailStopEnabled: Boolean,
    val sellOption: SellOption
)
