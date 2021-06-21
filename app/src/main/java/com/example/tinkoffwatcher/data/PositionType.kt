package com.example.tinkoffwatcher.data

import com.google.gson.annotations.SerializedName

enum class PositionType(val value: Int) {
    @SerializedName("0")
    Short(0),
    @SerializedName("1")
    Long(1)
}