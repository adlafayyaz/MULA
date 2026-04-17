package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.RegisterScreenState

sealed interface RegisterScreenEvent { data object RetryRequested : RegisterScreenEvent }

class RegisterViewModel : ViewModel() {
    var state by mutableStateOf(RegisterScreenState())
        private set

    fun on_event(event: RegisterScreenEvent) {
        when (event) {
            RegisterScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}

