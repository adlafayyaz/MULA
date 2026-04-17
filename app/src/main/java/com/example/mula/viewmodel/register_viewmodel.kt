package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mula.data.repository.AuthRepository
import com.example.mula.navigation.ROUTE_LOGIN
import kotlinx.coroutines.launch

data class RegisterScreenState(
    val is_loading: Boolean = false,
    val error_message: String? = null,
    val username: String = "",
    val phone_number: String = "",
    val selected_gender: String = "",
    val birth_date: String = "",
    val password: String = "",
    val confirm_password: String = "",
    val is_password_visible: Boolean = false,
    val is_confirm_password_visible: Boolean = false,
    val username_error: String? = null,
    val phone_number_error: String? = null,
    val gender_error: String? = null,
    val birth_date_error: String? = null,
    val password_error: String? = null,
    val confirm_password_error: String? = null,
    val is_register_enabled: Boolean = false,
    val navigation_target: String? = null,
    val success_message: String? = null
)

sealed class RegisterScreenEvent {
    data class OnUsernameChanged(val value: String) : RegisterScreenEvent()
    data class OnPhoneNumberChanged(val value: String) : RegisterScreenEvent()
    data class OnGenderSelected(val value: String) : RegisterScreenEvent()
    data class OnBirthDateSelected(val value: String) : RegisterScreenEvent()
    data class OnPasswordChanged(val value: String) : RegisterScreenEvent()
    data class OnConfirmPasswordChanged(val value: String) : RegisterScreenEvent()
    data object OnPasswordVisibilityToggled : RegisterScreenEvent()
    data object OnConfirmPasswordVisibilityToggled : RegisterScreenEvent()
    data object OnRegisterClicked : RegisterScreenEvent()
    data object OnLoginClicked : RegisterScreenEvent()
    data object OnNavigationHandled : RegisterScreenEvent()
    data object OnSuccessMessageDismissed : RegisterScreenEvent()
    data object OnErrorMessageDismissed : RegisterScreenEvent()
}

class RegisterViewModel : ViewModel() {
    private val auth_repository: AuthRepository = Stage5ARepositoryProvider.auth_repository

    var state by mutableStateOf(RegisterScreenState())
        private set

    fun on_event(event: RegisterScreenEvent) {
        when (event) {
            is RegisterScreenEvent.OnUsernameChanged -> {
                state = state.copy(
                    username = event.value,
                    username_error = null,
                    is_register_enabled = compute_register_enabled(
                        username = event.value,
                        phone_number = state.phone_number,
                        gender = state.selected_gender,
                        birth_date = state.birth_date,
                        password = state.password,
                        confirm_password = state.confirm_password
                    )
                )
            }

            is RegisterScreenEvent.OnPhoneNumberChanged -> {
                state = state.copy(
                    phone_number = event.value,
                    phone_number_error = null,
                    is_register_enabled = compute_register_enabled(
                        username = state.username,
                        phone_number = event.value,
                        gender = state.selected_gender,
                        birth_date = state.birth_date,
                        password = state.password,
                        confirm_password = state.confirm_password
                    )
                )
            }

            is RegisterScreenEvent.OnGenderSelected -> {
                state = state.copy(
                    selected_gender = event.value,
                    gender_error = null,
                    is_register_enabled = compute_register_enabled(
                        username = state.username,
                        phone_number = state.phone_number,
                        gender = event.value,
                        birth_date = state.birth_date,
                        password = state.password,
                        confirm_password = state.confirm_password
                    )
                )
            }

            is RegisterScreenEvent.OnBirthDateSelected -> {
                state = state.copy(
                    birth_date = event.value,
                    birth_date_error = null,
                    is_register_enabled = compute_register_enabled(
                        username = state.username,
                        phone_number = state.phone_number,
                        gender = state.selected_gender,
                        birth_date = event.value,
                        password = state.password,
                        confirm_password = state.confirm_password
                    )
                )
            }

            is RegisterScreenEvent.OnPasswordChanged -> {
                state = state.copy(
                    password = event.value,
                    password_error = null,
                    confirm_password_error = null,
                    is_register_enabled = compute_register_enabled(
                        username = state.username,
                        phone_number = state.phone_number,
                        gender = state.selected_gender,
                        birth_date = state.birth_date,
                        password = event.value,
                        confirm_password = state.confirm_password
                    )
                )
            }

            is RegisterScreenEvent.OnConfirmPasswordChanged -> {
                state = state.copy(
                    confirm_password = event.value,
                    confirm_password_error = null,
                    is_register_enabled = compute_register_enabled(
                        username = state.username,
                        phone_number = state.phone_number,
                        gender = state.selected_gender,
                        birth_date = state.birth_date,
                        password = state.password,
                        confirm_password = event.value
                    )
                )
            }

            RegisterScreenEvent.OnPasswordVisibilityToggled -> {
                state = state.copy(is_password_visible = !state.is_password_visible)
            }

            RegisterScreenEvent.OnConfirmPasswordVisibilityToggled -> {
                state = state.copy(is_confirm_password_visible = !state.is_confirm_password_visible)
            }

            RegisterScreenEvent.OnRegisterClicked -> submit_register()
            RegisterScreenEvent.OnLoginClicked -> if (!state.is_loading) state = state.copy(navigation_target = ROUTE_LOGIN)
            RegisterScreenEvent.OnNavigationHandled -> state = state.copy(navigation_target = null)
            RegisterScreenEvent.OnSuccessMessageDismissed -> state = state.copy(success_message = null)
            RegisterScreenEvent.OnErrorMessageDismissed -> state = state.copy(error_message = null)
        }
    }

    private fun submit_register() {
        if (state.is_loading) return
        val username = state.username.trimmed()
        val phone_number = state.phone_number.trimmed()
        val gender = state.selected_gender.trimmed()
        val birth_date = state.birth_date.trimmed()
        val password = state.password
        val confirm_password = state.confirm_password

        val username_error = when {
            username.isBlank() -> "Nama pengguna wajib diisi"
            username.length < 3 -> "Nama pengguna minimal 3 karakter"
            else -> null
        }
        val phone_error = when {
            phone_number.isBlank() -> "Nomor handphone wajib diisi"
            !is_valid_indonesian_phone(phone_number) -> "Format nomor handphone tidak valid"
            else -> null
        }
        val gender_error = if (gender.isBlank()) "Jenis kelamin wajib dipilih" else null
        val birth_date_error = when {
            birth_date.isBlank() -> "Tanggal lahir wajib diisi"
            !is_not_future_date(birth_date) -> "Tanggal lahir tidak boleh di masa depan"
            else -> null
        }
        val password_error = when {
            password.isBlank() -> "Kata sandi wajib diisi"
            password.length < 8 -> "Kata sandi minimal 8 karakter"
            else -> null
        }
        val confirm_error = when {
            confirm_password.isBlank() -> "Konfirmasi kata sandi wajib diisi"
            confirm_password != password -> "Konfirmasi kata sandi harus sama"
            else -> null
        }
        if (
            username_error != null ||
            phone_error != null ||
            gender_error != null ||
            birth_date_error != null ||
            password_error != null ||
            confirm_error != null
        ) {
            state = state.copy(
                username_error = username_error,
                phone_number_error = phone_error,
                gender_error = gender_error,
                birth_date_error = birth_date_error,
                password_error = password_error,
                confirm_password_error = confirm_error,
                is_register_enabled = compute_register_enabled(
                    username = username,
                    phone_number = phone_number,
                    gender = gender,
                    birth_date = birth_date,
                    password = password,
                    confirm_password = confirm_password
                )
            )
            return
        }

        viewModelScope.launch {
            state = state.copy(is_loading = true, error_message = null)
            auth_repository.register(
                username = username,
                phone_number = phone_number,
                gender = gender,
                birth_date = birth_date,
                password = password
            ).onSuccess {
                state = state.copy(
                    is_loading = false,
                    success_message = "Pendaftaran berhasil",
                    navigation_target = ROUTE_LOGIN
                )
            }.onFailure { throwable ->
                state = state.copy(
                    is_loading = false,
                    error_message = throwable.message ?: "Pendaftaran gagal"
                )
            }
        }
    }

    private fun compute_register_enabled(
        username: String,
        phone_number: String,
        gender: String,
        birth_date: String,
        password: String,
        confirm_password: String
    ): Boolean = username.trimmed().length >= 3 &&
        is_valid_indonesian_phone(phone_number) &&
        gender.trimmed().isNotBlank() &&
        is_not_future_date(birth_date) &&
        password.length >= 8 &&
        confirm_password == password &&
        confirm_password.isNotBlank()
}
