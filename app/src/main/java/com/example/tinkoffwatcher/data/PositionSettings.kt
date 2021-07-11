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
    val isTrailStopEnabledByUser: Boolean = false,
    val isObserveEnabled: Boolean,
    var activationPrice: Double?,
    var stopLossPercent: Double?,
    var orderType: OrderType?,
    val positionType: PositionType
) : Parcelable
