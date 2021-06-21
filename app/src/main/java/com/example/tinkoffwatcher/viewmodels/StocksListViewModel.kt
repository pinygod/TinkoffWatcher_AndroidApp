package com.example.tinkoffwatcher.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinkoffwatcher.data.repository.StocksRepository
import com.example.tinkoffwatcher.utils.DataStore
import com.example.tinkoffwatcher.utils.Event
import com.example.tinkoffwatcher.utils.StocksEvent
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StocksListViewModel(private val stocksRepository: StocksRepository, private val dataStore: DataStore) : ViewModel() {

    private val _stocksEvent: MutableStateFlow<Event> = MutableStateFlow(Event.Empty)
    val stocksEvent = _stocksEvent

    val searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            searchQuery.collectLatest { query ->
                stocksRepository.getUserStocks(query).catch {
                    _stocksEvent.value = StocksEvent.ShowMessage("Error while loading stocks :(")
                }.collect {
                    _stocksEvent.value = StocksEvent.Loaded(it)
                }
            }
        }
    }

    fun onLogoutClicked(){
        viewModelScope.launch {
            dataStore.deleteUserToken()
            _stocksEvent.value = StocksEvent.NavigateToLogin
        }

    }
}