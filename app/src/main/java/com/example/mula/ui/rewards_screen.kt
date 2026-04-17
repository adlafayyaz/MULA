package com.example.mula.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.viewmodel.RewardsScreenEvent
import com.example.mula.viewmodel.RewardsViewModel

data class RewardsScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun RewardsScreenRoute(viewModel: RewardsViewModel = viewModel()) {
    RewardsScreen(state = viewModel.state) { viewModel.on_event(RewardsScreenEvent.RetryRequested) }
}

@Composable
fun RewardsScreen(
    state: RewardsScreenState,
    onRetry: () -> Unit = {}
) {
    ScreenScaffold(screenName = "rewards_screen", state = state, onRetry = onRetry)
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun RewardsScreenPreview() {
    MulaTheme { RewardsScreen(state = RewardsScreenState()) }
}
