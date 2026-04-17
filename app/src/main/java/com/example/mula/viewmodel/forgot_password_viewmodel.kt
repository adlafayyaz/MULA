package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.ForgotPasswordScreenState

sealed interface ForgotPasswordScreenEvent { data object RetryRequested : ForgotPasswordScreenEvent }

class ForgotPasswordViewModel : ViewModel() {
    var state by mutableStateOf(ForgotPasswordScreenState())
        private set

    fun on_event(event: ForgotPasswordScreenEvent) {
        when (event) {
            ForgotPasswordScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}

