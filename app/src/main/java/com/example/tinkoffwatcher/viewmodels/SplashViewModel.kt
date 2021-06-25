package com.example.tinkoffwatcher.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinkoffwatcher.data.repository.AuthenticationRepository
import com.example.tinkoffwatcher.utils.DataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SplashViewModel(
    private val dataStore: DataStore,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<Event>(Event.Empty)
    val loginState = _loginState

    init {
        viewModelScope.launch {
            dataStore.userLoginDataFlow.collect { loginData ->
                if (!loginData.username.isNullOrBlank() && !loginData.password.isNullOrBlank()) {
                    val token =
                        authenticationRepository.login(loginData.username, loginData.password)
                    dataStore.updateUserToken(token)
                    dataStore.updateUsername(loginData.username)
                    dataStore.updatePassword(loginData.password)
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