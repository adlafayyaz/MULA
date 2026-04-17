package com.example.mula.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.viewmodel.RegisterScreenEvent
import com.example.mula.viewmodel.RegisterViewModel

data class RegisterScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun RegisterScreenRoute(viewModel: RegisterViewModel = viewModel()) {
    RegisterScreen(state = viewModel.state) { viewModel.on_event(RegisterScreenEvent.RetryRequested) }
}

@Composable
fun RegisterScreen(
    state: RegisterScreenState,
    onRetry: () -> Unit = {}
) {
    ScreenScaffold(screenName = "register_screen", state = state, onRetry = onRetry)
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun RegisterScreenPreview() {
    MulaTheme { RegisterScreen(state = RegisterScreenState()) }
}
