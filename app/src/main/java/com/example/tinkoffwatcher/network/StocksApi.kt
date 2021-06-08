package com.example.tinkoffwatcher.network

import com.example.tinkoffwatcher.data.Stock
import com.example.tinkoffwatcher.data.StockEditSettingsModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface StocksApi {

    @GET("api/stocks/get_user_stocks")
    suspend fun getUserStocks(): List<Stock>

    @PATCH("api/stocks/edit_stock_settings")
    suspend fun editStockSettings(@Body model: StockEditSettingsModel)
}