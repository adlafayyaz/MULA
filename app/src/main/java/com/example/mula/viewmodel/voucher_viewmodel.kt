package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mula.data.model.Voucher

data class VoucherScreenState(
    val is_loading: Boolean = false,
    val error_message: String? = null,
    val entry_source: String = ENTRY_SOURCE_TAB,
    val vouchers: List<Voucher> = emptyList(),
    val selected_voucher_id: String? = null,
    val selected_tab: String = TAB_VOUCHER,
    val show_bottom_tab: Boolean = true,
    val navigation_target: String? = null,
    val navigation_argument_map: Map<String, String> = emptyMap(),
    val pop_back_stack: Boolean = false
)

sealed class VoucherScreenEvent {
    data class OnScreenOpened(val entry_source: String) : VoucherScreenEvent()
    data class OnVoucherActionClicked(val voucher_id: String) : VoucherScreenEvent()
    data object OnBackClicked : VoucherScreenEvent()
    data class OnTabSelected(val tab_id: String) : VoucherScreenEvent()
    data object OnRetryClicked : VoucherScreenEvent()
    data object OnNavigationHandled : VoucherScreenEvent()
}

class VoucherViewModel : ViewModel() {
    var state by mutableStateOf(VoucherScreenState())
        private set

    fun on_event(event: VoucherScreenEvent) {
        when (event) {
            is VoucherScreenEvent.OnScreenOpened -> load_vouchers(event.entry_source)
            is VoucherScreenEvent.OnVoucherActionClicked -> handle_voucher_action(event.voucher_id)
            VoucherScreenEvent.OnBackClicked -> state = state.copy(pop_back_stack = true)
            is VoucherScreenEvent.OnTabSelected -> handle_tab(event.tab_id)
            VoucherScreenEvent.OnRetryClicked -> load_vouchers(state.entry_source)
            VoucherScreenEvent.OnNavigationHandled -> {
                state = state.copy(
                    navigation_target = null,
                    navigation_argument_map = emptyMap(),
                    pop_back_stack = false
                )
            }
        }
    }

    private fun load_vouchers(entry_source: String) {
        val normalized_entry_source = if (entry_source == ENTRY_SOURCE_CHECKOUT) ENTRY_SOURCE_CHECKOUT else ENTRY_SOURCE_TAB
        state = state.copy(
            is_loading = false,
            error_message = null,
            entry_source = normalized_entry_source,
            vouchers = Stage5ARepositoryProvider.vouchers,
            show_bottom_tab = normalized_entry_source == ENTRY_SOURCE_TAB,
            selected_tab = TAB_VOUCHER
        )
    }

    private fun handle_voucher_action(voucher_id: String) {
        state = state.copy(selected_voucher_id = voucher_id)
        if (state.entry_source == ENTRY_SOURCE_CHECKOUT) {
            state = state.copy(pop_back_stack = true)
        }
    }

    private fun handle_tab(tab_id: String) {
        if (!state.show_bottom_tab || tab_id == TAB_VOUCHER) return
        val route = route_for_tab(tab_id) ?: return
        state = state.copy(
            selected_tab = tab_id,
            navigation_target = route
        )
    }
}
