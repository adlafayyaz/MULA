package com.example.mula.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.body_text_color

@Composable
fun CheckoutSummaryRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    is_emphasized: Boolean = false
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = if (is_emphasized) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium, color = body_text_color)
        Text(text = value, style = if (is_emphasized) MaterialTheme.typography.titleMedium else MaterialTheme.typography.labelMedium, color = body_text_color)
    }
}

@Preview(showBackground = true)
@Composable
private fun CheckoutSummaryRowPreview() {
    MulaTheme { CheckoutSummaryRow(label = "Total", value = "Rp23.000", is_emphasized = true) }
}
