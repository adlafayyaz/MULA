package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.NotificationScreenState

sealed interface NotificationScreenEvent { data object RetryRequested : NotificationScreenEvent }

class NotificationViewModel : ViewModel() {
    var state by mutableStateOf(NotificationScreenState())
        private set

    fun on_event(event: NotificationScreenEvent) {
        when (event) {
            NotificationScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}

