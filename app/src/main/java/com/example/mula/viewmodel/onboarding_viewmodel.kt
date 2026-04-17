package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.OnboardingScreenState

sealed interface OnboardingScreenEvent { data object RetryRequested : OnboardingScreenEvent }

class OnboardingViewModel : ViewModel() {
    var state by mutableStateOf(OnboardingScreenState())
        private set

    fun on_event(event: OnboardingScreenEvent) {
        when (event) {
            OnboardingScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}

