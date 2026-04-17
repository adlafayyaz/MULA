package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.VoucherScreenState

sealed interface VoucherScreenEvent { data object RetryRequested : VoucherScreenEvent }

class VoucherViewModel : ViewModel() {
    var state by mutableStateOf(VoucherScreenState())
        private set

    fun on_event(event: VoucherScreenEvent) {
        when (event) {
            VoucherScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}

