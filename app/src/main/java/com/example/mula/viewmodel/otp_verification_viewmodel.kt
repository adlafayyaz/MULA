package com.example.mula.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mula.data.repository.AuthRepository
import com.example.mula.navigation.ROUTE_RESET_PASSWORD
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class OtpVerificationScreenState(
    val is_loading: Boolean = false,
    val error_message: String? = null,
    val phone_number: String = "",
    val otp_digit_1: String = "",
    val otp_digit_2: String = "",
    val otp_digit_3: String = "",
    val otp_digit_4: String = "",
    val resend_seconds_remaining: Int = 30,
    val can_resend: Boolean = false,
    val navigation_target: String? = null,
    val is_verify_enabled: Boolean = false
)

sealed class OtpVerificationScreenEvent {
    data class OnOtpDigitChanged(val index: Int, val value: String) : OtpVerificationScreenEvent()
    data class OnBackspacePressed(val index: Int) : OtpVerificationScreenEvent()
    data object OnResendClicked : OtpVerificationScreenEvent()
    data object OnTimerTick : OtpVerificationScreenEvent()
    data object OnAutoVerifyTriggered : OtpVerificationScreenEvent()
    data object OnNavigationHandled : OtpVerificationScreenEvent()
    data object OnErrorMessageDismissed : OtpVerificationScreenEvent()
}

class OtpVerificationViewModel : ViewModel() {
    private val auth_repository: AuthRepository = Stage5ARepositoryProvider.auth_repository
    private var resend_timer_job: Job? = null

    var state by mutableStateOf(OtpVerificationScreenState())
        private set

    fun bind_phone_number(phone_number: String) {
        val normalized = phone_number.trimmed()
        if (normalized.isBlank() || state.phone_number == normalized) return
        state = state.copy(phone_number = normalized)
        start_resend_timer()
    }

    fun on_event(event: OtpVerificationScreenEvent) {
        when (event) {
            is OtpVerificationScreenEvent.OnOtpDigitChanged -> {
                val sanitized = event.value.filter(Char::isDigit).take(1)
                update_digit(index = event.index, value = sanitized)
                if (build_otp_code().length == 4) on_event(OtpVerificationScreenEvent.OnAutoVerifyTriggered)
            }

            is OtpVerificationScreenEvent.OnBackspacePressed -> handle_backspace(index = event.index)
            OtpVerificationScreenEvent.OnResendClicked -> resend_code()
            OtpVerificationScreenEvent.OnTimerTick -> tick_timer()
            OtpVerificationScreenEvent.OnAutoVerifyTriggered -> verify_otp()
            OtpVerificationScreenEvent.OnNavigationHandled -> state = state.copy(navigation_target = null)
            OtpVerificationScreenEvent.OnErrorMessageDismissed -> state = state.copy(error_message = null)
        }
    }

    private fun update_digit(index: Int, value: String) {
        val updated_state = when (index) {
            0 -> state.copy(otp_digit_1 = value)
            1 -> state.copy(otp_digit_2 = value)
            2 -> state.copy(otp_digit_3 = value)
            3 -> state.copy(otp_digit_4 = value)
            else -> state
        }
        state = updated_state.copy(
            error_message = null,
            is_verify_enabled = build_otp_code(
                digit_1 = updated_state.otp_digit_1,
                digit_2 = updated_state.otp_digit_2,
                digit_3 = updated_state.otp_digit_3,
                digit_4 = updated_state.otp_digit_4
            ).length == 4
        )
    }

    private fun handle_backspace(index: Int) {
        when (index.coerceIn(0, 3)) {
            3 -> update_digit(index = if (state.otp_digit_4.isBlank()) 2 else 3, value = "")
            2 -> update_digit(index = if (state.otp_digit_3.isBlank()) 1 else 2, value = "")
            1 -> update_digit(index = if (state.otp_digit_2.isBlank()) 0 else 1, value = "")
            else -> update_digit(index = 0, value = "")
        }
    }

    private fun resend_code() {
        if (state.is_loading || !state.can_resend || state.phone_number.isBlank()) return
        viewModelScope.launch {
            state = state.copy(is_loading = true, error_message = null)
            auth_repository.request_password_reset(phone_number = state.phone_number)
                .onSuccess {
                    state = state.copy(is_loading = false)
                    start_resend_timer()
                }
                .onFailure { throwable ->
                    state = state.copy(
                        is_loading = false,
                        error_message = throwable.message ?: "Gagal kirim ulang kode"
                    )
                }
        }
    }

    private fun verify_otp() {
        if (state.is_loading || build_otp_code().length != 4 || state.phone_number.isBlank()) return
        viewModelScope.launch {
            state = state.copy(is_loading = true, error_message = null)
            auth_repository.verify_otp(
                phone_number = state.phone_number,
                otp_code = build_otp_code()
            ).onSuccess { token ->
                ResetPasswordSessionStore.reset_token = token
                state = state.copy(
                    is_loading = false,
                    navigation_target = ROUTE_RESET_PASSWORD
                )
            }.onFailure { throwable ->
                state = state.copy(
                    is_loading = false,
                    error_message = throwable.message ?: "Kode OTP tidak valid"
                )
            }
        }
    }

    private fun tick_timer() {
        if (state.resend_seconds_remaining <= 0) {
            state = state.copy(can_resend = true)
            return
        }
        val remaining = (state.resend_seconds_remaining - 1).coerceAtLeast(0)
        state = state.copy(
            resend_seconds_remaining = remaining,
            can_resend = remaining == 0
        )
    }

    private fun start_resend_timer() {
        resend_timer_job?.cancel()
        state = state.copy(resend_seconds_remaining = 30, can_resend = false)
        resend_timer_job = viewModelScope.launch {
            while (state.resend_seconds_remaining > 0) {
                delay(1000)
                on_event(OtpVerificationScreenEvent.OnTimerTick)
            }
        }
    }

    private fun build_otp_code(
        digit_1: String = state.otp_digit_1,
        digit_2: String = state.otp_digit_2,
        digit_3: String = state.otp_digit_3,
        digit_4: String = state.otp_digit_4
    ): String = listOf(digit_1, digit_2, digit_3, digit_4).joinToString(separator = "")

    override fun onCleared() {
        resend_timer_job?.cancel()
        super.onCleared()
    }
}
