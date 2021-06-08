package com.example.tinkoffwatcher.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinkoffwatcher.data.Stock
import com.example.tinkoffwatcher.data.StocksRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StockSettingsViewModel(private val stocksRepository: StocksRepository) : ViewModel() {

    private var _stock: Stock? = null

    private val _trailStopPrice = MutableStateFlow(0.0)
    private val _stopLossPercent = MutableStateFlow(0.0)
    val trailStopEnabled = MutableStateFlow(false)

    fun setStock(stock: Stock) {
        trailStopEnabled.value = stock.isTrailStopEnabledByUser
        _trailStopPrice.value = stock.takeProfitPrice
        _stopLossPercent.value = stock.stopLossPercent
        _stock = stock

        startObservingSettings()
    }

    fun onTrailStopPriceChanged(price: CharSequence) {
        if (price.isNotBlank()) {
            val parsedPrice = price.toString().toDoubleOrNull()
            parsedPrice?.let {
                if (it != _trailStopPrice.value) {
                    _trailStopPrice.value = it
                }
            }
        }
    }

    fun onStopLossPercentChanged(percent: CharSequence) {
        if (percent.isNotBlank()) {
            val parsedPercent = percent.toString().toDoubleOrNull()
            parsedPercent?.let {
                if (it != _stopLossPercent.value) {
                    _stopLossPercent.value = it
                }
            }
        }
    }

    private fun startObservingSettings() {
        viewModelScope.launch {
            combine(
                _trailStopPrice,
                _stopLossPercent,
                trailStopEnabled
            ) { price, percent, state ->
                Triple(
                    price,
                    percent,
                    state
                )
            }.collectLatest { (price, percent, state) ->
                _stock?.let {
                    if (price != it.takeProfitPrice || percent != it.stopLossPercent || state != it.isTrailStopEnabledByUser)
                        stocksRepository.updateStockSettings(
                            it.figi,
                            price,
                            percent,
                            state
                        )
                }
            }
        }
    }

}