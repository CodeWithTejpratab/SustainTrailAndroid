package com.sustenanceandroid.mainScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sustenanceandroid.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(application: Application) : AndroidViewModel(application) {

    data class State(
        var name: String? = null,
        val age: Int? = null
    )

    private val _myData = MutableStateFlow(State())
    val state: StateFlow<State> = _myData.asStateFlow()

    init {
        val resources = application.resources
        _myData.update {
            state.value.copy(
                name = resources.getString(R.string.people_name),
                age = resources.getInteger(R.integer.age)
            )
        }
    }
}
