package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.OrderHistoryFailedDetailScreenState

sealed interface OrderHistoryFailedDetailScreenEvent { data object RetryRequested : OrderHistoryFailedDetailScreenEvent }

class OrderHistoryFailedDetailViewModel : ViewModel() {
    var state by mutableStateOf(OrderHistoryFailedDetailScreenState())
        private set

    fun on_event(event: OrderHistoryFailedDetailScreenEvent) {
        when (event) {
            OrderHistoryFailedDetailScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}

