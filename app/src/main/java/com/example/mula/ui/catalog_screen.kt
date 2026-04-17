package com.example.mula.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.viewmodel.CatalogScreenEvent
import com.example.mula.viewmodel.CatalogViewModel

data class CatalogScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun CatalogScreenRoute(viewModel: CatalogViewModel = viewModel()) {
    CatalogScreen(state = viewModel.state) { viewModel.on_event(CatalogScreenEvent.RetryRequested) }
}

@Composable
fun CatalogScreen(
    state: CatalogScreenState,
    onRetry: () -> Unit = {}
) {
    ScreenScaffold(screenName = "catalog_screen", state = state, onRetry = onRetry)
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun CatalogScreenPreview() {
    MulaTheme { CatalogScreen(state = CatalogScreenState()) }
}
