package com.example.mula.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.viewmodel.OrderHistoryScreenEvent
import com.example.mula.viewmodel.OrderHistoryViewModel

data class OrderHistoryScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun OrderHistoryScreenRoute(viewModel: OrderHistoryViewModel = viewModel()) {
    OrderHistoryScreen(state = viewModel.state) { viewModel.on_event(OrderHistoryScreenEvent.RetryRequested) }
}

@Composable
fun OrderHistoryScreen(
    state: OrderHistoryScreenState,
    onRetry: () -> Unit = {}
) {
    ScreenScaffold(screenName = "order_history_screen", state = state, onRetry = onRetry)
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun OrderHistoryScreenPreview() {
    MulaTheme { OrderHistoryScreen(state = OrderHistoryScreenState()) }
}
