package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mula.data.repository.AuthRepository
import com.example.mula.navigation.ROUTE_FORGOT_PASSWORD
import com.example.mula.navigation.ROUTE_LOGIN
import kotlinx.coroutines.launch

data class ResetPasswordScreenState(
    val is_loading: Boolean = false,
    val error_message: String? = null,
    val new_password: String = "",
    val confirm_password: String = "",
    val is_new_password_visible: Boolean = false,
    val is_confirm_password_visible: Boolean = false,
    val new_password_error: String? = null,
    val confirm_password_error: String? = null,
    val is_submit_enabled: Boolean = false,
    val navigation_target: String? = null,
    val clear_back_stack_to_route: String? = null
)

sealed class ResetPasswordScreenEvent {
    data class OnNewPasswordChanged(val value: String) : ResetPasswordScreenEvent()
    data class OnConfirmPasswordChanged(val value: String) : ResetPasswordScreenEvent()
    data object OnNewPasswordVisibilityToggled : ResetPasswordScreenEvent()
    data object OnConfirmPasswordVisibilityToggled : ResetPasswordScreenEvent()
    data object OnSubmitClicked : ResetPasswordScreenEvent()
    data object OnNavigationHandled : ResetPasswordScreenEvent()
    data object OnErrorMessageDismissed : ResetPasswordScreenEvent()
}

object ResetPasswordSessionStore {
    var reset_token: String? = null
}

class ResetPasswordViewModel : ViewModel() {
    private val auth_repository: AuthRepository = Stage5ARepositoryProvider.auth_repository

    var state by mutableStateOf(ResetPasswordScreenState())
        private set

    fun on_event(event: ResetPasswordScreenEvent) {
        when (event) {
            is ResetPasswordScreenEvent.OnNewPasswordChanged -> {
                state = state.copy(
                    new_password = event.value,
                    new_password_error = null,
                    confirm_password_error = null,
                    is_submit_enabled = is_submit_enabled(
                        new_password = event.value,
                        confirm_password = state.confirm_password
                    )
                )
            }

            is ResetPasswordScreenEvent.OnConfirmPasswordChanged -> {
                state = state.copy(
                    confirm_password = event.value,
                    confirm_password_error = null,
                    is_submit_enabled = is_submit_enabled(
                        new_password = state.new_password,
                        confirm_password = event.value
                    )
                )
            }

            ResetPasswordScreenEvent.OnNewPasswordVisibilityToggled -> {
                state = state.copy(is_new_password_visible = !state.is_new_password_visible)
            }

            ResetPasswordScreenEvent.OnConfirmPasswordVisibilityToggled -> {
                state = state.copy(is_confirm_password_visible = !state.is_confirm_password_visible)
            }

            ResetPasswordScreenEvent.OnSubmitClicked -> submit()
            ResetPasswordScreenEvent.OnNavigationHandled -> {
                state = state.copy(
                    navigation_target = null,
                    clear_back_stack_to_route = null
                )
            }

            ResetPasswordScreenEvent.OnErrorMessageDismissed -> state = state.copy(error_message = null)
        }
    }

    private fun submit() {
        if (state.is_loading) return
        val new_password_error = when {
            state.new_password.isBlank() -> "Kata sandi wajib diisi"
            state.new_password.length < 8 -> "Kata sandi minimal 8 karakter"
            else -> null
        }
        val confirm_error = when {
            state.confirm_password.isBlank() -> "Konfirmasi kata sandi wajib diisi"
            state.confirm_password != state.new_password -> "Konfirmasi kata sandi harus sama"
            else -> null
        }
        if (new_password_error != null || confirm_error != null) {
            state = state.copy(
                new_password_error = new_password_error,
                confirm_password_error = confirm_error,
                is_submit_enabled = is_submit_enabled(
                    new_password = state.new_password,
                    confirm_password = state.confirm_password
                )
            )
            return
        }
        val token = ResetPasswordSessionStore.reset_token
        if (token.isNullOrBlank()) {
            state = state.copy(error_message = "Token reset tidak tersedia")
            return
        }
        viewModelScope.launch {
            state = state.copy(is_loading = true, error_message = null)
            auth_repository.reset_password(
                reset_token = token,
                new_password = state.new_password
            ).onSuccess {
                ResetPasswordSessionStore.reset_token = null
                state = state.copy(
                    is_loading = false,
                    navigation_target = ROUTE_LOGIN,
                    clear_back_stack_to_route = ROUTE_FORGOT_PASSWORD
                )
            }.onFailure { throwable ->
                state = state.copy(
                    is_loading = false,
                    error_message = throwable.message ?: "Reset kata sandi gagal"
                )
            }
        }
    }

    private fun is_submit_enabled(new_password: String, confirm_password: String): Boolean =
        new_password.length >= 8 && confirm_password == new_password && confirm_password.isNotBlank()
}
