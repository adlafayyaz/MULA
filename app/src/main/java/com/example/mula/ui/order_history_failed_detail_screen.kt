package com.example.mula.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.default_divider_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.viewmodel.OrderHistoryFailedDetailScreenEvent
import com.example.mula.viewmodel.OrderHistoryFailedDetailViewModel

data class OrderHistoryFailedDetailScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun OrderHistoryFailedDetailScreenRoute(
    order_id: String = "0001",
    on_back: () -> Unit = {},
    viewModel: OrderHistoryFailedDetailViewModel = viewModel()
) {
    OrderHistoryFailedDetailScreen(state = viewModel.state, order_id = order_id, on_back = on_back) {
        viewModel.on_event(OrderHistoryFailedDetailScreenEvent.RetryRequested)
    }
}

@Composable
fun OrderHistoryFailedDetailScreen(
    state: OrderHistoryFailedDetailScreenState,
    order_id: String = "0001",
    on_back: () -> Unit = {},
    onRetry: () -> Unit = {}
) {
    Stage4ABackground(modifier = Modifier.fillMaxSize()) {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)
        ) {
            CommerceScreenHeader(title = "PEMBELIAN GAGAL", on_back_click = on_back)
            HorizontalDivider(color = default_divider_color)
            Text("Pesanan anda dibatalkan", style = MaterialTheme.typography.titleMedium, color = body_text_color)
            Text("Maaf, pesanan Anda tidak dapat diproses karena gerai yang Anda pilih sedang tutup selama proses pemesanan berlangsung.", style = MaterialTheme.typography.bodyMedium, color = body_text_color)
            DetailSectionTitle("PESANAN DIANTAR DARI")
            LocationRow("MULA Cirendeu")
            DetailMetaRow("Pemesanan via Aplikasi", "21:54")
            DetailMetaRow("Rabu, 11 Juni 2025", "")
            DetailSectionTitle("Lokasi Pengiriman:")
            LocationRow("New Grand Cirendeu Royal Residence")
            DetailSectionTitle("PESANANMU")
            OrderItemCardRow("Creamy Latte", "Mungil, Dingin, Saus Karamel", "Rp. 22.000")
            VoucherSectionRow("MULAi Aja Dulu! Dis..")
            SurfaceBlock {
                DetailSectionTitle("DETAIL PEMBAYARAN")
                DetailMetaRow("Harga", "Rp. 22.000")
                DetailMetaRow("Biaya Layanan", "Rp. 3.000")
                DetailMetaRow("Total Pengembalian Dana", "Rp. 25.000")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun OrderHistoryFailedDetailScreenPreview() {
    MulaTheme { OrderHistoryFailedDetailScreen(state = OrderHistoryFailedDetailScreenState()) }
}
