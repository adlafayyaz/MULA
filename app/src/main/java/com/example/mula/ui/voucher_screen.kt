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
import com.example.mula.ui.components.VoucherCard
import com.example.mula.ui.components.voucher_tab_button
import com.example.mula.ui.theme.MulaDimens
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.background_app
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.viewmodel.VoucherScreenEvent
import com.example.mula.viewmodel.VoucherViewModel

data class VoucherScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun VoucherScreenRoute(
    entry_source: String = "tab",
    on_back: () -> Unit = {},
    on_voucher_selected: (String) -> Unit = {},
    viewModel: VoucherViewModel = viewModel()
) {
    VoucherScreen(
        state = VoucherScreenState(
            is_loading = viewModel.state.is_loading,
            error_message = viewModel.state.error_message
        ),
        entry_source = if (entry_source.isBlank()) "tab" else entry_source,
        on_back = on_back,
        on_voucher_selected = on_voucher_selected
    ) { viewModel.on_event(VoucherScreenEvent.OnRetryClicked) }
}

@Composable
fun VoucherScreen(
    state: VoucherScreenState,
    entry_source: String = "tab",
    on_back: () -> Unit = {},
    on_voucher_selected: (String) -> Unit = {},
    onRetry: () -> Unit = {}
) {
    val vouchers = listOf(
        Triple("PerMULAan! Diskon 50%", "Voucher khusus untuk pengguna baru", "18 Juni 2025"),
        Triple("MULAi Aja Dulu! Diskon 25%", "Maksimum Rp25rb", "16 Juni 2025"),
        Triple("Beli 1 Gratis 1", "Pembelian Minimal Rp35rb", "14 Juni 2025"),
        Triple("GoPay Diskon 40%", "Maksimum Rp35rb", "13 Juni 2025")
    )
    val show_tab_bar = entry_source == "tab"

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
                bottom = if (show_tab_bar) MulaDimens.bottom_tab_bar_height + 32.dp else 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)
        ) {
            item {
                ScreenHeaderRow(
                    title = "Voucher",
                    show_back_button = !show_tab_bar,
                    on_back_click = on_back
                )
            }
            items(vouchers) { voucher ->
                VoucherCard(
                    title = voucher.first,
                    description = "${voucher.second}\nBerlaku sampai ${voucher.third}",
                    expiry_date_text = "",
                    action_text = "Pakai",
                    modifier = Modifier.testTag("voucher_card"),
                    on_action_click = { on_voucher_selected(voucher.first) }
                )
            }
        }

        if (show_tab_bar) {
            CustomBottomTabBar(
                selected_tab = voucher_tab_button,
                on_tab_selected = {},
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .testTag("main_bottom_tab_bar")
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun VoucherScreenPreview() {
    MulaTheme { VoucherScreen(state = VoucherScreenState()) }
}
