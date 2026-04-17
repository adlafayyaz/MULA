package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mula.data.repository.SessionRepository
import com.example.mula.navigation.ROUTE_AUTH_LANDING
import com.example.mula.navigation.ROUTE_HOME
import com.example.mula.navigation.ROUTE_ONBOARDING
import com.example.mula.navigation.ROUTE_SPLASH
import kotlinx.coroutines.launch

data class SplashScreenState(
    val is_loading: Boolean = false,
    val error_message: String? = null,
    val is_first_launch: Boolean? = null,
    val is_logged_in: Boolean? = null,
    val navigation_target: String? = null,
    val clear_back_stack_to_route: String? = null
)

sealed class SplashScreenEvent {
    data object OnAppStarted : SplashScreenEvent()
    data object OnRetryClicked : SplashScreenEvent()
    data object OnNavigationHandled : SplashScreenEvent()
}

class SplashViewModel : ViewModel() {
    private val session_repository: SessionRepository = Stage5ARepositoryProvider.session_repository

    var state by mutableStateOf(SplashScreenState())
        private set

    fun on_event(event: SplashScreenEvent) {
        when (event) {
            SplashScreenEvent.OnAppStarted,
            SplashScreenEvent.OnRetryClicked -> load_startup_state()
            SplashScreenEvent.OnNavigationHandled -> {
                state = state.copy(
                    navigation_target = null,
                    clear_back_stack_to_route = null
                )
            }
        }
    }

    private fun load_startup_state() {
        if (state.is_loading) return
        viewModelScope.launch {
            state = state.copy(is_loading = true, error_message = null)
            runCatching {
                val first_launch = session_repository.is_first_launch()
                val session = session_repository.get_session()
                val target = when {
                    first_launch -> ROUTE_ONBOARDING
                    session.is_logged_in -> ROUTE_HOME
                    else -> ROUTE_AUTH_LANDING
                }
                state = state.copy(
                    is_loading = false,
                    is_first_launch = first_launch,
                    is_logged_in = session.is_logged_in,
                    navigation_target = target,
                    clear_back_stack_to_route = ROUTE_SPLASH
                )
            }.onFailure { throwable ->
                state = state.copy(
                    is_loading = false,
                    error_message = throwable.message ?: "Gagal memuat startup"
                )
            }
        }
    }
}
