package com.example.mula.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.components.RatingStarRow
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.default_divider_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.viewmodel.OrderHistorySuccessDetailScreenEvent
import com.example.mula.viewmodel.OrderHistorySuccessDetailViewModel

data class OrderHistorySuccessDetailScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun OrderHistorySuccessDetailScreenRoute(
    order_id: String = "0001",
    on_back: () -> Unit = {},
    viewModel: OrderHistorySuccessDetailViewModel = viewModel()
) {
    OrderHistorySuccessDetailScreen(state = viewModel.state, order_id = order_id, on_back = on_back) {
        viewModel.on_event(OrderHistorySuccessDetailScreenEvent.RetryRequested)
    }
}

@Composable
fun OrderHistorySuccessDetailScreen(
    state: OrderHistorySuccessDetailScreenState,
    order_id: String = "0001",
    on_back: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    Stage4ABackground(modifier = Modifier.fillMaxSize()) {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)
        ) {
            CommerceScreenHeader(title = "PEMBELIAN BERHASIL", on_back_click = on_back)
            HorizontalDivider(color = default_divider_color)
            Text("Penilaian", style = MaterialTheme.typography.titleMedium, modifier = Modifier.testTag("rating_section_title_text"))
            RatingStarRow(selected_rating = 5, on_rating_selected = {})
            Text("Terima kasih telah memesan kopi kami! Penilaian yang Anda berikan sangat berharga bagi kami untuk terus menyajikan rasa dan pelayanan terbaik.", style = MaterialTheme.typography.bodyMedium, color = body_text_color)
            DetailSectionTitle("PESANAN DIAMBIL DI")
            LocationRow("MULA Cirendeu")
            DetailMetaRow("Pemesanan via Aplikasi", "11:40")
            DetailMetaRow("Selasa, 10 Juni 2025", "")
            DetailSectionTitle("PESANANMU")
            OrderItemCardRow("Creamy Latte", "Mungil, Dingin, Saus Karamel", "Rp. 22.000")
            OrderItemCardRow("Matcha Depanmu", "Sedang, Dingin", "Rp. 24.000")
            VoucherSectionRow("MULAi Aja Dulu! Dis..")
            SurfaceBlock {
                DetailSectionTitle("DETAIL PEMBAYARAN")
                DetailMetaRow("Harga", "Rp. 46.000")
                DetailMetaRow("Biaya Layanan", "Rp. 3.000")
                DetailMetaRow("PPN 11%", "Rp. 5.060")
                DetailMetaRow("Total", "Rp. 54.060")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun OrderHistorySuccessDetailScreenPreview() {
    MulaTheme { OrderHistorySuccessDetailScreen(state = OrderHistorySuccessDetailScreenState()) }
}
