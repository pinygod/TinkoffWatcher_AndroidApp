package com.example.tinkoffwatcher.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Position(
    val name: String,
    val figi: String,
    val ticker: String,
    val isin: String,
    val instrumentType: InstrumentType,
    val lastPrice: Double
) : Parcelable