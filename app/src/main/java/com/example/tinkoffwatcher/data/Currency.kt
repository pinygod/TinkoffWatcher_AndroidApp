package com.example.tinkoffwatcher.data

import com.google.gson.annotations.SerializedName

enum class Currency(val cur: String) {
    @SerializedName("RUB")
    Rub("RUB"),
    @SerializedName("USD")
    Usd("USD"),
    @SerializedName("EUR")
    Eur("EUR"),
    @SerializedName("GBP")
    Gbp("GBP"),
    @SerializedName("HKD")
    Hkd("HKD"),
    @SerializedName("CHF")
    Chf("CHF"),
    @SerializedName("JPY")
    Jpy("JPY"),
    @SerializedName("CNY")
    Cny("CNY"),
    @SerializedName("TRY")
    Try("TRY"),
}