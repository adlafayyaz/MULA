package com.example.mula.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.viewmodel.CheckoutScreenEvent
import com.example.mula.viewmodel.CheckoutViewModel

data class CheckoutScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun CheckoutScreenRoute(viewModel: CheckoutViewModel = viewModel()) {
    CheckoutScreen(state = viewModel.state) { viewModel.on_event(CheckoutScreenEvent.RetryRequested) }
}

@Composable
fun CheckoutScreen(
    state: CheckoutScreenState,
    onRetry: () -> Unit = {}
) {
    ScreenScaffold(screenName = "checkout_screen", state = state, onRetry = onRetry)
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun CheckoutScreenPreview() {
    MulaTheme { CheckoutScreen(state = CheckoutScreenState()) }
}
