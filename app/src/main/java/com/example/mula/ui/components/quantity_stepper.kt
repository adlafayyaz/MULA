package com.example.mula.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mula.ui.theme.MulaDimens
import com.example.mula.ui.theme.MulaShapeTokens
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.default_divider_color
import com.example.mula.ui.theme.mula_spacing
import com.example.mula.ui.theme.muted_text_color
import com.example.mula.ui.theme.screen_surface_color

@Composable
fun QuantityStepper(
    quantity: Int,
    modifier: Modifier = Modifier,
    on_increment: () -> Unit,
    on_decrement: () -> Unit,
    is_increment_enabled: Boolean = true,
    is_decrement_enabled: Boolean = true
) {
    Row(
        modifier = modifier
            .height(MulaDimens.product_quantity_control_height)
            .border(1.dp, default_divider_color, MulaShapeTokens.pill)
            .background(screen_surface_color, MulaShapeTokens.pill)
            .padding(horizontal = mula_spacing.sm.dp),
        horizontalArrangement = Arrangement.spacedBy(mula_spacing.sm.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SecondaryTextLink(text = "-", on_click = on_decrement, enabled = is_decrement_enabled)
        Text(text = quantity.toString(), style = MaterialTheme.typography.labelLarge, color = body_text_color)
        SecondaryTextLink(text = "+", on_click = on_increment, enabled = is_increment_enabled)
    }
}

@Preview(showBackground = true)
@Composable
private fun QuantityStepperPreview() {
    MulaTheme { QuantityStepper(quantity = 2, on_increment = {}, on_decrement = {}) }
}
