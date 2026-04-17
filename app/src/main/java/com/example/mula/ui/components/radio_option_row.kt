package com.example.mula.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mula.ui.theme.MulaShapeTokens
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.default_divider_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.ui.theme.primary_button_background_color
import com.example.mula.ui.theme.screen_surface_color

@Composable
fun RadioOptionRow(
    title: String,
    trailing_text: String? = null,
    is_selected: Boolean,
    modifier: Modifier = Modifier,
    on_click: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(screen_surface_color, MulaShapeTokens.small)
            .clickable(onClick = on_click)
            .padding(horizontal = mula_spacing.md.dp, vertical = mula_spacing.sm.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(mula_spacing.sm.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(if (is_selected) primary_button_background_color else screen_surface_color)
                    .border(1.dp, if (is_selected) primary_button_background_color else default_divider_color, CircleShape)
                    .padding(6.dp)
            )
            Text(text = title, style = MaterialTheme.typography.bodyMedium, color = body_text_color)
        }
        if (trailing_text != null) {
            Text(text = trailing_text, style = MaterialTheme.typography.bodyMedium, color = body_text_color)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RadioOptionRowPreview() {
    MulaTheme { RadioOptionRow(title = "Regular", trailing_text = "+0", is_selected = true, on_click = {}) }
}
