package com.example.mula.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.viewmodel.LoginScreenEvent
import com.example.mula.viewmodel.LoginViewModel

data class LoginScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun LoginScreenRoute(viewModel: LoginViewModel = viewModel()) {
    LoginScreen(state = viewModel.state) { viewModel.on_event(LoginScreenEvent.RetryRequested) }
}

@Composable
fun LoginScreen(
    state: LoginScreenState,
    onRetry: () -> Unit = {}
) {
    ScreenScaffold(screenName = "login_screen", state = state, onRetry = onRetry)
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun LoginScreenPreview() {
    MulaTheme { LoginScreen(state = LoginScreenState()) }
}
