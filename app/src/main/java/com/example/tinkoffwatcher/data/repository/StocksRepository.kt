package com.example.tinkoffwatcher.data.repository

import com.example.tinkoffwatcher.data.InstrumentType
import com.example.tinkoffwatcher.data.SellOption
import com.example.tinkoffwatcher.data.Stock
import com.example.tinkoffwatcher.data.StockEditSettingsModel
import com.example.tinkoffwatcher.data.network.StocksApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class StocksRepository(private val stocksApi: StocksApi) {

    fun getUserStocks(query: String) = flow<List<Stock>> {
        while (true) {
            var stocks = stocksApi.getUserStocks()
                .filter { stock -> stock.instrumentType == InstrumentType.Stock }
            stocks = getStocksByQuery(stocks, query)
            stocks = stocks.sortedBy { it.lastPrice - it.averagePositionPrice.value }
            emit(stocks)
            delay(10000)
        }
    }.flowOn(Dispatchers.IO)

    private fun getStocksByQuery(stocksList: List<Stock>, query: String) =
        stocksList.filter { element ->
            checkStringsLikeness(
                element.name,
                query
            ) || checkStringsLikeness(
                element.ticker,
                query
            )
        }

    private fun checkStringsLikeness(string: String?, query: String) = if (!string.isNullOrEmpty())
        string.contains(query, ignoreCase = true) else false

    suspend fun updateTrailStopPrice(figi: String, price: Double) {
        stocksApi.editStockSettings(StockEditSettingsModel(figi = figi, takeProfitPrice = price))
    }

    suspend fun updateTrailStopEnabled(figi: String, state: Boolean) {
        stocksApi.editStockSettings(StockEditSettingsModel(figi = figi, isTrailStopEnabledByUser = state))
    }

    suspend fun updateStopLossPercent(figi: String, percent: Double) {
        stocksApi.editStockSettings(StockEditSettingsModel(figi = figi, stopLossPercent = percent))
    }

    suspend fun updateStockSettings(
        figi: String,
        price: Double,
        percent: Double,
        state: Boolean,
        sellOption: SellOption
    ){
        stocksApi.editStockSettings(StockEditSettingsModel(figi = figi, takeProfitPrice = price, stopLossPercent = percent,isTrailStopEnabledByUser = state, sellOption = sellOption.value))
    }
}