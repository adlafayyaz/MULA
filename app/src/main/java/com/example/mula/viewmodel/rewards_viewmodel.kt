package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.RewardsScreenState

sealed interface RewardsScreenEvent { data object RetryRequested : RewardsScreenEvent }

class RewardsViewModel : ViewModel() {
    var state by mutableStateOf(RewardsScreenState())
        private set

    fun on_event(event: RewardsScreenEvent) {
        when (event) {
            RewardsScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}

