package com.example.mula.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.viewmodel.ProductDetailScreenEvent
import com.example.mula.viewmodel.ProductDetailViewModel

data class ProductDetailScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun ProductDetailScreenRoute(viewModel: ProductDetailViewModel = viewModel()) {
    ProductDetailScreen(state = viewModel.state) { viewModel.on_event(ProductDetailScreenEvent.RetryRequested) }
}

@Composable
fun ProductDetailScreen(
    state: ProductDetailScreenState,
    onRetry: () -> Unit = {}
) {
    ScreenScaffold(screenName = "product_detail_screen", state = state, onRetry = onRetry)
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun ProductDetailScreenPreview() {
    MulaTheme { ProductDetailScreen(state = ProductDetailScreenState()) }
}
