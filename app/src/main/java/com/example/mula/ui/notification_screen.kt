package com.example.mula.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.viewmodel.NotificationScreenEvent
import com.example.mula.viewmodel.NotificationViewModel

data class NotificationScreenState(
    override val is_loading: Boolean = false,
    override val error_message: String? = null
) : ScreenStateContract

@Composable
fun NotificationScreenRoute(viewModel: NotificationViewModel = viewModel()) {
    NotificationScreen(state = viewModel.state) { viewModel.on_event(NotificationScreenEvent.RetryRequested) }
}

@Composable
fun NotificationScreen(
    state: NotificationScreenState,
    onRetry: () -> Unit = {}
) {
    Stage4ABackground(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(stage_4a_screen_padding),
            verticalArrangement = Arrangement.spacedBy(mula_spacing.lg.dp)
        ) {
            ScreenHeaderRow(title = "Notifikasi")
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("notification_list"),
                verticalArrangement = Arrangement.spacedBy(mula_spacing.md.dp)
            ) {
                NotificationCardBlock(
                    meta_text = "Info • 18 Juni 2025, 12:44",
                    title = "💸 Segera Pakai Mula Tokenmu!",
                    body = "Pakai Mula Token kamu buat transaksi sebelum 1 Agustus 2025.",
                    footer = "Notifikasi akan terhapus otomatis dalam 30 hari setelah dikirim.",
                    modifier = Modifier.testTag("notification_card")
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun NotificationScreenPreview() {
    MulaTheme { NotificationScreen(state = NotificationScreenState()) }
}
