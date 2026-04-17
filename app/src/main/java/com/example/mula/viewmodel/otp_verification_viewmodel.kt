package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.OtpVerificationScreenState

sealed interface OtpVerificationScreenEvent { data object RetryRequested : OtpVerificationScreenEvent }

class OtpVerificationViewModel : ViewModel() {
    var state by mutableStateOf(OtpVerificationScreenState())
        private set

    fun on_event(event: OtpVerificationScreenEvent) {
        when (event) {
            OtpVerificationScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}

