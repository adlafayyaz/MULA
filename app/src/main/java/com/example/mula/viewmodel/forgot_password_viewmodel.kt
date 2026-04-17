package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mula.data.repository.AuthRepository
import com.example.mula.navigation.ARG_PHONE_NUMBER
import com.example.mula.navigation.ROUTE_LOGIN
import com.example.mula.navigation.ROUTE_OTP_VERIFICATION
import kotlinx.coroutines.launch

data class ForgotPasswordScreenState(
    val is_loading: Boolean = false,
    val error_message: String? = null,
    val phone_number: String = "",
    val phone_number_error: String? = null,
    val is_submit_enabled: Boolean = false,
    val navigation_target: String? = null,
    val navigation_argument_map: Map<String, String> = emptyMap()
)

sealed class ForgotPasswordScreenEvent {
    data class OnPhoneNumberChanged(val value: String) : ForgotPasswordScreenEvent()
    data object OnSubmitClicked : ForgotPasswordScreenEvent()
    data object OnLoginClicked : ForgotPasswordScreenEvent()
    data object OnNavigationHandled : ForgotPasswordScreenEvent()
    data object OnErrorMessageDismissed : ForgotPasswordScreenEvent()
}

class ForgotPasswordViewModel : ViewModel() {
    private val auth_repository: AuthRepository = Stage5ARepositoryProvider.auth_repository

    var state by mutableStateOf(ForgotPasswordScreenState())
        private set

    fun on_event(event: ForgotPasswordScreenEvent) {
        when (event) {
            is ForgotPasswordScreenEvent.OnPhoneNumberChanged -> {
                state = state.copy(
                    phone_number = event.value,
                    phone_number_error = null,
                    is_submit_enabled = is_valid_indonesian_phone(event.value)
                )
            }

            ForgotPasswordScreenEvent.OnSubmitClicked -> submit()
            ForgotPasswordScreenEvent.OnLoginClicked -> if (!state.is_loading) state = state.copy(navigation_target = ROUTE_LOGIN)
            ForgotPasswordScreenEvent.OnNavigationHandled -> {
                state = state.copy(
                    navigation_target = null,
                    navigation_argument_map = emptyMap()
                )
            }

            ForgotPasswordScreenEvent.OnErrorMessageDismissed -> state = state.copy(error_message = null)
        }
    }

    private fun submit() {
        if (state.is_loading) return
        val phone_number = state.phone_number.trimmed()
        val phone_error = when {
            phone_number.isBlank() -> "Nomor handphone wajib diisi"
            !is_valid_indonesian_phone(phone_number) -> "Format nomor handphone tidak valid"
            else -> null
        }
        if (phone_error != null) {
            state = state.copy(
                phone_number_error = phone_error,
                is_submit_enabled = is_valid_indonesian_phone(phone_number)
            )
            return
        }
        viewModelScope.launch {
            state = state.copy(is_loading = true, error_message = null)
            auth_repository.request_password_reset(phone_number = phone_number)
                .onSuccess {
                    state = state.copy(
                        is_loading = false,
                        navigation_target = ROUTE_OTP_VERIFICATION,
                        navigation_argument_map = mapOf(ARG_PHONE_NUMBER to phone_number)
                    )
                }
                .onFailure { throwable ->
                    state = state.copy(
                        is_loading = false,
                        error_message = throwable.message ?: "Permintaan reset gagal"
                    )
                }
        }
    }
}
