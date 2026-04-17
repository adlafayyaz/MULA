package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.OrderHistorySuccessDetailScreenState

sealed interface OrderHistorySuccessDetailScreenEvent { data object RetryRequested : OrderHistorySuccessDetailScreenEvent }

class OrderHistorySuccessDetailViewModel : ViewModel() {
    var state by mutableStateOf(OrderHistorySuccessDetailScreenState())
        private set

    fun on_event(event: OrderHistorySuccessDetailScreenEvent) {
        when (event) {
            OrderHistorySuccessDetailScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}

