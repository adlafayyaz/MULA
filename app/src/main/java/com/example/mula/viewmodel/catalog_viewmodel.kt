package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.CatalogScreenState

sealed interface CatalogScreenEvent { data object RetryRequested : CatalogScreenEvent }

class CatalogViewModel : ViewModel() {
    var state by mutableStateOf(CatalogScreenState())
        private set

    fun on_event(event: CatalogScreenEvent) {
        when (event) {
            CatalogScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}

