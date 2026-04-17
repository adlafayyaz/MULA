package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.SplashScreenState

sealed interface SplashScreenEvent { data object RetryRequested : SplashScreenEvent }

class SplashViewModel : ViewModel() {
    var state by mutableStateOf(SplashScreenState())
        private set

    fun on_event(event: SplashScreenEvent) {
        when (event) {
            SplashScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}

