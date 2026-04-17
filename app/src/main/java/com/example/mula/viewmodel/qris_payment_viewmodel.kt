package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.QrisPaymentScreenState

sealed interface QrisPaymentScreenEvent { data object RetryRequested : QrisPaymentScreenEvent }

class QrisPaymentViewModel : ViewModel() {
    var state by mutableStateOf(QrisPaymentScreenState())
        private set

    fun on_event(event: QrisPaymentScreenEvent) {
        when (event) {
            QrisPaymentScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}

