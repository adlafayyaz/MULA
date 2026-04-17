package com.example.mula.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.viewmodel.OrderHistoryFailedDetailScreenEvent
import com.example.mula.viewmodel.OrderHistoryFailedDetailViewModel

data class OrderHistoryFailedDetailScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun OrderHistoryFailedDetailScreenRoute(viewModel: OrderHistoryFailedDetailViewModel = viewModel()) {
    OrderHistoryFailedDetailScreen(state = viewModel.state) { viewModel.on_event(OrderHistoryFailedDetailScreenEvent.RetryRequested) }
}

@Composable
fun OrderHistoryFailedDetailScreen(
    state: OrderHistoryFailedDetailScreenState,
    onRetry: () -> Unit = {}
) {
    ScreenScaffold(screenName = "order_history_failed_detail_screen", state = state, onRetry = onRetry)
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun OrderHistoryFailedDetailScreenPreview() {
    MulaTheme { OrderHistoryFailedDetailScreen(state = OrderHistoryFailedDetailScreenState()) }
}
