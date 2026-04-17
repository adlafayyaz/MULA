package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.ProfileScreenState

sealed interface ProfileScreenEvent { data object RetryRequested : ProfileScreenEvent }

class ProfileViewModel : ViewModel() {
    var state by mutableStateOf(ProfileScreenState())
        private set

    fun on_event(event: ProfileScreenEvent) {
        when (event) {
            ProfileScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}
