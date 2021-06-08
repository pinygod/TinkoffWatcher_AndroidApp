package com.example.tinkoffwatcher.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinkoffwatcher.network.AuthorizationHeaderInterceptor
import com.example.tinkoffwatcher.utils.DataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SplashViewModel(private val dataStore: DataStore) : ViewModel() {

    private val _loginState = MutableStateFlow<Event>(Event.Empty)
    val loginState = _loginState

    init {
        viewModelScope.launch {
            dataStore.userTokenFlow.collect { token ->
                if (token.isNotEmpty()) {
                    _loginState.value = Event.NavigateToMainScreen
                } else {
                    _loginState.value = Event.NavigateToLogin
                }
            }
        }
    }

    sealed class Event {
        object Empty : Event()
        object NavigateToMainScreen : Event()
        object NavigateToLogin : Event()
    }
}