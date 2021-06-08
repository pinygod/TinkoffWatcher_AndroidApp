package com.example.tinkoffwatcher.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinkoffwatcher.data.AuthenticationRepository
import com.example.tinkoffwatcher.network.AuthorizationHeaderInterceptor
import com.example.tinkoffwatcher.utils.DataStore
import com.example.tinkoffwatcher.utils.Event
import com.example.tinkoffwatcher.utils.LoginEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

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
                _uiState.value = LoginEvent.NavigateToMainScreen
            } catch (e: Exception) {
                _uiState.value = LoginEvent.ShowMessage("Error while signing in")
            } finally {
                _uiState.value = Event.Empty
            }
        }
    }

}