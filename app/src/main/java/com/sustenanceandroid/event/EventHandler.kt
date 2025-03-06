package com.sustenanceandroid.event

import android.util.Log
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter

class EventHandler {
    private val _events = MutableSharedFlow<AppEvent>()
    private val events = _events.asSharedFlow()

    companion object {
        private const val TAG = "EventHandler"
    }

    suspend fun post(event: AppEvent) {
        Log.d(TAG, "Emitting event = $event")
        _events.emit(event)
    }

    suspend fun onEvent(event: AppEvent, action: () -> Unit) {
        events.filter { publishedEvent -> publishedEvent == event }.collectLatest { action() }
    }
}
