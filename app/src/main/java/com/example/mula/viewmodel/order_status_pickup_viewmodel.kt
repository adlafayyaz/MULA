package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.OrderStatusPickupScreenState

sealed interface OrderStatusPickupScreenEvent { data object RetryRequested : OrderStatusPickupScreenEvent }

class OrderStatusPickupViewModel : ViewModel() {
    var state by mutableStateOf(OrderStatusPickupScreenState())
        private set

    fun on_event(event: OrderStatusPickupScreenEvent) {
        when (event) {
            OrderStatusPickupScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}

