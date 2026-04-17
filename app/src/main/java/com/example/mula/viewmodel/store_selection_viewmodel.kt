package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.StoreSelectionScreenState

sealed interface StoreSelectionScreenEvent { data object RetryRequested : StoreSelectionScreenEvent }

class StoreSelectionViewModel : ViewModel() {
    var state by mutableStateOf(StoreSelectionScreenState())
        private set

    fun on_event(event: StoreSelectionScreenEvent) {
        when (event) {
            StoreSelectionScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}

