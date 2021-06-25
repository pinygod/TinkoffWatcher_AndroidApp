package com.example.tinkoffwatcher.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PositionSettings(
    val positionFigi: String,
    val position: Position,
    val balance: Double,
    val lots: Double,
    val blocked: Int,
    val averagePositionPrice: MoneyAmount,
    val maxPrice: Double,
    val isTrailStopEnabledByUser: Boolean,
    val takeProfitPrice: Double,
    val stopLossPercent: Double,
    val positionType: PositionType,
    val orderType: OrderType
) : Parcelable
