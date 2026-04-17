package com.example.mula.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
fun ProductOptionChip(
    text: String,
    is_selected: Boolean,
    modifier: Modifier = Modifier,
    on_click: () -> Unit
) {
    Text(
        text = text,
        modifier = modifier
            .border(
                width = 1.dp,
                color = if (is_selected) primary_button_background_color else default_divider_color,
                shape = MulaShapeTokens.small
            )
            .background(
                color = if (is_selected) primary_button_background_color else screen_surface_color,
                shape = MulaShapeTokens.small
            )
            .clickable(onClick = on_click)
            .padding(horizontal = mula_spacing.md.dp, vertical = mula_spacing.sm.dp),
        style = MaterialTheme.typography.labelMedium,
        color = if (is_selected) text_on_primary else body_text_color
    )
}

@Preview(showBackground = true)
@Composable
private fun ProductOptionChipPreview() {
    MulaTheme { ProductOptionChip(text = "Dingin", is_selected = true, on_click = {}) }
}
