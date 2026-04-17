package com.example.mula.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.viewmodel.OrderStatusPickupScreenEvent
import com.example.mula.viewmodel.OrderStatusPickupViewModel

data class OrderStatusPickupScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun OrderStatusPickupScreenRoute(viewModel: OrderStatusPickupViewModel = viewModel()) {
    OrderStatusPickupScreen(state = viewModel.state) { viewModel.on_event(OrderStatusPickupScreenEvent.RetryRequested) }
}

@Composable
fun OrderStatusPickupScreen(
    state: OrderStatusPickupScreenState,
    onRetry: () -> Unit = {}
) {
    ScreenScaffold(screenName = "order_status_pickup_screen", state = state, onRetry = onRetry)
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun OrderStatusPickupScreenPreview() {
    MulaTheme { OrderStatusPickupScreen(state = OrderStatusPickupScreenState()) }
}
