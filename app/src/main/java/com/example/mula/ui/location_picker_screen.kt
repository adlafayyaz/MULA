package com.example.mula.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import com.example.mula.ui.components.PrimaryButton
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.viewmodel.LocationPickerScreenEvent
import com.example.mula.viewmodel.LocationPickerViewModel

data class LocationPickerScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun LocationPickerScreenRoute(
    on_back: () -> Unit = {},
    on_confirm: (String) -> Unit = {},
    viewModel: LocationPickerViewModel = viewModel()
) {
    LocationPickerScreen(state = viewModel.state, on_back = on_back, on_confirm = on_confirm) {
        viewModel.on_event(LocationPickerScreenEvent.RetryRequested)
    }
}

@Composable
fun LocationPickerScreen(
    state: LocationPickerScreenState,
    on_back: () -> Unit = {},
    on_confirm: (String) -> Unit = {},
    onRetry: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        MapPlaceholder(
            label = "Map",
            modifier = Modifier.fillMaxSize(),
            test_tag = "location_map_view"
        )
        CommerceScreenHeader(
            title = "Pilih Lokasimu",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 24.dp, vertical = 24.dp),
            on_back_click = on_back
        )
        SurfaceBlock(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(6.dp)
                    .fillMaxWidth(0.22f)
                    .align(Alignment.CenterHorizontally)
                    .background(com.example.mula.ui.theme.default_divider_color, CircleShape)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LocationRow(
                    text = "New Grand Cirendeu Royal Residence",
                    modifier = Modifier.weight(1f)
                )
                Text("⌕", style = MaterialTheme.typography.titleMedium, color = body_text_color)
            }
            PrimaryButton(
                text = "Konfirmasi Lokasi",
                on_click = { on_confirm("New Grand Cirendeu Royal Residence") },
                modifier = Modifier.fillMaxWidth().testTag("confirm_location_button")
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun LocationPickerScreenPreview() {
    MulaTheme { LocationPickerScreen(state = LocationPickerScreenState()) }
}
