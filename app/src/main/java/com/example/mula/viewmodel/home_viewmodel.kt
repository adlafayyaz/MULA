package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.HomeScreenState

sealed interface HomeScreenEvent { data object RetryRequested : HomeScreenEvent }

class HomeViewModel : ViewModel() {
    var state by mutableStateOf(HomeScreenState())
        private set

    fun on_event(event: HomeScreenEvent) {
        when (event) {
            HomeScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}

