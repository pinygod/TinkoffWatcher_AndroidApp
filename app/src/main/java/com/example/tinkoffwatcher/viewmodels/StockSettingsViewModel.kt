package com.example.tinkoffwatcher.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinkoffwatcher.data.SellOption
import com.example.tinkoffwatcher.data.Stock
import com.example.tinkoffwatcher.data.StockSettingsObserveModel
import com.example.tinkoffwatcher.data.repository.StocksRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StockSettingsViewModel(private val stocksRepository: StocksRepository) : ViewModel() {

    private var _stock: Stock? = null

    private val _trailStopPrice = MutableStateFlow(0.0)
    private val _stopLossPercent = MutableStateFlow(0.0)
    val trailStopEnabled = MutableStateFlow(false)
    val sellOption = MutableStateFlow(SellOption.Limit)

    fun setStock(stock: Stock) {
        trailStopEnabled.value = stock.isTrailStopEnabledByUser
        sellOption.value = stock.sellOption
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

    fun onSellOptionChanged(sellOption: SellOption) {
        if (sellOption != this.sellOption.value) {
            this.sellOption.value = sellOption
        }
    }

    private fun startObservingSettings() {
        viewModelScope.launch {
            combine(
                _trailStopPrice,
                _stopLossPercent,
                trailStopEnabled,
                sellOption
            ) { price, percent, trailState, sellOption ->
                StockSettingsObserveModel(
                    price,
                    percent,
                    trailState,
                    sellOption
                )
            }.collectLatest { observe ->
                _stock?.let { stock ->
                    //if (observe.activationPrice != stock.takeProfitPrice || observe.stopLossPercent != stock.stopLossPercent || observe.isTrailStopEnabled != stock.isTrailStopEnabledByUser || observe.sellOption != stock.sellOption)
                    stocksRepository.updateStockSettings(
                        stock.figi,
                        observe.activationPrice,
                        observe.stopLossPercent,
                        observe.isTrailStopEnabled,
                        observe.sellOption
                    )
                }
            }
        }
    }
}