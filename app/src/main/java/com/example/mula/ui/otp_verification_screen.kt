package com.example.mula.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.viewmodel.OtpVerificationScreenEvent
import com.example.mula.viewmodel.OtpVerificationViewModel

data class OtpVerificationScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun OtpVerificationScreenRoute(viewModel: OtpVerificationViewModel = viewModel()) {
    OtpVerificationScreen(state = viewModel.state) { viewModel.on_event(OtpVerificationScreenEvent.RetryRequested) }
}

@Composable
fun OtpVerificationScreen(
    state: OtpVerificationScreenState,
    onRetry: () -> Unit = {}
) {
    ScreenScaffold(screenName = "otp_verification_screen", state = state, onRetry = onRetry)
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun OtpVerificationScreenPreview() {
    MulaTheme { OtpVerificationScreen(state = OtpVerificationScreenState()) }
}
