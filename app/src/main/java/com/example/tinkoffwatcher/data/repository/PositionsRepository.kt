package com.example.tinkoffwatcher.data.repository

import com.example.tinkoffwatcher.data.*
import com.example.tinkoffwatcher.data.network.PositionsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PositionsRepository(private val positionsApi: PositionsApi) {

    fun getUserPositions(query: String) = flow {
        while (true) {
            var positions = positionsApi.getUserPositions()
            positions = getPositionsByQuery(positions, query)
            positions = positions.sortedBy { it.position.lastPrice - it.averagePositionPrice.value }
            emit(positions)
            delay(10000)
        }
    }.flowOn(Dispatchers.IO)

    private fun getPositionsByQuery(positionsList: List<PositionSettings>, query: String) =
        positionsList.filter { element ->
            checkStringsLikeness(
                element.position.name,
                query
            ) || checkStringsLikeness(
                element.position.ticker,
                query
            )
        }

    private fun checkStringsLikeness(string: String?, query: String) = if (!string.isNullOrEmpty())
        string.contains(query, ignoreCase = true) else false

    suspend fun updateTrailStopPrice(isin: String, price: Double) {
        positionsApi.editPositionSettings(EditPositionSettingsModel(isin = isin, takeProfitPrice = price))
    }

    suspend fun updateTrailStopEnabled(isin: String, state: Boolean) {
        positionsApi.editPositionSettings(EditPositionSettingsModel(isin = isin, isTrailStopEnabledByUser = state))
    }

    suspend fun updateStopLossPercent(isin: String, percent: Double) {
        positionsApi.editPositionSettings(EditPositionSettingsModel(isin = isin, stopLossPercent = percent))
    }

    suspend fun updatePositionSettings(
        isin: String,
        price: Double,
        percent: Double,
        state: Boolean,
        orderType: OrderType
    ){
        positionsApi.editPositionSettings(EditPositionSettingsModel(isin = isin, takeProfitPrice = price, stopLossPercent = percent,isTrailStopEnabledByUser = state, orderType = orderType))
    }
}