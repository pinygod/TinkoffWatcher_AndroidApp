package com.example.tinkoffwatcher.utils

import com.example.tinkoffwatcher.data.PositionSettings

sealed class Event {
    object Empty : Event()
    object Loading: Event()

    fun isLoading() = this == Loading
}

sealed class LoginEvent : Event() {
    object NavigateToMainScreen : Event()
    data class ShowMessage(val text: String) : Event()
}

sealed class PositionsEvent : Event() {
    object NavigateToLogin: Event()
    data class Loaded(val positions: List<PositionSettings>) : Event()
    data class ShowMessage(val text: String) : Event()
}