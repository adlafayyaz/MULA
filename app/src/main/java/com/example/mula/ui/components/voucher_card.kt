package com.example.mula.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mula.ui.theme.MulaShapeTokens
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.default_divider_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.ui.theme.screen_surface_color

@Composable
fun VoucherCard(
    title: String,
    description: String,
    expiry_date_text: String,
    action_text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    on_action_click: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, default_divider_color, MulaShapeTokens.medium)
            .background(screen_surface_color, MulaShapeTokens.medium)
            .padding(mula_spacing.md.dp),
        horizontalArrangement = Arrangement.spacedBy(mula_spacing.md.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium, color = body_text_color)
            Text(text = description, style = MaterialTheme.typography.bodySmall, color = body_text_color)
            Text(text = expiry_date_text, style = MaterialTheme.typography.bodySmall, color = body_text_color)
        }
        Box(modifier = Modifier.width(1.dp).height(64.dp).background(default_divider_color))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "logo", style = MaterialTheme.typography.bodySmall, color = body_text_color)
            PrimaryButton(
                text = action_text,
                on_click = on_action_click,
                enabled = enabled,
                modifier = Modifier.width(110.dp)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun VoucherCardPreview() {
    MulaTheme {
        VoucherCard(
            title = "Hemat 10K",
            description = "voucher pembayaran",
            expiry_date_text = "berlaku sampai 31 des",
            action_text = "Pakai",
            on_action_click = {}
        )
    }
}
