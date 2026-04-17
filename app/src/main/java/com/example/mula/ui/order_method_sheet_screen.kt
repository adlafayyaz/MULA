package com.example.mula.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.viewmodel.OrderMethodSheetScreenEvent
import com.example.mula.viewmodel.OrderMethodSheetViewModel

data class OrderMethodSheetScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun OrderMethodSheetScreenRoute(viewModel: OrderMethodSheetViewModel = viewModel()) {
    OrderMethodSheetScreen(state = viewModel.state) { viewModel.on_event(OrderMethodSheetScreenEvent.RetryRequested) }
}

@Composable
fun OrderMethodSheetScreen(
    state: OrderMethodSheetScreenState,
    onRetry: () -> Unit = {}
) {
    ScreenScaffold(screenName = "order_method_sheet", state = state, onRetry = onRetry)
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun OrderMethodSheetScreenPreview() {
    MulaTheme { OrderMethodSheetScreen(state = OrderMethodSheetScreenState()) }
}
