package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mula.data.repository.SessionRepository
import com.example.mula.navigation.ROUTE_AUTH_LANDING
import com.example.mula.navigation.ROUTE_ONBOARDING
import kotlinx.coroutines.launch

data class OnboardingScreenState(
    val is_loading: Boolean = false,
    val error_message: String? = null,
    val current_page_index: Int = 0,
    val page_count: Int = 2,
    val navigation_target: String? = null,
    val clear_back_stack_to_route: String? = null
)

sealed class OnboardingScreenEvent {
    data class OnPageChanged(val page_index: Int) : OnboardingScreenEvent()
    data object OnNextClicked : OnboardingScreenEvent()
    data object OnFinalCtaClicked : OnboardingScreenEvent()
    data object OnBackPressed : OnboardingScreenEvent()
    data object OnNavigationHandled : OnboardingScreenEvent()
}

class OnboardingViewModel : ViewModel() {
    private val session_repository: SessionRepository = Stage5ARepositoryProvider.session_repository

    var state by mutableStateOf(OnboardingScreenState())
        private set

    fun on_event(event: OnboardingScreenEvent) {
        when (event) {
            is OnboardingScreenEvent.OnPageChanged -> {
                state = state.copy(current_page_index = event.page_index.coerceIn(0, state.page_count - 1))
            }

            OnboardingScreenEvent.OnNextClicked -> {
                if (state.current_page_index < state.page_count - 1) {
                    state = state.copy(current_page_index = state.current_page_index + 1)
                }
            }

            OnboardingScreenEvent.OnFinalCtaClicked -> complete_onboarding()

            OnboardingScreenEvent.OnBackPressed -> {
                if (state.current_page_index > 0) {
                    state = state.copy(current_page_index = state.current_page_index - 1)
                }
            }

            OnboardingScreenEvent.OnNavigationHandled -> {
                state = state.copy(
                    navigation_target = null,
                    clear_back_stack_to_route = null
                )
            }
        }
    }

    private fun complete_onboarding() {
        if (state.is_loading) return
        viewModelScope.launch {
            state = state.copy(is_loading = true, error_message = null)
            runCatching {
                session_repository.set_first_launch_completed()
                state = state.copy(
                    is_loading = false,
                    navigation_target = ROUTE_AUTH_LANDING,
                    clear_back_stack_to_route = ROUTE_ONBOARDING
                )
            }.onFailure { throwable ->
                state = state.copy(
                    is_loading = false,
                    error_message = throwable.message ?: "Gagal menyimpan onboarding"
                )
            }
        }
    }
}
