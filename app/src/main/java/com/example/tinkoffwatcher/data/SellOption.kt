package com.example.tinkoffwatcher.data

import com.google.gson.annotations.SerializedName

enum class SellOption(val value: Int) {
    @SerializedName("0")
    Limit(0),
    @SerializedName("1")
    Market(1)
}