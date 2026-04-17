package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.OrderHistoryScreenState

sealed interface OrderHistoryScreenEvent { data object RetryRequested : OrderHistoryScreenEvent }

class OrderHistoryViewModel : ViewModel() {
    var state by mutableStateOf(OrderHistoryScreenState())
        private set

    fun on_event(event: OrderHistoryScreenEvent) {
        when (event) {
            OrderHistoryScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}

