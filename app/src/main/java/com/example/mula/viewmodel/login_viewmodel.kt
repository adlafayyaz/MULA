package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mula.data.model.AppSession
import com.example.mula.data.repository.AuthRepository
import com.example.mula.data.repository.SessionRepository
import com.example.mula.navigation.ROUTE_AUTH_LANDING
import com.example.mula.navigation.ROUTE_FORGOT_PASSWORD
import com.example.mula.navigation.ROUTE_HOME
import com.example.mula.navigation.ROUTE_REGISTER
import kotlinx.coroutines.launch

data class LoginScreenState(
    val is_loading: Boolean = false,
    val error_message: String? = null,
    val username: String = "",
    val password: String = "",
    val is_password_visible: Boolean = false,
    val username_error: String? = null,
    val password_error: String? = null,
    val is_login_enabled: Boolean = false,
    val navigation_target: String? = null,
    val clear_back_stack_to_route: String? = null
)

sealed class LoginScreenEvent {
    data class OnUsernameChanged(val value: String) : LoginScreenEvent()
    data class OnPasswordChanged(val value: String) : LoginScreenEvent()
    data object OnPasswordVisibilityToggled : LoginScreenEvent()
    data object OnForgotPasswordClicked : LoginScreenEvent()
    data object OnLoginClicked : LoginScreenEvent()
    data object OnRegisterClicked : LoginScreenEvent()
    data object OnNavigationHandled : LoginScreenEvent()
    data object OnErrorMessageDismissed : LoginScreenEvent()
}

class LoginViewModel : ViewModel() {
    private val auth_repository: AuthRepository = Stage5ARepositoryProvider.auth_repository
    private val session_repository: SessionRepository = Stage5ARepositoryProvider.session_repository

    var state by mutableStateOf(LoginScreenState())
        private set

    fun on_event(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.OnUsernameChanged -> {
                state = state.copy(
                    username = event.value,
                    username_error = null,
                    is_login_enabled = is_login_enabled(
                        username = event.value,
                        password = state.password
                    )
                )
            }

            is LoginScreenEvent.OnPasswordChanged -> {
                state = state.copy(
                    password = event.value,
                    password_error = null,
                    is_login_enabled = is_login_enabled(
                        username = state.username,
                        password = event.value
                    )
                )
            }

            LoginScreenEvent.OnPasswordVisibilityToggled -> {
                state = state.copy(is_password_visible = !state.is_password_visible)
            }

            LoginScreenEvent.OnForgotPasswordClicked -> {
                if (!state.is_loading) state = state.copy(navigation_target = ROUTE_FORGOT_PASSWORD)
            }

            LoginScreenEvent.OnRegisterClicked -> {
                if (!state.is_loading) state = state.copy(navigation_target = ROUTE_REGISTER)
            }

            LoginScreenEvent.OnLoginClicked -> submit_login()

            LoginScreenEvent.OnNavigationHandled -> {
                state = state.copy(
                    navigation_target = null,
                    clear_back_stack_to_route = null
                )
            }

            LoginScreenEvent.OnErrorMessageDismissed -> state = state.copy(error_message = null)
        }
    }

    private fun submit_login() {
        if (state.is_loading) return
        val username = state.username.trimmed()
        val password = state.password
        val username_error = if (username.isBlank()) "Nama pengguna wajib diisi" else null
        val password_error = when {
            password.isBlank() -> "Kata sandi wajib diisi"
            password.length < 8 -> "Kata sandi minimal 8 karakter"
            else -> null
        }
        if (username_error != null || password_error != null) {
            state = state.copy(
                username_error = username_error,
                password_error = password_error,
                is_login_enabled = is_login_enabled(username = username, password = password)
            )
            return
        }
        viewModelScope.launch {
            state = state.copy(is_loading = true, error_message = null)
            auth_repository.login(username = username, password = password)
                .onSuccess { user ->
                    session_repository.save_session(
                        AppSession(
                            token = "token_${user.id}",
                            refresh_token = "refresh_${user.id}",
                            user_id = user.id,
                            is_logged_in = true,
                            is_first_launch = false
                        )
                    )
                    state = state.copy(
                        is_loading = false,
                        navigation_target = ROUTE_HOME,
                        clear_back_stack_to_route = ROUTE_AUTH_LANDING
                    )
                }
                .onFailure { throwable ->
                    state = state.copy(
                        is_loading = false,
                        error_message = throwable.message ?: "Login gagal"
                    )
                }
        }
    }

    private fun is_login_enabled(username: String, password: String): Boolean =
        username.trimmed().isNotBlank() && password.length >= 8
}
