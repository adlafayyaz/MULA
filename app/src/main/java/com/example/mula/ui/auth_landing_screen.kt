package com.example.mula.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.viewmodel.AuthLandingScreenEvent
import com.example.mula.viewmodel.AuthLandingViewModel

data class AuthLandingScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun AuthLandingScreenRoute(viewModel: AuthLandingViewModel = viewModel()) {
    AuthLandingScreen(state = viewModel.state) { viewModel.on_event(AuthLandingScreenEvent.RetryRequested) }
}

@Composable
fun AuthLandingScreen(
    state: AuthLandingScreenState,
    onRetry: () -> Unit = {}
) {
    ScreenScaffold(screenName = "auth_landing_screen", state = state, onRetry = onRetry)
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun AuthLandingScreenPreview() {
    MulaTheme { AuthLandingScreen(state = AuthLandingScreenState()) }
}
