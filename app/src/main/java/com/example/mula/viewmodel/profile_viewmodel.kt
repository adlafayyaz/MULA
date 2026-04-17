package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mula.data.repository.AuthRepository
import com.example.mula.data.repository.ProfileRepository
import com.example.mula.data.repository.SessionRepository
import com.example.mula.navigation.ROUTE_AUTH_LANDING
import com.example.mula.navigation.ROUTE_HOME
import kotlinx.coroutines.launch

data class ProfileScreenState(
    val is_loading: Boolean = false,
    val error_message: String? = null,
    val user_name: String = "",
    val phone_number: String = "",
    val birth_date: String = "",
    val gender: String = "",
    val join_date: String = "",
    val profile_image_res_name: String? = null,
    val selected_tab: String = TAB_PROFILE,
    val navigation_target: String? = null,
    val clear_back_stack_to_route: String? = null,
    val is_logging_out: Boolean = false
)

sealed class ProfileScreenEvent {
    data object OnScreenOpened : ProfileScreenEvent()
    data object OnEditAvatarClicked : ProfileScreenEvent()
    data object OnEditUsernameClicked : ProfileScreenEvent()
    data object OnEditPhoneClicked : ProfileScreenEvent()
    data object OnLogoutClicked : ProfileScreenEvent()
    data class OnTabSelected(val tab_id: String) : ProfileScreenEvent()
    data object OnRetryClicked : ProfileScreenEvent()
    data object OnNavigationHandled : ProfileScreenEvent()
    data object OnErrorMessageDismissed : ProfileScreenEvent()
}

class ProfileViewModel : ViewModel() {
    private val profile_repository: ProfileRepository = Stage5ARepositoryProvider.profile_repository
    private val auth_repository: AuthRepository = Stage5ARepositoryProvider.auth_repository
    private val session_repository: SessionRepository = Stage5ARepositoryProvider.session_repository

    var state by mutableStateOf(ProfileScreenState())
        private set

    fun on_event(event: ProfileScreenEvent) {
        when (event) {
            ProfileScreenEvent.OnScreenOpened,
            ProfileScreenEvent.OnRetryClicked -> load_profile()
            ProfileScreenEvent.OnEditAvatarClicked -> Unit
            ProfileScreenEvent.OnEditUsernameClicked -> Unit
            ProfileScreenEvent.OnEditPhoneClicked -> Unit
            ProfileScreenEvent.OnLogoutClicked -> logout()
            is ProfileScreenEvent.OnTabSelected -> handle_tab(event.tab_id)
            ProfileScreenEvent.OnNavigationHandled -> {
                state = state.copy(
                    navigation_target = null,
                    clear_back_stack_to_route = null
                )
            }

            ProfileScreenEvent.OnErrorMessageDismissed -> state = state.copy(error_message = null)
        }
    }

    private fun load_profile() {
        if (state.is_loading) return
        viewModelScope.launch {
            state = state.copy(is_loading = true, error_message = null, selected_tab = TAB_PROFILE)
            profile_repository.get_profile()
                .onSuccess { profile ->
                    state = state.copy(
                        is_loading = false,
                        user_name = profile.username,
                        phone_number = profile.phone_number,
                        birth_date = profile.birth_date,
                        gender = profile.gender,
                        join_date = profile.join_date,
                        profile_image_res_name = profile.profile_image_res_name,
                        selected_tab = TAB_PROFILE
                    )
                }
                .onFailure { throwable ->
                    state = state.copy(
                        is_loading = false,
                        error_message = throwable.message ?: "Gagal memuat profil"
                    )
                }
        }
    }

    private fun logout() {
        if (state.is_logging_out) return
        viewModelScope.launch {
            state = state.copy(is_logging_out = true, error_message = null)
            runCatching { auth_repository.logout() }
            runCatching { session_repository.clear_session() }
            state = state.copy(
                is_logging_out = false,
                navigation_target = ROUTE_AUTH_LANDING,
                clear_back_stack_to_route = ROUTE_HOME
            )
        }
    }

    private fun handle_tab(tab_id: String) {
        if (tab_id == TAB_PROFILE || state.is_logging_out) return
        val route = route_for_tab(tab_id) ?: return
        state = state.copy(
            selected_tab = tab_id,
            navigation_target = route
        )
    }
}
