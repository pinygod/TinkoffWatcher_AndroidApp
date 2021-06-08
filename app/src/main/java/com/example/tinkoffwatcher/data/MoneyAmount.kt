package com.example.tinkoffwatcher.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoneyAmount(
    val value: Double,
    val currency: Currency
):Parcelable