package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mula.data.model.Order
import com.example.mula.data.repository.OrdersRepository
import com.example.mula.navigation.ARG_ORDER_ID
import com.example.mula.navigation.ROUTE_CATALOG
import com.example.mula.navigation.ROUTE_ORDER_HISTORY_FAILED_DETAIL
import com.example.mula.navigation.ROUTE_ORDER_HISTORY_SUCCESS_DETAIL
import kotlinx.coroutines.launch

data class OrderHistoryScreenState(
    val is_loading: Boolean = false,
    val error_message: String? = null,
    val orders: List<Order> = emptyList(),
    val has_orders: Boolean = false,
    val selected_tab: String = TAB_ORDER_HISTORY,
    val navigation_target: String? = null,
    val navigation_argument_map: Map<String, String> = emptyMap()
)

sealed class OrderHistoryScreenEvent {
    data object OnScreenOpened : OrderHistoryScreenEvent()
    data class OnOrderClicked(val order_id: String, val result_status: String) : OrderHistoryScreenEvent()
    data class OnReorderClicked(val order_id: String) : OrderHistoryScreenEvent()
    data class OnTabSelected(val tab_id: String) : OrderHistoryScreenEvent()
    data object OnRetryClicked : OrderHistoryScreenEvent()
    data object OnNavigationHandled : OrderHistoryScreenEvent()
}

class OrderHistoryViewModel : ViewModel() {
    private val orders_repository: OrdersRepository = Stage5ARepositoryProvider.orders_repository

    var state by mutableStateOf(OrderHistoryScreenState())
        private set

    fun on_event(event: OrderHistoryScreenEvent) {
        when (event) {
            OrderHistoryScreenEvent.OnScreenOpened,
            OrderHistoryScreenEvent.OnRetryClicked -> load_orders()
            is OrderHistoryScreenEvent.OnOrderClicked -> open_order_detail(event.order_id, event.result_status)
            is OrderHistoryScreenEvent.OnReorderClicked -> reorder(event.order_id)
            is OrderHistoryScreenEvent.OnTabSelected -> handle_tab(event.tab_id)
            OrderHistoryScreenEvent.OnNavigationHandled -> {
                state = state.copy(
                    navigation_target = null,
                    navigation_argument_map = emptyMap()
                )
            }
        }
    }

    private fun load_orders() {
        if (state.is_loading) return
        viewModelScope.launch {
            state = state.copy(is_loading = true, error_message = null, selected_tab = TAB_ORDER_HISTORY)
            orders_repository.get_orders()
                .onSuccess { orders ->
                    state = state.copy(
                        is_loading = false,
                        orders = orders,
                        has_orders = orders.isNotEmpty(),
                        selected_tab = TAB_ORDER_HISTORY
                    )
                }
                .onFailure { throwable ->
                    state = state.copy(
                        is_loading = false,
                        error_message = throwable.message ?: "Gagal memuat riwayat pesanan"
                    )
                }
        }
    }

    private fun open_order_detail(order_id: String, result_status: String) {
        val target = when (result_status) {
            "success" -> ROUTE_ORDER_HISTORY_SUCCESS_DETAIL
            "failed" -> ROUTE_ORDER_HISTORY_FAILED_DETAIL
            else -> null
        } ?: return
        state = state.copy(
            navigation_target = target,
            navigation_argument_map = mapOf(ARG_ORDER_ID to order_id)
        )
    }

    private fun reorder(order_id: String) {
        if (state.is_loading) return
        viewModelScope.launch {
            state = state.copy(is_loading = true, error_message = null)
            orders_repository.reorder(order_id = order_id)
                .onSuccess {
                    state = state.copy(
                        is_loading = false,
                        navigation_target = ROUTE_CATALOG,
                        navigation_argument_map = emptyMap()
                    )
                }
                .onFailure { throwable ->
                    state = state.copy(
                        is_loading = false,
                        error_message = throwable.message ?: "Reorder gagal"
                    )
                }
        }
    }

    private fun handle_tab(tab_id: String) {
        if (tab_id == TAB_ORDER_HISTORY) return
        val route = route_for_tab(tab_id) ?: return
        state = state.copy(
            selected_tab = tab_id,
            navigation_target = route,
            navigation_argument_map = emptyMap()
        )
    }
}
