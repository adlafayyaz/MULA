package com.example.mula.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.components.CustomBottomTabBar
import com.example.mula.ui.components.OrderHistoryCard
import com.example.mula.ui.components.order_history_tab_button
import com.example.mula.ui.theme.MulaDimens
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.background_app
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.viewmodel.OrderHistoryScreenEvent
import com.example.mula.viewmodel.OrderHistoryViewModel

data class OrderHistoryScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun OrderHistoryScreenRoute(
    on_success_detail: () -> Unit = {},
    on_failed_detail: () -> Unit = {},
    on_reorder: () -> Unit = {},
    on_tab_selected: (String) -> Unit = {},
    viewModel: OrderHistoryViewModel = viewModel()
) {
    OrderHistoryScreen(
        state = OrderHistoryScreenState(
            is_loading = viewModel.state.is_loading,
            error_message = viewModel.state.error_message
        ),
        on_success_detail = on_success_detail,
        on_failed_detail = on_failed_detail,
        on_reorder = on_reorder,
        on_tab_selected = on_tab_selected
    ) { viewModel.on_event(OrderHistoryScreenEvent.OnRetryClicked) }
}

@Composable
fun OrderHistoryScreen(
    state: OrderHistoryScreenState,
    on_success_detail: () -> Unit = {},
    on_failed_detail: () -> Unit = {},
    on_reorder: () -> Unit = {},
    on_tab_selected: (String) -> Unit = {},
    onRetry: () -> Unit = {}
) {
    val orders = listOf(
        listOf("BERHASIL", "MULA Tebet", "18 Juni 2025", "Pemesanan via aplikasi", "2x Es Kopi Susu", "Rp58.000", "false"),
        listOf("GAGAL", "MULA Sudirman", "17 Juni 2025", "Pemesanan via aplikasi", "1x Cappuccino", "Rp32.000", "true")
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background_app)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 24.dp,
                end = 24.dp,
                top = 24.dp,
                bottom = MulaDimens.bottom_tab_bar_height + 32.dp
            ),
            verticalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)
        ) {
            item {
                androidx.compose.material3.Text(
                    text = "Riwayat Pesanan",
                    style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                    color = com.example.mula.ui.theme.body_text_color,
                    modifier = Modifier.testTag("order_history_title_text")
                )
            }
            items(orders) { order ->
                OrderHistoryCard(
                    status_text = order[0],
                    branch_name = order[1],
                    date_text = order[2],
                    source_text = order[3],
                    item_summary_text = order[4],
                    total_text = order[5],
                    show_failed_badge = order[6].toBoolean(),
                    modifier = Modifier.testTag("order_history_card"),
                    on_card_click = if (order[6].toBoolean()) on_failed_detail else on_success_detail,
                    on_reorder_click = on_reorder
                )
            }
        }

        CustomBottomTabBar(
            selected_tab = order_history_tab_button,
            on_tab_selected = on_tab_selected,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .testTag("main_bottom_tab_bar")
        )
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun OrderHistoryScreenPreview() {
    MulaTheme { OrderHistoryScreen(state = OrderHistoryScreenState()) }
}
