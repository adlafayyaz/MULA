package com.example.mula.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.components.PrimaryButton
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.overlay_black_40
import com.example.mula.ui.theme.text_on_primary
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.viewmodel.OrderStatusPickupScreenEvent
import com.example.mula.viewmodel.OrderStatusPickupViewModel

data class OrderStatusPickupScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun OrderStatusPickupScreenRoute(
    order_id: String = "0001",
    viewModel: OrderStatusPickupViewModel = viewModel()
) {
    OrderStatusPickupScreen(state = viewModel.state, order_id = order_id) {
        viewModel.on_event(OrderStatusPickupScreenEvent.RetryRequested)
    }
}

@Composable
fun OrderStatusPickupScreen(
    state: OrderStatusPickupScreenState,
    order_id: String = "0001",
    onRetry: () -> Unit = {}
) {
    var show_overlay by rememberSaveable { mutableStateOf(true) }

    Stage4ABackground(modifier = Modifier.fillMaxSize()) {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)
        ) {
            CommerceScreenHeader(title = order_id)
            SurfaceBlock {
                Text("Status: Siap Diambil", style = MaterialTheme.typography.titleMedium)
            }
            SurfaceBlock {
                Text("Scan QR untuk mengambil pesananmu di kasir", style = MaterialTheme.typography.bodyMedium)
                PrimaryButton(
                    text = "Tampilkan QR",
                    on_click = { show_overlay = true },
                    modifier = Modifier.fillMaxWidth().testTag("show_pickup_qr_button")
                )
            }
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
        if (show_overlay) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(overlay_black_40)
                    .testTag("fullscreen_qr_overlay"),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.foundation.layout.Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)) {
                    ArtworkPlaceholder(label = "Pickup QR", modifier = Modifier.fillMaxWidth(0.72f).padding(horizontal = 24.dp))
                    Text("Ketuk layar dimana saja untuk kembali", color = text_on_primary)
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun OrderStatusPickupScreenPreview() {
    MulaTheme { OrderStatusPickupScreen(state = OrderStatusPickupScreenState()) }
}
