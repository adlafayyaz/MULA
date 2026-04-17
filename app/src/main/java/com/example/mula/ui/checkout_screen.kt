package com.example.mula.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.components.BranchSelectorCard
import com.example.mula.ui.components.CheckoutSummaryRow
import com.example.mula.ui.components.PrimaryButton
import com.example.mula.ui.components.SectionHeaderRow
import com.example.mula.ui.theme.MulaDimens
import com.example.mula.ui.theme.MulaShapeTokens
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.background_app
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.headline_accent_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.ui.theme.screen_surface_color
import com.example.mula.viewmodel.CheckoutScreenEvent
import com.example.mula.viewmodel.CheckoutViewModel

data class CheckoutScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun CheckoutScreenRoute(
    order_method: String = "delivery",
    viewModel: CheckoutViewModel = viewModel()
) {
    CheckoutScreen(
        state = viewModel.state,
        order_method = if (order_method.isBlank()) "delivery" else order_method
    ) { viewModel.on_event(CheckoutScreenEvent.RetryRequested) }
}

@Composable
fun CheckoutScreen(
    state: CheckoutScreenState,
    order_method: String = "delivery",
    onRetry: () -> Unit = {}
) {
    val is_delivery = order_method == "delivery"
    val voucher_text = if (is_delivery) "Pakai voucher untuk lebih hemat" else "MULAi Aja Dulu! Diskon 25%"

    Box(modifier = Modifier.fillMaxSize().background(background_app)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 24.dp, end = 24.dp, top = 24.dp, bottom = MulaDimens.checkout_footer_min_height + 36.dp),
            verticalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)
        ) {
            item { CommerceScreenHeader(title = "Checkout") }
            item {
                MethodSummaryCard(
                    title = if (is_delivery) "Pesan Antar" else "Ambil Sendiri",
                    description = if (is_delivery) "Segera diantar ke lokasimu" else "Ambil ke toko tanpa antri"
                )
            }
            item {
                Text("Gerai Cabang", style = MaterialTheme.typography.titleMedium, color = body_text_color, modifier = Modifier.testTag("branch_section_title_text"))
            }
            item {
                BranchSelectorCard(
                    branch_name = "MULA Cirendeu",
                    branch_distance_text = "1,2 km",
                    on_click = onRetry
                )
            }
            if (is_delivery) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(mula_spacing.sm.dp)) {
                        Text("Pengantaran", style = MaterialTheme.typography.titleMedium, color = body_text_color, modifier = Modifier.testTag("delivery_address_section_title_text"))
                        SurfaceBlock(tag = "delivery_address_text") { LocationRow("Lokasi New Grand Cirendeu Royal") }
                    }
                }
            }
            item {
                SurfaceBlock(tag = "eco_bag_row") {
                    DetailMetaRow(left = "Eco Bag", right = "Tambah")
                }
            }
            item {
                SurfaceBlock(tag = "voucher_row") {
                    DetailMetaRow(left = "Voucher", right = voucher_text)
                }
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(mula_spacing.sm.dp)) {
                    SectionHeaderRow(title = "Detail Pesanan")
                    OrderItemCardRow("Creamy Latte", "Mungil, Dingin, Saus Karamel", "Rp. 22.000")
                }
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth().background(screen_surface_color, MulaShapeTokens.large).padding(mula_spacing.md.dp),
                    verticalArrangement = Arrangement.spacedBy(mula_spacing.sm.dp)
                ) {
                    SectionHeaderRow(title = "Total Pembayaran")
                    CheckoutSummaryRow("Harga", "Rp. 22.000")
                    CheckoutSummaryRow("Biaya Layanan", "Rp. 3.000")
                    CheckoutSummaryRow("PPN 11%", "Rp. 2.420")
                    CheckoutSummaryRow("Potongan Harga", "-Rp. 5.500")
                    CheckoutSummaryRow("Total", "Rp. 21.920", is_emphasized = true)
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(screen_surface_color)
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            androidx.compose.foundation.layout.Column(verticalArrangement = Arrangement.spacedBy(mula_spacing.sm.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(mula_spacing.sm.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("o", color = headline_accent_color)
                    Text("Transaksi ini memberimu 10 MULA koin", style = MaterialTheme.typography.bodySmall, color = body_text_color)
                }
                PrimaryButton(
                    text = "Lanjut ke Pembayaran",
                    on_click = onRetry,
                    modifier = Modifier.fillMaxWidth().testTag("continue_to_payment_button")
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun CheckoutScreenPreview() {
    MulaTheme { CheckoutScreen(state = CheckoutScreenState()) }
}
