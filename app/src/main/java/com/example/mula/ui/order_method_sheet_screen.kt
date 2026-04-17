package com.example.mula.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.default_divider_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.viewmodel.OrderMethodSheetScreenEvent
import com.example.mula.viewmodel.OrderMethodSheetViewModel

data class OrderMethodSheetScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun OrderMethodSheetScreenRoute(viewModel: OrderMethodSheetViewModel = viewModel()) {
    OrderMethodSheetScreen(state = viewModel.state) { viewModel.on_event(OrderMethodSheetScreenEvent.RetryRequested) }
}

@Composable
fun OrderMethodSheetScreen(
    state: OrderMethodSheetScreenState,
    onRetry: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize().background(com.example.mula.ui.theme.overlay_gray_40)) {
        SurfaceBlock(
            modifier = Modifier.align(Alignment.BottomCenter),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(top = mula_spacing.sm.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(6.dp)
                        .fillMaxWidth(0.22f)
                        .background(default_divider_color, CircleShape)
                )
                Text(
                    text = "Pilih Metode Pemesanan",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.testTag("order_method_sheet_title_text")
                )
                MethodSummaryCard("Pesan Antar", "Segera diantar ke lokasimu")
                MethodSummaryCard("Ambil Sendiri", "Ambil ke toko tanpa antri")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun OrderMethodSheetScreenPreview() {
    MulaTheme { OrderMethodSheetScreen(state = OrderMethodSheetScreenState()) }
}
