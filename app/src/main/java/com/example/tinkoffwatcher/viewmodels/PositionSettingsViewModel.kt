package com.example.tinkoffwatcher.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinkoffwatcher.data.OrderType
import com.example.tinkoffwatcher.data.PositionSettings
import com.example.tinkoffwatcher.data.PositionSettingsObserveModel
import com.example.tinkoffwatcher.data.repository.PositionsRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PositionSettingsViewModel(private val positionsRepository: PositionsRepository) :
    ViewModel() {

    private var _position: PositionSettings? = null

    private val _trailStopPrice = MutableStateFlow(0.0)
    private val _stopLossPercent = MutableStateFlow(0.0)
    val trailStopEnabled = MutableStateFlow(false)
    val orderType = MutableStateFlow(OrderType.Limit)

    fun setPosition(position: PositionSettings) {
        trailStopEnabled.value = position.isTrailStopEnabledByUser
        orderType.value = position.orderType
        _trailStopPrice.value = position.takeProfitPrice
        _stopLossPercent.value = position.stopLossPercent
        _position = position

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

    fun onOrderTypeChanged(orderType: OrderType) {
        if (orderType != this.orderType.value) {
            this.orderType.value = orderType
        }
    }

    private fun startObservingSettings() {
        viewModelScope.launch {
            combine(
                _trailStopPrice,
                _stopLossPercent,
                trailStopEnabled,
                orderType
            ) { price, percent, trailState, orderType ->
                PositionSettingsObserveModel(
                    price,
                    percent,
                    trailState,
                    orderType
                )
            }.collectLatest { observe ->
                _position?.let { position ->
                    positionsRepository.updatePositionSettings(
                        position.positionFigi,
                        observe.activationPrice,
                        observe.stopLossPercent,
                        observe.isTrailStopEnabled,
                        observe.orderType
                    )
                }
            }
        }
    }
}