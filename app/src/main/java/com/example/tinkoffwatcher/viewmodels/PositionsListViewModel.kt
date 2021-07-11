package com.example.tinkoffwatcher.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinkoffwatcher.NotificationService
import com.example.tinkoffwatcher.data.PositionSettings
import com.example.tinkoffwatcher.data.RecyclerObject
import com.example.tinkoffwatcher.data.RecyclerObjectType
import com.example.tinkoffwatcher.data.repository.NotificationsRepository
import com.example.tinkoffwatcher.data.repository.PositionsRepository
import com.example.tinkoffwatcher.ui.adapters.PositionsListAdapter
import com.example.tinkoffwatcher.utils.DataStore
import com.example.tinkoffwatcher.utils.Event
import com.example.tinkoffwatcher.utils.PositionsEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PositionsListViewModel(
    private val positionsRepository: PositionsRepository,
    private val notificationsRepository: NotificationsRepository,
    private val dataStore: DataStore
) : ViewModel(), PositionsListAdapter.OnObserveChangeListener {

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
                    val recyclerList = ArrayList<RecyclerObject>()

                    val observablePositions = it.filter { position ->
                        position.isObserveEnabled
                    }.map { position ->
                        RecyclerObject(RecyclerObjectType.ObservablePosition, position)
                    }

                    val nonObservablePosition = it.filter { position ->
                        !position.isObserveEnabled
                    }.map { position ->
                        RecyclerObject(RecyclerObjectType.NonObservablePosition, position)
                    }

                    if (observablePositions.isNotEmpty()) {
                        recyclerList.add(RecyclerObject(RecyclerObjectType.Title, "Отслеживаемые"))
                        recyclerList.addAll(observablePositions)
                    }
                    if (nonObservablePosition.isNotEmpty()) {
                        recyclerList.add(RecyclerObject(RecyclerObjectType.Title, "Портфель"))
                        recyclerList.addAll(nonObservablePosition)
                    }

                    _event.value = PositionsEvent.Loaded(recyclerList)
                }
            }
        }
    }

    fun onLogoutClicked() {
        viewModelScope.launch {
            notificationsRepository.deleteFCMToken()
            NotificationService.deleteFCMToken()
            dataStore.clearSavedPreferences() // important to delete api token in local store AFTER all actions with api
            _event.value = PositionsEvent.NavigateToLogin
        }
    }

    override fun onObserveChange(position: PositionSettings) {
        viewModelScope.launch {
            positionsRepository.updatePositionSettings(position.positionFigi, !position.isObserveEnabled)
        }
    }
}