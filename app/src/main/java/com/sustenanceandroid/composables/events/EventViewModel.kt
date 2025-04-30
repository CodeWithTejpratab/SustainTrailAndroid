package com.sustenanceandroid.composables.events

import com.sustenanceandroid.mainScreen.BaseViewModel
import com.sustenanceandroid.model.Exposition
import com.sustenanceandroid.model.api.Screen
import com.sustenanceandroid.module.AppModule.apiRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EventViewModel : BaseViewModel() {

    data class UiState(
        val expositions: List<Exposition> = emptyList(),
        val isRefreshing: Boolean = false
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun fetchExpositions() {
        _uiState.update { it.copy(isRefreshing = true) }
        launchCatchingBlock {
            val data = apiRepository.fetchExpositions(Screen.EVENTS)
            data?.let { expositions -> _uiState.update { it.copy(expositions = expositions) } }
            delay(1000)
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }
}
