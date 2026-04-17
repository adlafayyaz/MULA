package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.PaymentMethodScreenState

sealed interface PaymentMethodScreenEvent { data object RetryRequested : PaymentMethodScreenEvent }

class PaymentMethodViewModel : ViewModel() {
    var state by mutableStateOf(PaymentMethodScreenState())
        private set

    fun on_event(event: PaymentMethodScreenEvent) {
        when (event) {
            PaymentMethodScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}

