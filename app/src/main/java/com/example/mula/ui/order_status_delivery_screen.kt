package com.example.mula.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.viewmodel.OrderStatusDeliveryScreenEvent
import com.example.mula.viewmodel.OrderStatusDeliveryViewModel

data class OrderStatusDeliveryScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun OrderStatusDeliveryScreenRoute(viewModel: OrderStatusDeliveryViewModel = viewModel()) {
    OrderStatusDeliveryScreen(state = viewModel.state) { viewModel.on_event(OrderStatusDeliveryScreenEvent.RetryRequested) }
}

@Composable
fun OrderStatusDeliveryScreen(
    state: OrderStatusDeliveryScreenState,
    onRetry: () -> Unit = {}
) {
    ScreenScaffold(screenName = "order_status_delivery_screen", state = state, onRetry = onRetry)
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun OrderStatusDeliveryScreenPreview() {
    MulaTheme { OrderStatusDeliveryScreen(state = OrderStatusDeliveryScreenState()) }
}
