package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.OrderStatusDeliveryScreenState

sealed interface OrderStatusDeliveryScreenEvent { data object RetryRequested : OrderStatusDeliveryScreenEvent }

class OrderStatusDeliveryViewModel : ViewModel() {
    var state by mutableStateOf(OrderStatusDeliveryScreenState())
        private set

    fun on_event(event: OrderStatusDeliveryScreenEvent) {
        when (event) {
            OrderStatusDeliveryScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}

