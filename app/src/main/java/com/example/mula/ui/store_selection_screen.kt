package com.example.mula.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.viewmodel.StoreSelectionScreenEvent
import com.example.mula.viewmodel.StoreSelectionViewModel

data class StoreSelectionScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun StoreSelectionScreenRoute(viewModel: StoreSelectionViewModel = viewModel()) {
    StoreSelectionScreen(state = viewModel.state) { viewModel.on_event(StoreSelectionScreenEvent.RetryRequested) }
}

@Composable
fun StoreSelectionScreen(
    state: StoreSelectionScreenState,
    onRetry: () -> Unit = {}
) {
    ScreenScaffold(screenName = "store_selection_screen", state = state, onRetry = onRetry)
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun StoreSelectionScreenPreview() {
    MulaTheme { StoreSelectionScreen(state = StoreSelectionScreenState()) }
}
