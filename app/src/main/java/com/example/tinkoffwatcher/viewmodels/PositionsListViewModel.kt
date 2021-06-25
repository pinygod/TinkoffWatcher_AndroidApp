package com.example.tinkoffwatcher.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinkoffwatcher.data.repository.PositionsRepository
import com.example.tinkoffwatcher.utils.DataStore
import com.example.tinkoffwatcher.utils.Event
import com.example.tinkoffwatcher.utils.PositionsEvent
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PositionsListViewModel(
    private val positionsRepository: PositionsRepository,
    private val dataStore: DataStore
) : ViewModel() {

    private val _event: MutableStateFlow<Event> = MutableStateFlow(Event.Empty)
    val event = _event

    val searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            searchQuery.collectLatest { query ->
                positionsRepository.getUserPositions(query).catch {
                    _event.value =
                        PositionsEvent.ShowMessage("Error while loading your positions :(")
                }.collect {
                    _event.value = PositionsEvent.Loaded(it)
                }
            }
        }
    }

    fun onLogoutClicked() {
        viewModelScope.launch {
            dataStore.clearSavedPreferences()
            _event.value = PositionsEvent.NavigateToLogin
        }

    }
}