package com.example.mula.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.mula.ui.theme.MulaTheme
import com.example.mula.ui.theme.body_text_color
import com.example.mula.ui.theme.muted_text_color

@Composable
fun SecondaryTextLink(
    text: String,
    on_click: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text_align: TextAlign = TextAlign.Center
) {
    Text(
        text = text,
        modifier = modifier.clickable(enabled = enabled, onClick = on_click),
        style = MaterialTheme.typography.labelMedium,
        color = if (enabled) body_text_color else muted_text_color,
        textAlign = text_align
    )
}

@Preview(showBackground = true, widthDp = 393)
@Composable
private fun SecondaryTextLinkPreview() {
    MulaTheme { SecondaryTextLink(text = "lupa kata sandi", on_click = {}) }
}
