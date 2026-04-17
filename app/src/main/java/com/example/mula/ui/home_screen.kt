package com.example.mula.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.viewmodel.HomeScreenEvent
import com.example.mula.viewmodel.HomeViewModel

data class HomeScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun HomeScreenRoute(viewModel: HomeViewModel = viewModel()) {
    HomeScreen(state = viewModel.state) { viewModel.on_event(HomeScreenEvent.RetryRequested) }
}

@Composable
fun HomeScreen(
    state: HomeScreenState,
    onRetry: () -> Unit = {}
) {
    ScreenScaffold(screenName = "home_screen", state = state, onRetry = onRetry)
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun HomeScreenPreview() {
    MulaTheme { HomeScreen(state = HomeScreenState()) }
}
