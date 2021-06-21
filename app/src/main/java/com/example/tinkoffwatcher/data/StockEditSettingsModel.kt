package com.example.tinkoffwatcher.data

data class StockEditSettingsModel(
    val figi: String,
    val isTrailStopEnabledByUser: Boolean? = null,
    val takeProfitPrice: Double? = null,
    val stopLossPercent: Double? = null,
    val sellOption: Int? = null
)