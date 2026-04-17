package com.example.mula.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mula.ui.theme.MulaShapeTokens
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.ui.theme.screen_surface_color

@Composable
fun TokenBalanceCard(
    token_text: String,
    button_text: String,
    modifier: Modifier = Modifier,
    on_button_click: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(screen_surface_color, MulaShapeTokens.large)
            .padding(mula_spacing.lg.dp),
        horizontalArrangement = Arrangement.spacedBy(mula_spacing.md.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "◉", style = MaterialTheme.typography.headlineMedium, color = body_text_color)
        Column(modifier = Modifier.weight(1f)) {
            Text(text = token_text, style = MaterialTheme.typography.titleLarge, color = body_text_color)
            Text(text = "token_balance", style = MaterialTheme.typography.bodySmall, color = body_text_color)
        }
        PrimaryButton(
            text = button_text,
            on_click = on_button_click,
            modifier = Modifier.size(width = 110.dp, height = 40.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun TokenBalanceCardPreview() {
    MulaTheme { TokenBalanceCard(token_text = "240 Token", button_text = "Tukarkan", on_button_click = {}) }
}
