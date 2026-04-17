package com.example.mula.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.components.PrimaryButton
import com.example.mula.ui.theme.MulaShapeTokens
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.ui.theme.screen_surface_color
import com.example.mula.viewmodel.QrisPaymentScreenEvent
import com.example.mula.viewmodel.QrisPaymentViewModel

data class QrisPaymentScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun QrisPaymentScreenRoute(
    payment_id: String = "#2506120001",
    viewModel: QrisPaymentViewModel = viewModel()
) {
    QrisPaymentScreen(state = viewModel.state, payment_id = payment_id) {
        viewModel.on_event(QrisPaymentScreenEvent.RetryRequested)
    }
}

@Composable
fun QrisPaymentScreen(
    state: QrisPaymentScreenState,
    payment_id: String = "#2506120001",
    onRetry: () -> Unit = {}
) {
    Stage4ABackground(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                androidx.compose.material3.IconButton(onClick = onRetry, modifier = Modifier.testTag("close_button")) { CloseIcon() }
            }
            SurfaceBlock {
                Text("Invoice : $payment_id", style = MaterialTheme.typography.titleMedium, modifier = Modifier.testTag("invoice_number_text"))
                DetailMetaRow("Tanggal Invoice", "12 Jun 2025")
                DetailMetaRow("Total Bayar", "Rp. 49.200")
                DetailMetaRow("Status", "Menunggu Pembayaran")
            }
            SurfaceBlock {
                Text(
                    text = "Download QR",
                    color = com.example.mula.ui.theme.headline_accent_color,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.align(Alignment.End).testTag("download_qr_button")
                )
                ArtworkPlaceholder(label = "QRIS", modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).height(220.dp), test_tag = "qris_image")
                Text("Bayar Sebelum 10:01", style = MaterialTheme.typography.bodyMedium, color = body_text_color, modifier = Modifier.align(Alignment.CenterHorizontally).testTag("payment_deadline_text"))
            }
            PrimaryButton(
                text = "Cek Status Pembayaran",
                on_click = onRetry,
                modifier = Modifier.fillMaxWidth().testTag("check_payment_status_button")
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 88.dp)
                .background(screen_surface_color, MulaShapeTokens.pill)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text("Berhasil disimpan ke galeri", style = MaterialTheme.typography.bodySmall, modifier = Modifier.testTag("download_success_overlay"))
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun QrisPaymentScreenPreview() {
    MulaTheme { QrisPaymentScreen(state = QrisPaymentScreenState()) }
}
