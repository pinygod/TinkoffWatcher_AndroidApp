package com.example.tinkoffwatcher.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinkoffwatcher.NotificationService
import com.example.tinkoffwatcher.data.repository.AuthenticationRepository
import com.example.tinkoffwatcher.utils.DataStore
import com.example.tinkoffwatcher.utils.Event
import com.example.tinkoffwatcher.utils.LoginEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModel(
    private val authenticationRepository: AuthenticationRepository,
    private val dataStore: DataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow<Event>(Event.Empty)
    val uiState = _uiState

    val username: MutableStateFlow<String> = MutableStateFlow("")
    val password: MutableStateFlow<String> = MutableStateFlow("")

    fun onLoginPressed() {
        viewModelScope.launch {
            _uiState.value = Event.Loading
            try {
                val token = authenticationRepository.login(username.value, password.value)
                dataStore.updateUserToken(token)
                dataStore.updateUsername(username.value)
                dataStore.updatePassword(password.value)
                NotificationService.generateFCMToken()
                _uiState.value = LoginEvent.NavigateToMainScreen
            } catch (e: Exception) {
                _uiState.value = LoginEvent.ShowMessage("Error while signing in")
            } finally {
                _uiState.value = Event.Empty
            }
        }
    }

}