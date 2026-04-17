package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.ResetPasswordScreenState

sealed interface ResetPasswordScreenEvent { data object RetryRequested : ResetPasswordScreenEvent }

class ResetPasswordViewModel : ViewModel() {
    var state by mutableStateOf(ResetPasswordScreenState())
        private set

    fun on_event(event: ResetPasswordScreenEvent) {
        when (event) {
            ResetPasswordScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}

