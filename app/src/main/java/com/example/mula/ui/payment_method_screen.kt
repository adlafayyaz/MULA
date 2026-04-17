package com.example.mula.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.viewmodel.PaymentMethodScreenEvent
import com.example.mula.viewmodel.PaymentMethodViewModel

data class PaymentMethodScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun PaymentMethodScreenRoute(viewModel: PaymentMethodViewModel = viewModel()) {
    PaymentMethodScreen(state = viewModel.state) { viewModel.on_event(PaymentMethodScreenEvent.RetryRequested) }
}

@Composable
fun PaymentMethodScreen(
    state: PaymentMethodScreenState,
    onRetry: () -> Unit = {}
) {
    ScreenScaffold(screenName = "payment_method_screen", state = state, onRetry = onRetry)
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun PaymentMethodScreenPreview() {
    MulaTheme { PaymentMethodScreen(state = PaymentMethodScreenState()) }
}
