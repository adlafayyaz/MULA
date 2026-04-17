package com.example.mula.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.viewmodel.ResetPasswordScreenEvent
import com.example.mula.viewmodel.ResetPasswordViewModel

data class ResetPasswordScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun ResetPasswordScreenRoute(viewModel: ResetPasswordViewModel = viewModel()) {
    ResetPasswordScreen(state = viewModel.state) { viewModel.on_event(ResetPasswordScreenEvent.RetryRequested) }
}

@Composable
fun ResetPasswordScreen(
    state: ResetPasswordScreenState,
    onRetry: () -> Unit = {}
) {
    ScreenScaffold(screenName = "reset_password_screen", state = state, onRetry = onRetry)
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun ResetPasswordScreenPreview() {
    MulaTheme { ResetPasswordScreen(state = ResetPasswordScreenState()) }
}
