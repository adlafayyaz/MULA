package com.example.mula.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mula.ui.theme.MulaShapeTokens
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.default_divider_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.ui.theme.primary_button_background_color
import com.example.mula.ui.theme.screen_surface_color
import com.example.mula.ui.theme.text_on_primary

@Composable
fun OrderHistoryCard(
    status_text: String,
    branch_name: String,
    date_text: String,
    item_summary_text: String,
    source_text: String,
    total_text: String,
    show_failed_badge: Boolean,
    modifier: Modifier = Modifier,
    on_card_click: () -> Unit,
    on_reorder_click: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, default_divider_color, MulaShapeTokens.large)
            .background(screen_surface_color, MulaShapeTokens.large)
            .clickable(onClick = on_card_click)
            .padding(mula_spacing.md.dp),
        verticalArrangement = Arrangement.spacedBy(mula_spacing.xs.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = status_text, style = MaterialTheme.typography.labelMedium, color = body_text_color)
            if (show_failed_badge) {
                Text(text = "failed", style = MaterialTheme.typography.bodySmall, color = body_text_color)
            }
        }
        Text(text = branch_name, style = MaterialTheme.typography.titleMedium, color = body_text_color)
        Text(text = date_text, style = MaterialTheme.typography.bodySmall, color = body_text_color)
        Text(text = item_summary_text, style = MaterialTheme.typography.bodySmall, color = body_text_color)
        Text(text = source_text, style = MaterialTheme.typography.bodySmall, color = body_text_color)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = total_text, style = MaterialTheme.typography.titleMedium, color = body_text_color)
            TextButton(
                onClick = on_reorder_click,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = primary_button_background_color,
                    contentColor = text_on_primary
                ),
                shape = MulaShapeTokens.pill
            ) {
                Text(text = "Pesan Lagi", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun OrderHistoryCardPreview() {
    MulaTheme {
        OrderHistoryCard(
            status_text = "Selesai",
            branch_name = "Mula Central",
            date_text = "17 Apr 2026",
            item_summary_text = "1x Iced Latte",
            source_text = "mobile_app",
            total_text = "Rp23.000",
            show_failed_badge = false,
            on_card_click = {},
            on_reorder_click = {}
        )
    }
}
