package com.example.mula.ui

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.components.PrimaryButton
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.muted_text_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.viewmodel.PaymentMethodScreenEvent
import com.example.mula.viewmodel.PaymentMethodViewModel

data class PaymentMethodScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun PaymentMethodScreenRoute(viewModel: PaymentMethodViewModel = viewModel()) {
    PaymentMethodScreen(state = viewModel.state) { viewModel.on_event(PaymentMethodScreenEvent.RetryRequested) }
}

@Composable
fun PaymentMethodScreen(
    state: PaymentMethodScreenState,
    onRetry: () -> Unit = {}
) {
    var selected by rememberSaveable { mutableStateOf("QRIS") }

    Stage4ABackground(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)
        ) {
            CommerceScreenHeader(title = "Pilih Pembayaran")
            Text("Metode Pembayaran", style = MaterialTheme.typography.titleMedium, color = body_text_color, modifier = Modifier.testTag("payment_method_section_title_text"))
            Text("Pilih metode pembayaranmu", style = MaterialTheme.typography.bodyMedium, color = body_text_color, modifier = Modifier.testTag("payment_method_section_description_text"))
            SurfaceBlock(tag = "payment_method_list") {
                PaymentOptionRow("QRIS", "Bayar pakai scan QR", selected == "QRIS")
                PaymentOptionRow("GoPay", "Bayar cepat dari dompet digital", selected == "GoPay")
                PaymentOptionRow("Tunai", "Bayar langsung di kasir", selected == "Tunai")
            }
            Text(
                text = "Kamu tidak dapat melakukan pembatalan atau perubahan apapun pada pemesanan setelah melakukan pembayaran",
                style = MaterialTheme.typography.bodySmall,
                color = muted_text_color
            )
            PrimaryButton(
                text = "Lanjutkan",
                on_click = onRetry,
                modifier = Modifier.fillMaxWidth().testTag("payment_continue_button")
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun PaymentMethodScreenPreview() {
    MulaTheme { PaymentMethodScreen(state = PaymentMethodScreenState()) }
}
