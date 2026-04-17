package com.example.mula.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.muted_text_color

@Composable
fun SectionHeaderRow(
    title: String,
    trailing_text: String? = null,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium, color = body_text_color)
        if (trailing_text != null) {
            Text(text = trailing_text, style = MaterialTheme.typography.bodySmall, color = muted_text_color)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SectionHeaderRowPreview() {
    MulaTheme { SectionHeaderRow(title = "Promo Hari Ini", trailing_text = "lihat semua") }
}
