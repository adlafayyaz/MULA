package com.example.mula.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.viewmodel.OrderStatusDeliveryScreenEvent
import com.example.mula.viewmodel.OrderStatusDeliveryViewModel

data class OrderStatusDeliveryScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun OrderStatusDeliveryScreenRoute(
    order_id: String = "0001",
    viewModel: OrderStatusDeliveryViewModel = viewModel()
) {
    OrderStatusDeliveryScreen(state = viewModel.state, order_id = order_id) {
        viewModel.on_event(OrderStatusDeliveryScreenEvent.RetryRequested)
    }
}

@Composable
fun OrderStatusDeliveryScreen(
    state: OrderStatusDeliveryScreenState,
    order_id: String = "0001",
    onRetry: () -> Unit = {}
) {
    Stage4ABackground(modifier = Modifier.fillMaxSize()) {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)
        ) {
            CommerceScreenHeader(title = order_id)
            DetailMetaRow("Status:", "Sedang disiapkan")
            MapPlaceholder(label = "Delivery Map", modifier = Modifier.fillMaxWidth().height(220.dp), test_tag = "delivery_map_view")
            DetailMetaRow("Pemesanan via Aplikasi", "11:40")
            DetailMetaRow("Selasa, 10 Juni 2025", "")
            DetailSectionTitle("Pesananmu")
            OrderItemCardRow("Creamy Latte", "Mungil, Dingin, Saus Karamel", "Rp. 22.000")
            VoucherSectionRow("MULAi Aja Dulu! Dis..")
            SurfaceBlock {
                DetailSectionTitle("Detail Pembayaran")
                DetailMetaRow("Total", "Rp. 21.920")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun OrderStatusDeliveryScreenPreview() {
    MulaTheme { OrderStatusDeliveryScreen(state = OrderStatusDeliveryScreenState()) }
}
