package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.LocationPickerScreenState

sealed interface LocationPickerScreenEvent { data object RetryRequested : LocationPickerScreenEvent }

class LocationPickerViewModel : ViewModel() {
    var state by mutableStateOf(LocationPickerScreenState())
        private set

    fun on_event(event: LocationPickerScreenEvent) {
        when (event) {
            LocationPickerScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}

