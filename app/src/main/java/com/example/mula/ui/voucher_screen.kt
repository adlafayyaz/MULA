package com.example.mula.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.viewmodel.VoucherScreenEvent
import com.example.mula.viewmodel.VoucherViewModel

data class VoucherScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun VoucherScreenRoute(viewModel: VoucherViewModel = viewModel()) {
    VoucherScreen(state = viewModel.state) { viewModel.on_event(VoucherScreenEvent.RetryRequested) }
}

@Composable
fun VoucherScreen(
    state: VoucherScreenState,
    onRetry: () -> Unit = {}
) {
    ScreenScaffold(screenName = "voucher_screen", state = state, onRetry = onRetry)
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun VoucherScreenPreview() {
    MulaTheme { VoucherScreen(state = VoucherScreenState()) }
}
