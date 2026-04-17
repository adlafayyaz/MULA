package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.OrderMethodSheetScreenState

sealed interface OrderMethodSheetScreenEvent { data object RetryRequested : OrderMethodSheetScreenEvent }

class OrderMethodSheetViewModel : ViewModel() {
    var state by mutableStateOf(OrderMethodSheetScreenState())
        private set

    fun on_event(event: OrderMethodSheetScreenEvent) {
        when (event) {
            OrderMethodSheetScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}

