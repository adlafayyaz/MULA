package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.CheckoutScreenState

sealed interface CheckoutScreenEvent { data object RetryRequested : CheckoutScreenEvent }

class CheckoutViewModel : ViewModel() {
    var state by mutableStateOf(CheckoutScreenState())
        private set

    fun on_event(event: CheckoutScreenEvent) {
        when (event) {
            CheckoutScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}

