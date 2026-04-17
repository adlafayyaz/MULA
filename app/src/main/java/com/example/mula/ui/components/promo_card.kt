package com.example.mula.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.example.mula.ui.theme.MulaDimens
import com.example.mula.ui.theme.MulaShapeTokens
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.default_divider_color
import com.example.mula.ui.theme.headline_accent_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.ui.theme.screen_surface_color

@Composable
fun PromoCard(
    title: String,
    description: String,
    image_res_name: String?,
    modifier: Modifier = Modifier,
    on_click: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(MulaDimens.promo_card_height)
            .border(1.dp, default_divider_color, MulaShapeTokens.large)
            .background(screen_surface_color, MulaShapeTokens.large)
            .clickable(onClick = on_click)
            .padding(mula_spacing.lg.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.titleLarge, color = headline_accent_color)
            Text(text = description, style = MaterialTheme.typography.bodyMedium, color = body_text_color)
        }
        Spacer(modifier = Modifier.width(mula_spacing.md.dp))
        Text(
            text = image_res_name ?: "img",
            style = MaterialTheme.typography.bodySmall,
            color = body_text_color
        )
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun PromoCardPreview() {
    MulaTheme {
        PromoCard(title = "Diskon QRIS", description = "hemat untuk pembayaran cepat", image_res_name = "img_promo", on_click = {})
    }
}
