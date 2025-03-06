package com.sustenanceandroid.mainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sustenanceandroid.event.AppEvent
import com.sustenanceandroid.injection.AppModule
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    companion object {
        private const val ERROR_TAG = "APP ERROR"
    }

    private val eventHandler = AppModule.eventHandler

    fun launchCatchingBlock(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                Log.d(ERROR_TAG, throwable.message.orEmpty())
            },
            block = block
        )

    fun postEvent(event: AppEvent) {
        launchCatchingBlock {
            eventHandler.post(event)
        }
    }

    fun onEvent(event: AppEvent, action: () -> Unit) {
        launchCatchingBlock {
            eventHandler.onEvent(event, action)
        }
    }
}
