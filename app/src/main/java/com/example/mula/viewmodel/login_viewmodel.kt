package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.LoginScreenState

sealed interface LoginScreenEvent { data object RetryRequested : LoginScreenEvent }

class LoginViewModel : ViewModel() {
    var state by mutableStateOf(LoginScreenState())
        private set

    fun on_event(event: LoginScreenEvent) {
        when (event) {
            LoginScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}

