package com.example.mula.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.viewmodel.ForgotPasswordScreenEvent
import com.example.mula.viewmodel.ForgotPasswordViewModel

data class ForgotPasswordScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun ForgotPasswordScreenRoute(viewModel: ForgotPasswordViewModel = viewModel()) {
    ForgotPasswordScreen(state = viewModel.state) { viewModel.on_event(ForgotPasswordScreenEvent.RetryRequested) }
}

@Composable
fun ForgotPasswordScreen(
    state: ForgotPasswordScreenState,
    onRetry: () -> Unit = {}
) {
    ScreenScaffold(screenName = "forgot_password_screen", state = state, onRetry = onRetry)
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun ForgotPasswordScreenPreview() {
    MulaTheme { ForgotPasswordScreen(state = ForgotPasswordScreenState()) }
}
