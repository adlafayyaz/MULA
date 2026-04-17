package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.AuthLandingScreenState

sealed interface AuthLandingScreenEvent { data object RetryRequested : AuthLandingScreenEvent }

class AuthLandingViewModel : ViewModel() {
    var state by mutableStateOf(AuthLandingScreenState())
        private set

    fun on_event(event: AuthLandingScreenEvent) {
        when (event) {
            AuthLandingScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}

