package com.example.tinkoffwatcher.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Stock(
    val name: String,
    val isin: String,
    val figi: String,
    val isTrailStopActivated: Boolean,
    val isTrailStopEnabledByUser: Boolean,
    val averagePositionPrice: MoneyAmount,
    val lastPrice: Double,
    val balance: Int,
    val blocked: Int,
    val instrumentType: InstrumentType,
    val maxPrice: Double,
    val stopLossPercent: Double,
    val takeProfitPrice: Double,
    val ticker: String
) : Parcelable