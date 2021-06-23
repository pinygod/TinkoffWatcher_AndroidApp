package com.example.tinkoffwatcher.data.network

import com.example.tinkoffwatcher.data.EditPositionSettingsModel
import com.example.tinkoffwatcher.data.PositionSettings
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface PositionsApi {

    @GET("stocks/get_user_positions")
    suspend fun getUserPositions(): List<PositionSettings>

    @PATCH("stocks/edit_position_settings")
    suspend fun editPositionSettings(@Body model: EditPositionSettingsModel)
}