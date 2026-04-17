package com.example.mula.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.viewmodel.QrisPaymentScreenEvent
import com.example.mula.viewmodel.QrisPaymentViewModel

data class QrisPaymentScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun QrisPaymentScreenRoute(viewModel: QrisPaymentViewModel = viewModel()) {
    QrisPaymentScreen(state = viewModel.state) { viewModel.on_event(QrisPaymentScreenEvent.RetryRequested) }
}

@Composable
fun QrisPaymentScreen(
    state: QrisPaymentScreenState,
    onRetry: () -> Unit = {}
) {
    ScreenScaffold(screenName = "qris_payment_screen", state = state, onRetry = onRetry)
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun QrisPaymentScreenPreview() {
    MulaTheme { QrisPaymentScreen(state = QrisPaymentScreenState()) }
}
