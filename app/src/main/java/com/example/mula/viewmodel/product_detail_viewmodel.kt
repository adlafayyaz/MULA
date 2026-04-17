package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.ui.ProductDetailScreenState

sealed interface ProductDetailScreenEvent { data object RetryRequested : ProductDetailScreenEvent }

class ProductDetailViewModel : ViewModel() {
    var state by mutableStateOf(ProductDetailScreenState())
        private set

    fun on_event(event: ProductDetailScreenEvent) {
        when (event) {
            ProductDetailScreenEvent.RetryRequested -> state = state.copy(error_message = null)
        }
    }
}

