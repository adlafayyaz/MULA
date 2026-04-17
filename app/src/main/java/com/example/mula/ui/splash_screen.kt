package com.example.mula.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.viewmodel.SplashScreenEvent
import com.example.mula.viewmodel.SplashViewModel
import com.example.mula.ui.theme.MulaTheme

data class SplashScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun SplashScreenRoute(viewModel: SplashViewModel = viewModel()) {
    SplashScreen(state = viewModel.state) { viewModel.on_event(SplashScreenEvent.RetryRequested) }
}

@Composable
fun SplashScreen(
    state: SplashScreenState,
    onRetry: () -> Unit = {}
) {
    ScreenScaffold(screenName = "splash_screen", state = state, onRetry = onRetry)
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun SplashScreenPreview() {
    MulaTheme { SplashScreen(state = SplashScreenState()) }
}
