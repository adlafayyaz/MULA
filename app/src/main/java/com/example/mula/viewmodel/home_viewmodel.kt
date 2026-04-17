package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mula.data.model.Banner
import com.example.mula.data.model.Promo
import com.example.mula.data.repository.HomeRepository
import com.example.mula.navigation.ARG_ENTRY_SOURCE
import com.example.mula.navigation.ARG_ORDER_METHOD
import com.example.mula.navigation.ROUTE_CATALOG
import com.example.mula.navigation.ROUTE_NOTIFICATION
import com.example.mula.navigation.ROUTE_REWARDS
import com.example.mula.navigation.ROUTE_VOUCHER
import java.time.LocalTime
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

data class HomeScreenState(
    val is_loading: Boolean = false,
    val error_message: String? = null,
    val greeting_text: String = "",
    val user_name: String = "",
    val token_balance_text: String = "",
    val banners: List<Banner> = emptyList(),
    val promos: List<Promo> = emptyList(),
    val selected_tab: String = TAB_HOME,
    val navigation_target: String? = null,
    val navigation_argument_map: Map<String, String> = emptyMap()
)

sealed class HomeScreenEvent {
    data object OnScreenOpened : HomeScreenEvent()
    data class OnBannerChanged(val index: Int) : HomeScreenEvent()
    data object OnNotificationClicked : HomeScreenEvent()
    data object OnRewardsClicked : HomeScreenEvent()
    data object OnDeliveryClicked : HomeScreenEvent()
    data object OnPickupClicked : HomeScreenEvent()
    data class OnPromoClicked(val promo_id: String) : HomeScreenEvent()
    data class OnTabSelected(val tab_id: String) : HomeScreenEvent()
    data object OnRetryClicked : HomeScreenEvent()
    data object OnNavigationHandled : HomeScreenEvent()
    data object OnErrorMessageDismissed : HomeScreenEvent()
}

class HomeViewModel : ViewModel() {
    private val home_repository: HomeRepository = Stage5ARepositoryProvider.home_repository

    var state by mutableStateOf(HomeScreenState())
        private set

    fun on_event(event: HomeScreenEvent) {
        when (event) {
            HomeScreenEvent.OnScreenOpened,
            HomeScreenEvent.OnRetryClicked -> load_home()
            is HomeScreenEvent.OnBannerChanged -> Unit
            HomeScreenEvent.OnNotificationClicked -> state = state.copy(navigation_target = ROUTE_NOTIFICATION)
            HomeScreenEvent.OnRewardsClicked -> state = state.copy(navigation_target = ROUTE_REWARDS)
            HomeScreenEvent.OnDeliveryClicked -> {
                state = state.copy(
                    navigation_target = ROUTE_CATALOG,
                    navigation_argument_map = mapOf(ARG_ORDER_METHOD to ORDER_METHOD_DELIVERY)
                )
            }

            HomeScreenEvent.OnPickupClicked -> {
                state = state.copy(
                    navigation_target = ROUTE_CATALOG,
                    navigation_argument_map = mapOf(ARG_ORDER_METHOD to ORDER_METHOD_PICKUP)
                )
            }

            is HomeScreenEvent.OnPromoClicked -> Unit
            is HomeScreenEvent.OnTabSelected -> handle_tab_selection(tab_id = event.tab_id)
            HomeScreenEvent.OnNavigationHandled -> {
                state = state.copy(
                    navigation_target = null,
                    navigation_argument_map = emptyMap()
                )
            }

            HomeScreenEvent.OnErrorMessageDismissed -> state = state.copy(error_message = null)
        }
    }

    private fun load_home() {
        if (state.is_loading) return
        viewModelScope.launch {
            state = state.copy(is_loading = true, error_message = null, selected_tab = TAB_HOME)
            runCatching {
                val banners = async { home_repository.get_banners().getOrThrow() }
                val promos = async { home_repository.get_promos().getOrThrow() }
                val token_balance = async { home_repository.get_token_balance().getOrThrow() }
                val greeting_name = async { home_repository.get_greeting_name().getOrThrow() }
                state = state.copy(
                    is_loading = false,
                    greeting_text = current_greeting_text(),
                    user_name = greeting_name.await(),
                    token_balance_text = build_token_balance_text(token_balance.await()),
                    banners = banners.await(),
                    promos = promos.await(),
                    selected_tab = TAB_HOME
                )
            }.onFailure { throwable ->
                state = state.copy(
                    is_loading = false,
                    error_message = throwable.message ?: "Gagal memuat home"
                )
            }
        }
    }

    private fun handle_tab_selection(tab_id: String) {
        val normalized_tab = tab_id.trimmed()
        if (normalized_tab == state.selected_tab || state.is_loading) return
        if (normalized_tab == TAB_VOUCHER) {
            state = state.copy(
                selected_tab = normalized_tab,
                navigation_target = ROUTE_VOUCHER,
                navigation_argument_map = mapOf(ARG_ENTRY_SOURCE to ENTRY_SOURCE_TAB)
            )
            return
        }
        val route = route_for_tab(normalized_tab) ?: return
        state = state.copy(
            selected_tab = normalized_tab,
            navigation_target = route,
            navigation_argument_map = emptyMap()
        )
    }

    private fun current_greeting_text(): String = when (LocalTime.now().hour) {
        in 0..10 -> "Selamat pagi,"
        in 11..14 -> "Selamat siang,"
        in 15..17 -> "Selamat sore,"
        else -> "Selamat malam,"
    }
}
